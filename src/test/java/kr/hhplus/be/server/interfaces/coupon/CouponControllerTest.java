package kr.hhplus.be.server.interfaces.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponRepository;
import kr.hhplus.be.server.infra.RedisRepository;
import kr.hhplus.be.server.infra.message.consumer.UserCouponIssuedConsumer;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("쿠폰 컨트롤러 통합 테스트")
public class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private RedisRepository redisRepository;

    @SpyBean
    private UserCouponIssuedConsumer userCouponIssuedConsumer;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private final Long couponId = 10L;
    private final String metaKey = "coupon:meta:" + couponId;
    private final String issueKey = "coupon:issue:" + couponId;
    private final String prefix = "/api/v1";

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
        redisRepository.remove(metaKey);
        redisRepository.remove(issueKey);

        redisRepository.addHash(metaKey, "limit", "3");
        redisRepository.addHash(metaKey, "expiresAt", LocalDate.now().plusDays(1).toString());
        redisRepository.addHash(metaKey, "status", CouponStatus.ACTIVE.name());
    }

    @Test
    @DisplayName("쿠폰 조회 성공")
    void success_get_coupon_test() throws Exception {
        Coupon coupon = couponRepository.save(
                Coupon.builder()
                        .name("discount_coupon")
                        .discountAmount(BigDecimal.valueOf(100L))
                        .issuedQuantity(100)
                        .issuedCount(0)
                        .couponStatus(CouponStatus.ACTIVE)
                        .activatedAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusDays(2))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get(prefix+"/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("discount_coupon"))
                .andExpect(jsonPath("$.data.discountAmount").value(100L))
                .andExpect(jsonPath("$.data.issuedQuantity").value(100))
                .andExpect(jsonPath("$.data.issuedCount").value(0))
                .andExpect(jsonPath("$.data.couponStatus").value("ACTIVE"));
    }

    @Test
    @DisplayName("쿠폰 발급 성공")
    void success_issue_coupon_test() throws Exception {
        Coupon coupon = couponRepository.save(
                Coupon.builder()
                        .name("discount_coupon")
                        .discountAmount(BigDecimal.valueOf(100L))
                        .issuedQuantity(100)
                        .issuedCount(0)
                        .couponStatus(CouponStatus.ACTIVE)
                        .activatedAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusDays(2))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(post(prefix+"/coupons/1/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.couponId").value(coupon.couponId()))
                .andExpect(jsonPath("$.data.used").value(false));
    }

    @Test
    @DisplayName("쿠폰 발급 요청 시 Kafka 메시지 전송 및 Consumer 가 수신하여 저장")
    void coupon_issue_kafka_event_flow() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post(prefix+"/coupons/{couponId}/publish", couponId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1
                                }
                                """))
                .andExpect(status().isOk());

        // then: Kafka 메시지가 Consumer 에 의해 처리되었는지 검증
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        Mockito.verify(userCouponIssuedConsumer, Mockito.atLeastOnce())
                                .consume(Mockito.argThat(msg ->
                                        msg.getUserId().equals(userId) &&
                                                msg.getCouponId().equals(couponId)
                                ))
                );
    }
}
