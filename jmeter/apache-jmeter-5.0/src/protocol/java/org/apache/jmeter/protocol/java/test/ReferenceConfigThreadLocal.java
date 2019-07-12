package org.apache.jmeter.protocol.java.test;

import com.alibaba.dubbo.config.ReferenceConfig;

/**
 * @author QiuHua Yang
 * @version Created on 2018/3/21
 */
public class ReferenceConfigThreadLocal {
    /**
     * 本地线程共享对象
     */
    private static final ThreadLocal<ReferenceConfig<Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(ReferenceConfig<Object> referenceConfig) {
        THREAD_LOCAL.set(referenceConfig);
    }

    public static ReferenceConfig<Object> get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
