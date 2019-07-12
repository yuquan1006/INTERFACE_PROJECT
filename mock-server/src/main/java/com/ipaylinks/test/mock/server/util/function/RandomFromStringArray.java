package com.ipaylinks.test.mock.server.util.function;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author QiuHua Yang
 * @version Created on 2018/4/13
 */
public class RandomFromStringArray implements Function{
    private static final String KEY = "__RandomFromStringArray";

    public static Object execute(Object... args) {
        Object result = null;
        if (null != args && args.length > 0){
            int min = 0;
            int max = args.length - 1;
            int index = random(min,max);
            result = args[index];
        }
        return result;
    }

    private static int  random(int minimum, int maximum){
        return ThreadLocalRandom.current().nextInt(minimum, maximum+1);
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }
}
