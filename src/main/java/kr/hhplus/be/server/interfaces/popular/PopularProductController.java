package kr.hhplus.be.server.interfaces.popular;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.domain.popular.PopularProduct;
import kr.hhplus.be.server.domain.popular.PopularProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class PopularProductController {

    private final PopularProductService popularProductService;

    @GetMapping("/stats/products/popular")
    public ApiResponse<PopularProductListDto.Response> getPopularProducts() {

        List<PopularProduct> popularProductList = popularProductService.getPopularProducts();

        return ApiResponse.success(PopularProductListDto.Response.of(popularProductList));
    }

}
