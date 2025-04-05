# API 명세

# 🧾 API 명세

## 💰 1. `PointController` API 명세

---

| 기능 | Method | URL | Request | Response | 조건 |
| --- | --- | --- | --- | --- | --- |
| 포인트 충전 | `POST` | `/points/{userId}/charge` | { "amount": 10000 } | { "userId": 1, "point": 10000, "updatedAt": 15000 } | amount > 0 && maxPoint 초과시 실패 |
| 포인트 사용 | `POST`  | `/points/{userId}/use` | { "amount": 5000 } | { "userId": 1, "point": 5000, "updatedAt": 10000 } | amount > 0 && 잔액 초과시 실패 |
| 포인트 잔액 조회 | `GET`  | `/points/{userId}` | - | { "userId": 1, "point": 10000 } | - |
| 포인트 이력 조회 | `GET`  | `/points/{userId}/histories` | - | [ {"id": 1, "userId": 1, "type": "CHARGE", "amount": 10000, "createdAt": "..." } ] | - |

## 🎟️ 2. `CouponController` API 명세

---

| 기능 | Method | URL | Request | Response | 조건 |
| --- | --- | --- | --- | --- | --- |
| 쿠폰 생성 | `POST` | `/coupons` | {"name": "coupon", "issuedAt": "…", "expiredAt":"…"} | {"couponId": 1, "name": "coupon", "issuedAt": "…", "expiredAt":"…"} |  |
| 쿠폰 발급 요청 | `POST` | `/coupons/issue` | { "userId": 1, "couponId": 10 } | { "userId": 1, "couponId": 10, "issuedAt": "..." } | 쿠폰 상태 `ACTIVE` && 유효기간 내 && 수량 초과 시 실패 (선착순 제한) |
| 발급된 쿠폰 목록 조회 | `GET` | `/users/{userId}/coupons` | - | [ { "userCouponId": 1, "couponId": 10, "used": false, "issuedAt": "..." } ] | - |
| 사용 가능한 쿠폰 조회 | `GET` | `/users/{userId}/coupons/available` | - | [ { "userCouponId": 1, "couponId": 10, "discountAmount": 1000 } ] | 유효기간 내 && 미사용 쿠폰만 |
| 쿠폰 사용 | `PATCH` | `/users/{userId}/coupons/{userCouponId}/use` | - | { "userCouponId": 1, "used": true, "usedAt": "..." } | 사용 가능한 상태여야 함 |

## 🛍️ 3. `ProductController` API 명세

---

| 기능 | Method | URL | Request | Response | 조건 |
| --- | --- | --- | --- | --- | --- |
| 상품 등록 | `POST` | `/products` | { "productId": 1, "name": "...", "description": "...", "price": 10000, "status": "ACTIVE" } | { "productId": 1, "name": "...", "description": "...", "price": 10000, "status": "ACTIVE" } |  |
| 상품 목록 조회 | `GET` | `/products` | - | [ { "productId": 1, "name": "...", "price": 10000, "status": "ACTIVE" } ] | - |
| 상품 상세 조회 | `GET` | `/products/{productId}` | - | { "productId": 1, "name": "...", "description": "...", "price": 10000, "status": "ACTIVE" } | - |
| 인기 상품 조회 | `GET` | `/products/popular` | - | [ { "productId": 1, "name": "...", "salesCount": 123 } ] | 최근 3일 기준 |
| 상품 재고 조회 | `GET` | `/products/{productId}/inventory` | - | { "productId": 1, "quantity": 100, "reserved": 10 } | - |

## 🧾 4. `OrderController` API 명세

---

| 기능 | Method | URL | Request | Response | 조건 |
| --- | --- | --- | --- | --- | --- |
| 주문 생성 | `POST` | `/orders` | { "userId": 1, "items": [ { "productId": 1, "quantity": 2 } ], "userCouponIds": [1], "usedPoint": 5000 } | { "orderId": 1001, "finalAmount": 15000, "createdAt": "..." } | 재고 충분 && 쿠폰 사용 가능 |
| 주문 목록 조회 | `GET` | `/orders` | - | [ { "orderId": 1001, "finalAmount": 15000, "createdAt": "..." } ] | - |
| 주문 상세 조회 | `GET` | `/orders/{orderId}` | - | { "orderId": 1001, "userId": 1, "items": [...], "finalAmount": 15000, ... } | - |
| 주문 상태 이력 조회 | `GET` | `/orders/{orderId}/events` | - | [ { "status": "CREATED", "changedAt": "..." }, ... ] | - |
| 사용자 주문 목록 조회 | `GET` | `/users/{userId}/orders` | - | { "orderId": 1001, "finalAmount": 15000, "createdAt": "..." } | - |

## 💳 5. `PaymentController` API 명세

---

| 기능 | Method | URL | Request | Response | 조건 |
| --- | --- | --- | --- | --- | --- |
| 결제 요청 | `POST` | `/payments` | { "orderId": 1001, "paymentMethod": "POINT" } | { "paymentId": 2001, "status": "SUCCESS", "paidAt": "..." } | 주문 상태 `CREATED` && 포인트 또는 결제 수단 충족 |
| 결제 상세 조회 | `GET` | `/payments/{paymentId}` | - | { "paymentId": 2001, "status": "SUCCESS", "paidAmount": 15000, "paidAt": "..." } | - |