-- point
INSERT INTO point (user_id, point, updated_at) VALUES (1, 1000000, NOW());

-- product
INSERT INTO product (product_id, name, price, product_status) VALUES (11, '상품 A', 10000, 'ACTIVE');
INSERT INTO product (product_id, name, price, product_status) VALUES (12, '상품 B', 20000, 'ACTIVE');

-- stock
INSERT INTO stock (stock_id, product_id, quantity, updated_at) VALUES (1, 11, 10, NOW());
INSERT INTO stock (stock_id, product_id, quantity, updated_at) VALUES (2, 12, 5, NOW());

-- coupon
INSERT INTO coupon (coupon_id, name, discount_amount, issued_quantity, issued_count, coupon_status, activated_at, expired_at, created_at, updated_at) VALUES (10, '주문할인쿠폰', 3000, 100, 1, 'ACTIVE', NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 3 DAY, NOW(), NOW());

-- user_coupon
INSERT INTO user_coupon (user_coupon_id, user_id, coupon_id, used, used_at, created_at) VALUES (5, 1, 10, FALSE, NULL, NOW());
