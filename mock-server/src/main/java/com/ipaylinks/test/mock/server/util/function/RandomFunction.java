package com.ipaylinks.test.mock.server.util.function;

import java.util.concurrent.ThreadLocalRandom;

public class RandomFunction implements Function {
    private static final String KEY = "__Random";

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    public static Object execute(Object... args) {
        String minimum = String.valueOf(args[0]);
        String maximum = String.valueOf(args[1]);
        long min = Long.parseLong(minimum.trim());
        long max = Long.parseLong(maximum.trim());
        long rand = ThreadLocalRandom.current().nextLong(min, max+1);
        return Long.toString(rand);
    }
}
