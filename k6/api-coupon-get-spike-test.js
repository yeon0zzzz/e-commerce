import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 1000 },  //10초 만에 1000명까지 증가
    { duration: '30s', target: 1000 },  //30초 동안 1000명 유지
    { duration: '10s', target: 1 }  //10초 동안 급감
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