package com.ipaylinks.test.mock.server.util.function;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author QiuHua Yang
 * @version Created on 2018/4/13
 */
public class RandomString implements Function{
    private static final String KEY = "__RandomString";

    public static Object execute(Object... args) {
        String arg1 = String.valueOf(args[0]);
        String arg2 = String.valueOf(args[1]);
        return RandomStringUtils.random(Integer.parseInt(arg1.trim()), arg2);
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }
}
