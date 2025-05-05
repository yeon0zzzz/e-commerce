package kr.hhplus.be.server.domain.popular;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularProductService {

    private final PopularProductRepository popularProductRepository;

    @Transactional(readOnly = true)
    public List<PopularProduct> getPopularProducts() {
        return popularProductRepository.findPopularProducts();
    }
}
