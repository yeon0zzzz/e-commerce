package kr.hhplus.be.server.infra.popular;

import kr.hhplus.be.server.domain.popular.PopularProduct;
import kr.hhplus.be.server.domain.popular.PopularProductRepository;
import kr.hhplus.be.server.infra.popular.jpa.PopularProductQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PopularProductRepositoryImpl implements PopularProductRepository {

    private final PopularProductQueryJpaRepository popularProductQueryJpaRepository;

    @Override
    public List<PopularProduct> findPopularProducts() {
        return popularProductQueryJpaRepository.findPopularProducts().stream()
                .map(p -> PopularProduct.builder()
                        .productId(p.getProductId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .totalQuantity(p.getTotalQuantity())
                        .build())
                .toList();
    }
}
