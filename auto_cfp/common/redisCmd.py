import redis
from config.globals import*
from common.logCmd import LogHandler
log = LogHandler("main")

class RedisCmd(object):
    @staticmethod
    def __connect_to_redis():
        try:
            r = redis.Redis(host=redis_host, port=redis_port, db=0, password=redis_password)
            log.info("创建redis连接")
            return r
        except BaseException as e:
            log.error("创建redis连接失败。%s" %e)
    @staticmethod
    def get_to_redis(key):
        '''从redis中通过key取到值'''
        redis_conn  = RedisCmd.__connect_to_redis()
        value = redis_conn.get(key)
        if value:
            value = value.decode()  # 返回是bytes数据，decode将bytes转化为字符串
        log.info("从redis中获取redisKey:{}   redisValue:{}".format(key, value))
        return value
    @staticmethod
    def set_to_redis(key,value):
        '''设置键值对到redis 往redis中加一个短信验证码，模拟手机发送短信的功能'''
        redis_conn = RedisCmd.__connect_to_redis()
        value = redis_conn.set(key, value)
        log.info("设置redisValue:{} redisValue:{} 到redis数据库".format(key, value))
        return value

    @staticmethod
    def del_to_redis(key):
        '''从redis中删除键值通过key'''
        redis_conn  = RedisCmd.__connect_to_redis()
        value = redis_conn.delete(key)
        log.info("从redis中删除键值redisKey:{}".format(key))
        # return value

if __name__ == '__main__':
    print(RedisCmd.del_to_redis("MES_TOKEN_1752963d-1584-44b6-a24a-03fb55f3a2a1"))
