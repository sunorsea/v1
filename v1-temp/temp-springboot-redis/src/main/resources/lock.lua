local lockKey = KEYS[1]
local lockValue = KEYS[2]
local lockTime = KEYS[3]

local result1 = redis.call('setnx',lockKey,lockValue)
if(result1 == 1)
then
    local result2=redis.call('expire',lockKey,lockTime);
    return result2;
else
    return result1;
end