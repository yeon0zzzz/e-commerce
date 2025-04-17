package kr.hhplus.be.server.interfaces.point;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("")
public class PointController {

    @GetMapping("/users/{userId}/point")
    public ApiResponse getPoint(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("point", 10000);
        response.put("updatedAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }

    @PostMapping("/users/{userId}/point")
    public ApiResponse charge(@PathVariable Long userId, @RequestBody Map<String, Object> request) {
        Long amount = Long.valueOf(request.get("amount").toString());

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("point", amount);
        response.put("updatedAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }
}