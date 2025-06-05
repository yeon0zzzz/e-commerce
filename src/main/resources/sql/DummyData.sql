-- 쿠폰
INSERT INTO coupon (name, discount_amount, activated_at, expired_at, issued_count, issued_quantity, coupon_status, created_at, updated_at) VALUES ('웰컴 쿠폰', 5000, NOW() - INTERVAL 10 DAY, NOW() + INTERVAL 20 DAY, 3, 100, 'ACTIVE', NOW() - INTERVAL 10 DAY, NOW());
INSERT INTO coupon (name, discount_amount, activated_at, expired_at, issued_count, issued_quantity, coupon_status, created_at, updated_at) VALUES ('여름 한정 쿠폰', 10000, NOW() - INTERVAL 30 DAY, NOW() - INTERVAL 1 DAY, 100, 100, 'EXPIRED', NOW() - INTERVAL 30 DAY, NOW() - INTERVAL 1 DAY);
INSERT INTO coupon (name, discount_amount, activated_at, expired_at, issued_count, issued_quantity, coupon_status, created_at, updated_at) VALUES ('특별 할인 쿠폰', 3000, NOW(), NOW() + INTERVAL 10 DAY, 5, 20, 'ACTIVE', NOW(), NOW());
INSERT INTO coupon (name, discount_amount, activated_at, expired_at, issued_count, issued_quantity, coupon_status, created_at, updated_at) VALUES ('VIP 전용 쿠폰', 15000, NOW() - INTERVAL 5 DAY, NOW() + INTERVAL 25 DAY, 2, 5, 'ACTIVE', NOW() - INTERVAL 5 DAY, NOW());

-- 사용자 쿠폰
INSERT INTO user_coupon (user_id, coupon_id, used, used_at, created_at) VALUES (1, 1, 0, NULL, NOW() - INTERVAL 3 DAY);
INSERT INTO user_coupon (user_id, coupon_id, used, used_at, created_at) VALUES (1, 2, 1, NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 5 DAY);
INSERT INTO user_coupon (user_id, coupon_id, used, used_at, created_at) VALUES (2, 1, 0, NULL, NOW() - INTERVAL 1 DAY);
INSERT INTO user_coupon (user_id, coupon_id, used, used_at, created_at) VALUES (2, 3, 1, NOW(), NOW() - INTERVAL 1 DAY);
INSERT INTO user_coupon (user_id, coupon_id, used, used_at, created_at) VALUES (3, 4, 0, NULL, NOW() - INTERVAL 2 DAY);


-- 결제
INSERT INTO payment (order_id, amount, status, paid_at) VALUES (1, 72000, 'COMPLETED', NOW() - INTERVAL 1 DAY);
INSERT INTO payment (order_id, amount, status, paid_at) VALUES (2, 54000, 'COMPLETED', NOW() - INTERVAL 2 DAY);
INSERT INTO payment (order_id, amount, status, paid_at) VALUES (3, 27000, 'FAILED', NOW() - INTERVAL 1 DAY);
INSERT INTO payment (order_id, amount, status, paid_at) VALUES (1, 72000, 'CANCELLED', NOW() - INTERVAL 5 DAY);
INSERT INTO payment (order_id, amount, status, paid_at) VALUES (3, 27000, 'PENDING', NULL);


-- 재고
INSERT INTO stock (product_id, quantity, updated_at) VALUES (1, 100, NOW() - INTERVAL 1 DAY);
INSERT INTO stock (product_id, quantity, updated_at) VALUES (2, 50, NOW() - INTERVAL 2 DAY);
INSERT INTO stock (product_id, quantity, updated_at) VALUES (3, 80, NOW());
INSERT INTO stock (product_id, quantity, updated_at) VALUES (4, 0, NOW() - INTERVAL 3 DAY);
INSERT INTO stock (product_id, quantity, updated_at) VALUES (5, 150, NOW() - INTERVAL 1 DAY);
INSERT INTO stock (product_id, quantity, updated_at) VALUES (6, 200, NOW());


-- 포인트
INSERT INTO point (user_id, point, updated_at) VALUES (1, 10000, NOW());
INSERT INTO point (user_id, point, updated_at) VALUES (2, 5000, NOW() - INTERVAL 1 DAY);
INSERT INTO point (user_id, point, updated_at) VALUES (3, 0, NOW() - INTERVAL 2 DAY);
INSERT INTO point (user_id, point, updated_at) VALUES (4, 20000, NOW() - INTERVAL 1 DAY);
INSERT INTO point (user_id, point, updated_at) VALUES (5, 15000, NOW());


-- 상품
INSERT INTO product (name, price, product_status) VALUES ('상품 A', 10000, 'ACTIVE');
INSERT INTO product (name, price, product_status) VALUES ('상품 B', 20000, 'ACTIVE');
INSERT INTO product (name, price, product_status) VALUES ('상품 C', 30000, 'ACTIVE');
INSERT INTO product (name, price, product_status) VALUES ('상품 D', 15000, 'ACTIVE');
INSERT INTO product (name, price, product_status) VALUES ('상품 E', 25000, 'ACTIVE');
INSERT INTO product (name, price, product_status) VALUES ('상품 F', 5000,  'ACTIVE');

-- 주문
INSERT INTO orders (user_id, total_amount, discount_amount, final_amount, status, created_at, updated_at) VALUES (1, 80000, 8000, 72000, 'PAID', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY);
INSERT INTO orders (user_id, total_amount, discount_amount, final_amount, status, created_at, updated_at) VALUES (2, 60000, 6000, 54000, 'PAID', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY);
INSERT INTO orders (user_id, total_amount, discount_amount, final_amount, status, created_at, updated_at) VALUES (3, 30000, 3000, 27000, 'PAID', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY);

-- 주문 아이템
INSERT INTO order_item (order_id, product_id, quantity, price, total_price) VALUES (1, 1, 2, 10000, 20000);
INSERT INTO order_item (order_id, product_id, quantity, price, total_price) VALUES (1, 2, 3, 20000, 60000);
INSERT INTO order_item (order_id, product_id, quantity, price, total_price) VALUES (2, 3, 1, 30000, 30000);
INSERT INTO order_item (order_id, product_id, quantity, price, total_price) VALUES (2, 4, 2, 15000, 30000);
INSERT INTO order_item (order_id, product_id, quantity, price, total_price) VALUES (3, 5, 4, 25000, 100000);
INSERT INTO order_item (order_id, product_id, quantity, price, total_price) VALUES (3, 6, 2, 5000, 10000);

-- 상품 일간 판매량
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (1, 5, NOW() - INTERVAL 1 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (2, 8, NOW() - INTERVAL 1 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (3, 3, NOW() - INTERVAL 2 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (4, 0, NOW() - INTERVAL 1 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (5, 10, NOW() - INTERVAL 1 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (6, 20, NOW() - INTERVAL 1 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (1, 2, NOW() - INTERVAL 2 DAY);
INSERT INTO product_sales_daily (product_id, quantity, sales_at) VALUES (2, 3, NOW() - INTERVAL 2 DAY);




