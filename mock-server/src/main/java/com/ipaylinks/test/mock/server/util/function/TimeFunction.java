package com.ipaylinks.test.mock.server.util.function;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFunction implements Function {
    private static final String KEY = "__time";

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    public static Object execute(Object... args) {
        String pattern = String.valueOf(args[0]);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }
}
