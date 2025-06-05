import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 1000 },  // 10초 만에 1000명 도달
    { duration: '30s', target: 1000 },  // 30초 유지
    { duration: '10s', target: 1 }      // 10초 만에 1명으로 감소
  ],
  thresholds: {
    http_req_duration: ['p(95)<1000'],
    http_req_failed: ['rate<0.01'],
  }
};

const BASE_URL = 'http://localhost:8080/api/v1';
const couponId = 1; // 테스트할 쿠폰ID, 필요시 랜덤/다중 처리

export default function () {
    // userId도 랜덤으로 만들어주면 중복발급 케이스 실험 가능
    const userId = Math.floor(Math.random() * 10000) + 1;
    let payload = JSON.stringify({ userId: userId });
    let headers = { 'Content-Type': 'application/json' };
    let res = http.post(`${BASE_URL}/coupons/${couponId}/publish`, payload, { headers: headers });

    check(res, {
        'status is 200 or 400 (중복/수량초과)': (r) => r.status === 200 || r.status === 400,
    });

    sleep(1);
}
