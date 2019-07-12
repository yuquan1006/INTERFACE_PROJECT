package com.ipaylinks.test.mock.server.redis.sequence;

import com.ipaylinks.cfp.common.util.RedisTemplateUtils;
import com.ipaylinks.test.mock.server.MockServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockServerApplication.class)
public class RedisSequenceTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        String bizCode1nextVal1 = RedisTemplateUtils.uuid(stringRedisTemplate, "bizCode1");
        System.out.println(bizCode1nextVal1);
        String bizCode1nextVal2 = RedisTemplateUtils.uuid(stringRedisTemplate, "bizCode1", 30);
        System.out.println(bizCode1nextVal2);
        String bizCode1nextVal3 = RedisTemplateUtils.uuid(stringRedisTemplate, "bizCode1", 32);
        System.out.println(bizCode1nextVal3);
        String bizCode2nextVal1 = RedisTemplateUtils.uuid(stringRedisTemplate, "bizCode2", 32);
        System.out.println(bizCode2nextVal1);
        String bizCode2nextVal2 = RedisTemplateUtils.uuid(stringRedisTemplate, "bizCode2", 32);
        System.out.println(bizCode2nextVal2);
    }
}
