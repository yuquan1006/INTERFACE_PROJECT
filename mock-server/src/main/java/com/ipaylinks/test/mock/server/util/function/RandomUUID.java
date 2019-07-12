package com.ipaylinks.test.mock.server.util.function;

import java.util.UUID;

/**
 * @author QiuHua Yang
 * @version Created on 2018/4/26
 */
public class RandomUUID implements Function{
    private static final String KEY = "__randomUUID";

    public static Object execute(Object... args) {
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }
}
