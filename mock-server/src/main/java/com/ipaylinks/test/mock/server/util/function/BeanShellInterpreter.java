package com.ipaylinks.test.mock.server.util.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QiuHua Yang
 * @version Created on 2018/4/16
 */
public class BeanShellInterpreter {
    private static final Logger log = LoggerFactory.getLogger(BeanShellInterpreter.class);

    private static final Method bshGet;

    private static final Method bshSet;

    private static final Method bshEval;

    private static final Method bshSource;

    private static final Class<?> bshClass;

    private static final String BSH_INTERPRETER = "bsh.Interpreter";

    static {
        // Temporary copies, so can set the final ones
        Method get = null;
        Method eval = null;
        Method set = null;
        Method source = null;
        Class<?> clazz = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            clazz = loader.loadClass(BSH_INTERPRETER);
            Class<String> string = String.class;
            Class<Object> object = Object.class;

            get = clazz.getMethod("get",
                    new Class[] { string });
            eval = clazz.getMethod("eval",
                    new Class[] { string });
            set = clazz.getMethod("set",
                    new Class[] { string, object });
            source = clazz.getMethod("source",
                    new Class[] { string });
        } catch (ClassNotFoundException|SecurityException | NoSuchMethodException e) {
            log.error("BeanShell Interpreter not found", e);
        } finally {
            bshEval = eval;
            bshGet = get;
            bshSet = set;
            bshSource = source;
            bshClass = clazz;
        }
    }

    /**
     *  This class is not serialised
     */
    private Object bshInstance = null; // The interpreter instance for this class

    private final String initFile; // Script file to initialize the Interpreter with

    private final Logger logger; // Logger to use during initialization and script run

    private final Map<String, Object> initMap;

    public BeanShellInterpreter(){
        this(null, null);
    }

    /**
     *
     * @param _initFile initialisation file
     * @param _log logger to pass to interpreter
     * @throws ClassNotFoundException when beanShell can not be instantiated
     */
    public BeanShellInterpreter(String _initFile, Logger _log){
        initFile = _initFile;
        logger = _log;

        initMap = new HashMap<>();
        try {
            init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Called from ctor, so must be private (or final, but it does not seem useful elsewhere)
     *
     * @throws ClassNotFoundException e
     */
    private void init() throws ClassNotFoundException {
        if (bshClass == null) {
            throw new ClassNotFoundException(BSH_INTERPRETER);
        }
        try {
            bshInstance = bshClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Can't instantiate BeanShell", e);
            throw new ClassNotFoundException("Can't instantiate BeanShell", e);
        }
        if (logger != null) {// Do this before starting the script
            try {
                set("log", logger);
            } catch (Exception e) {
                log.warn("Can't set logger variable", e);
            }
        }
        if (initFile != null && initFile.length() > 0) {
            String fileToUse=initFile;
            // Check file so we can distinguish file error from script error
            File in = new File(fileToUse);
            if (!in.exists()){
                log.warn("Cannot find init file: "+initFile);
            }
            if (!in.canRead()) {
                log.warn("Cannot read init file: "+fileToUse);
            }
            try {
                source(fileToUse);
            } catch (Exception e) {
                log.warn("Cannot source init file: "+fileToUse,e);
            }
        }
        if (initMap != null) {// Do this before starting the script
            initMap.forEach((key, value) -> {
                if (key != null && value != null){
                    try {
                        set(key, value);
                    }  catch (Exception e) {
                        log.warn("Can't set initMap to variable", e);
                    }
                }
            });
        }
    }

    /**
     * Resets the BeanShell interpreter.
     *
     * @throws ClassNotFoundException if interpreter cannot be instantiated
     */
    public void reset() throws ClassNotFoundException {
        init();
    }

    private Object bshInvoke(Method m, Object[] o, boolean shouldLog) throws Exception {
        Object r = null;
        final String errorString = "Error invoking bsh method: ";
        try {
            r = m.invoke(bshInstance, o);
        } catch (IllegalArgumentException | IllegalAccessException e) { // Programming error
            final String message = errorString + m.getName();
            log.error(message);
            throw new Exception(message, e);
        } catch (Exception e) { // Can occur at run-time
            // could be caused by the bsh Exceptions:
            // EvalError, ParseException or TargetError
            String message = errorString + m.getName();
            Throwable cause = e.getCause();
            if (cause != null) {
                message += "\t" + cause.getLocalizedMessage();
            }

            if (shouldLog) {
                log.error(message);
            }
            throw new Exception(message, e);
        }
        return r;
    }

    public Object eval(String s) throws Exception {
        return bshInvoke(bshEval, new Object[] { s }, true);
    }

    public Object evalNoLog(String s) throws Exception {
        return bshInvoke(bshEval, new Object[] { s }, false);
    }

    public Object set(String s, Object o) throws Exception {
        return bshInvoke(bshSet, new Object[] { s, o }, true);
    }

    public Object set(String s, boolean b) throws Exception {
        return bshInvoke(bshSet, new Object[] { s, Boolean.valueOf(b) }, true);
    }

    public Object source(String s) throws Exception {
        return bshInvoke(bshSource, new Object[] { s }, true);
    }

    public Object get(String s) throws Exception {
        return bshInvoke(bshGet, new Object[] { s }, true);
    }

    /**
     *  For use by Unit Tests
     *
     * @return boolean
     */
    public static boolean isInterpreterPresent(){
        return bshClass != null;
    }
}
