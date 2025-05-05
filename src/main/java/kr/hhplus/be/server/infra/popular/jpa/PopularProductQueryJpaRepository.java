package kr.hhplus.be.server.infra.popular.jpa;

import kr.hhplus.be.server.infra.order.jpa.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PopularProductQueryJpaRepository extends JpaRepository<OrderEntity, Long>{
    @Query(value = """
            SELECT
                oi.product_id AS productId,
            	p.name AS name,
            	p.price AS price,
            	SUM(oi.quantity) AS totalQuantity
            FROM orders o
            LEFT JOIN order_item oi
            	ON o.order_id = oi.order_id
            LEFT JOIN product p
            	ON oi.product_id = p.product_id
            WHERE
            	o.created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
            GROUP BY
            	oi.product_id
            ORDER BY
            	totalQuantity DESC,
            	price DESC
            LIMIT 5;
            """, nativeQuery = true)
    List<NativePopularProduct> findPopularProducts();
}
