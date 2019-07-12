package com.ipaylinks.test.mock.server.util.function;

import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ServiceLoader;

/**
 * @author QiuHua Yang
 * @version Created on 2018/4/14
 */
public class FunctionRegister {
    public static void registerFunctionToContext(StandardEvaluationContext ctx) {
        try {
            ServiceLoader<Function> functions = ServiceLoader.load(Function.class);
            for (Function function : functions){
                String key = function.getReferenceKey();
                Method method = function.getClass().getDeclaredMethod("execute", Object[].class);
                ctx.registerFunction(key, method);
            }

            Method generateCard = CardGenerator.class.getDeclaredMethod("generateCard", String.class, String.class);
            ctx.registerFunction("__generateCard", generateCard);

            Method md5Sign = MD5Function.class.getDeclaredMethod("sign", String.class, String.class);
            ctx.registerFunction("__MD5", md5Sign);

            Method beanShell = BeanShell.class.getDeclaredMethod("eval", String.class);
            ctx.registerFunction("__BeanShell", beanShell);

            Method beanShellSet = BeanShell.class.getDeclaredMethod("set", String.class, Object.class);
            ctx.registerFunction("__BeanShellSet", beanShellSet);

            Method beanShellGet = BeanShell.class.getDeclaredMethod("get", String.class);
            ctx.registerFunction("__BeanShellGet", beanShellGet);

            Method beanShellSource = BeanShell.class.getDeclaredMethod("source", String.class);
            ctx.registerFunction("__BeanShellSource", beanShellSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
