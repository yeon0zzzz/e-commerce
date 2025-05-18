package kr.hhplus.be.server.support.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;

    private final AopForTransaction aopForTransaction;

    @Around("@annotation(kr.hhplus.be.server.support.aop.DistributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 정보
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        RLock rLock = redissonClient.getLock(key);

        try {
            // 락 획득 시도
            boolean lockAvailable = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!lockAvailable) {
                throw new IllegalStateException("Redisson Lock 획득 실패: key = " + key);
            }

            // 트랜잭션이 있을 경우, unlock을 트랜잭션 종료(커밋) 이후로 연기
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCompletion(int status) {
                        unlockSafely(rLock, method.getName(), key);
                    }
                });
            }

            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw new InterruptedException("락 획득 중 인터럽트 발생");
        } finally {
            // 트랜잭션이 없는 경우엔 직접 해제
            if (!TransactionSynchronizationManager.isActualTransactionActive() && rLock.isLocked()) {
                unlockSafely(rLock, method.getName(), key);
            }
        }
    }

    private void unlockSafely(RLock rLock, String methodName, String key) {
        try {
            if (rLock.isHeldByCurrentThread() && rLock.isLocked()) {
                rLock.unlock();
                log.info("Redisson Lock Unlock: method={}, key={}", methodName, key);
            }
        } catch (IllegalMonitorStateException e) {
            log.warn("Redisson Lock Already Unlock: method={}, key={}", methodName, key);
        }
    }
}
