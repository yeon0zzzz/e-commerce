package kr.hhplus.be.server.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void truncateAllTables() {

        entityManager.createNativeQuery("TRUNCATE TABLE point").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE coupon").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE user_coupon").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE stock").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE product").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE orders").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE order_item").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE payment").executeUpdate();
    }
}
