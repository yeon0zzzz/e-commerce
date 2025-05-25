redis.call('ZINCRBY', KEYS[1], ARGV[1], ARGV[2])
redis.call('EXPIRE', KEYS[1], ARGV[3])
--redis.call('ZINCRBY', "product:sales:20250520", "2", "11")
--redis.call('EXPIRE', "product:sales:20250520", "172800")
return 1