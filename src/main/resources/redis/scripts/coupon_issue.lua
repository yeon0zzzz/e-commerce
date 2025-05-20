-- KEYS[1] = 발급 ZSET key (coupon:issue:{couponId})
-- KEYS[2] = 메타 HASH key (coupon:meta:{couponId})
-- ARGV[1] = userId
-- ARGV[2] = epochMillis (score)
-- ARGV[3] = 발급 limit

-- 중복 발급 체크
if redis.call("ZSCORE", KEYS[1], ARGV[1]) then
  return -1  -- 이미 발급됨
end

-- 수량 체크
local issued = redis.call("ZCARD", KEYS[1])
if tonumber(issued) >= tonumber(ARGV[3]) then
  return -2  -- 수량 초과
end

-- 발급 기록
redis.call("ZADD", KEYS[1], ARGV[2], ARGV[1])

-- TTL이 설정되어 있지 않다면 2일 설정 (옵션)
if redis.call("TTL", KEYS[1]) == -1 then
  redis.call("EXPIRE", KEYS[1], 172800)
end

return 1  -- 발급 성공
