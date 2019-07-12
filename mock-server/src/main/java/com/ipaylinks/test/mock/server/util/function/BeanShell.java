package com.ipaylinks.test.mock.server.util.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author QiuHua Yang
 * @version Created on 2018/5/16
 */
public class BeanShell {
    private final static Logger logger = LoggerFactory.getLogger(BeanShell.class);

    private static BeanShellInterpreter bshInterpreter = new BeanShellInterpreter(null, logger);

    public static Object eval(String script){
        Object result = "";
        try {
            result = bshInterpreter.eval(script);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void set(String name, Object value){
        try {
            bshInterpreter.set(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object get(String name){
        Object result = "";
        try {
            result = bshInterpreter.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object source(String filename){
        Object result = "";
        try {
            result = bshInterpreter.source(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        BeanShellInterpreter bshInterpreter = new BeanShellInterpreter(null, logger);

        Object object = bshInterpreter.eval("log.info(\"haha\");" +
                "return \"yangqiuhua\";");
        System.out.println(object);
    }
}
