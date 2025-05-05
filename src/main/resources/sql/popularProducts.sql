-- 상품
INSERT INTO product (product_id, name, price, product_status) VALUES (1, '상품 A', 10000, 'ACTIVE');
INSERT INTO product (product_id, name, price, product_status) VALUES (2, '상품 B', 20000, 'ACTIVE');
INSERT INTO product (product_id, name, price, product_status) VALUES (3, '상품 C', 30000, 'ACTIVE');
INSERT INTO product (product_id, name, price, product_status) VALUES (4, '상품 D', 15000, 'ACTIVE');
INSERT INTO product (product_id, name, price, product_status) VALUES (5, '상품 E', 25000, 'ACTIVE');
INSERT INTO product (product_id, name, price, product_status) VALUES (6, '상품 F', 5000,  'ACTIVE');

-- 주문
INSERT INTO orders (order_id, user_id, total_amount, discount_amount, final_amount, status, created_at, updated_at) VALUES (1, 1, 80000, 8000, 72000, 'PAID', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY);
INSERT INTO orders (order_id, user_id, total_amount, discount_amount, final_amount, status, created_at, updated_at) VALUES (2, 2, 60000, 6000, 54000, 'PAID', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY);
INSERT INTO orders (order_id, user_id, total_amount, discount_amount, final_amount, status, created_at, updated_at) VALUES (3, 3, 30000, 3000, 27000, 'PAID', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY);

-- 주문 아이템
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price, total_price) VALUES (1, 1, 1, 2, 10000, 20000);
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price, total_price) VALUES (2, 1, 2, 3, 20000, 60000);
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price, total_price) VALUES (3, 2, 3, 1, 30000, 30000);
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price, total_price) VALUES (4, 2, 4, 2, 15000, 30000);
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price, total_price) VALUES (5, 3, 5, 4, 25000, 100000);
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price, total_price) VALUES (6, 3, 6, 2, 5000, 10000);

