package kr.hhplus.be.server.interfaces.order;

import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.application.order.OrderPaymentCommand;
import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping("/orders/payment")
    public ApiResponse<OrderPaymentDto.Response> orderPayment(@RequestBody OrderPaymentDto.Request request) {

        // 1. 클라이언트의 요청은 DTO 로 받아서 응용 계층 입력 객체로 변환 (Request -> Command)
        OrderPaymentCommand command = request.toCommand();

        // 2. 응용 계층 서비스 호출 및 결과 반환 (Domain domain = Facade.doSomething(command)
        Order order = orderFacade.orderPayment(command);

        // 3. return -> 결과 반환 DTO 를 이용하여 응답 객체로 변환 (Domain -> Response)
        return ApiResponse.success(OrderPaymentDto.Response.of(order));
    }
}
