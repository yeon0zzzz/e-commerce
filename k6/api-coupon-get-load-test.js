import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
  stages: [
    { duration: '1m', target: 100 },  //1분간 100명 유지
    { duration: '2m', target: 500 },  // 2분간 500명 유지
    { duration: '2m', target: 1000 }  // 2분간 1000명 유지
  ],
  thresholds: {
    http_req_duration: ['p(95)<1000'],
    http_req_failed: ['rate<0.01'],
  }
}

export default function () {
    http.get('http://localhost:8080/api/v1/coupons/1');
    sleep(1);
}