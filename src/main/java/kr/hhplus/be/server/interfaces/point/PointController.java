package kr.hhplus.be.server.interfaces.point;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class PointController {

    private final PointService pointService;

    @GetMapping("/users/{userId}/point")
    public ApiResponse<PointDto.Response> getPoint(@PathVariable Long userId) {

        Point point = pointService.findByUserId(userId);

        return ApiResponse.success(PointDto.Response.of(point));
    }

    @PatchMapping("/users/{userId}/point")
    public ApiResponse<PointDto.Response> charge(@PathVariable Long userId, @RequestBody PointDto.Request request) {

        Point point = pointService.charge(userId, request.amount());

        return ApiResponse.success(PointDto.Response.of(point));
    }
}