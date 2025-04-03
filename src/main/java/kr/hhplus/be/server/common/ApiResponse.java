package kr.hhplus.be.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@Getter
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
    

    public static ApiResponse success(Map<String, Object> data) {
        return new ApiResponse(200, "SUCCESS", data);
    }

    public static ApiResponse success(List<Map<String, Object>> data) {
        return new ApiResponse(200, "SUCCESS", data);
    }

    public static ApiResponse error(int code, String message) {
        return new ApiResponse(code, message, null);
    }

}