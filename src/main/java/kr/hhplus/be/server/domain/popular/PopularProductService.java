package kr.hhplus.be.server.domain.popular;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularProductService {

    private final PopularProductRepository popularProductRepository;

    @Cacheable(value = "popularProducts", key = "'popularProducts'", cacheManager = "redisCacheManager")
    @Transactional(readOnly = true)
    public List<PopularProduct> getPopularProducts() {
        return popularProductRepository.findPopularProducts();
    }
}
