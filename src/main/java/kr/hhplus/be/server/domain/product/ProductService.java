package kr.hhplus.be.server.domain.product;

import java.util.List;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public Product getById(Long productId) {
        Product product = productRepository.findById(productId);
        product.validateIsActive();
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getPopular(int limit) {
        return productRepository.findPopularProducts(limit);
    }

    public Inventory getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);

        return inventory;
    }
}
