package kr.hhplus.be.server.interfaces.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointRepository;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("포인트 컨트롤러 통합 테스트")
public class PointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("포인트 조회 성공")
    void success_get_point_test() throws Exception {
        Point point = pointRepository.save(
                Point.builder()
                .userId(1L)
                .point(100L)
                .updatedAt(LocalDateTime.now())
                .build()
        );

        mockMvc.perform(get("/users/1/point"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.point").value(100L));
    }

    @Test
    @DisplayName("포인트_충전_테스트")
    void success_charge_point_test() throws Exception {
        Point point = pointRepository.save(
                Point.builder()
                        .userId(1L)
                        .point(100L)
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(patch("/users/1/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.point").value(300L));
    }
}
