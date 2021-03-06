--
-- Created by IntelliJ IDEA.
-- User: wangzhj
-- Date: 2016/12/14
-- Time: 9:06
-- To change this template use File | Settings | File Templates.
--

local key = "user:login:" .. KEYS[1];

local is_exists = redis.call("EXISTS", key)
if is_exists == 0 then
    redis.call("SET", key, 1)
    redis.call("EXPIRE", key, 60)
    redis.log(redis.LOG_NOTICE, "不存在")
    return 0
else
    local counter = redis.call("GET", key)
    if tonumber(counter) >= 3 then
        redis.log(redis.LOG_NOTICE, "3次以上")
        return 1
    else
        redis.call("INCR", key)
        redis.log(redis.LOG_NOTICE, "+1")
        return 0
    end
end
