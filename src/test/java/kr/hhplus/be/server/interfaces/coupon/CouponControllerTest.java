package kr.hhplus.be.server.interfaces.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponRepository;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
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

        mockMvc.perform(get("/coupons/1"))
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

        mockMvc.perform(post("/coupons/1/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.couponId").value(coupon.couponId()))
                .andExpect(jsonPath("$.data.used").value(false));
    }
}
