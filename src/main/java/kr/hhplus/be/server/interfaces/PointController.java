package kr.hhplus.be.server.interfaces;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/points")
public class PointController {

    @GetMapping("")
    public ApiResponse getPoint(@RequestParam Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("point", 10000);
        response.put("updatedAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }

    @PatchMapping("/{userId}")
    public ApiResponse charge(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long amount = Long.valueOf(request.get("amount").toString());

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("point", amount);
        response.put("updatedAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }

    @GetMapping("/histories")
    public ApiResponse getHistories(@RequestParam Long userId) {
        Map<String, Object> history = new HashMap<>();
        history.put("type", "CHARGE");
        history.put("amount", 10000);
        history.put("createdAt", LocalDateTime.now());
        System.out.println(history);

        return ApiResponse.success(java.util.List.of(history));
    }
}