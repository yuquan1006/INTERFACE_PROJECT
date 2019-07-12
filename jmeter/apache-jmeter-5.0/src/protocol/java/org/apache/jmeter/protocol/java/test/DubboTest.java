package org.apache.jmeter.protocol.java.test;

import com.alibaba.dubbo.config.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QiuHua Yang
 * @version Created on 2017/08/07
 */
public class DubboTest extends AbstractJavaSamplerClient {
    private static final Logger log = LoggerFactory.getLogger(DubboTest.class);

    private ReferenceConfig<Object> referenceConfig;

    /**
     * JMeter界面中展示出此方法所设置的默认参数
     *
     * @return Arguments
     */
    @Override
    public Arguments getDefaultParameters(){
//        logger.debug("getDefaultParameters");
        Arguments args = new Arguments();
        args.addArgument("url", "${url}");
        args.addArgument("registryConfigs", "${registryConfigs}");
        args.addArgument("monitorConfig", "${monitorConfig}");
        args.addArgument("interfaceName", "${interfaceName}");
        args.addArgument("version", "${version}");
        args.addArgument("methodConfigs", "${methodConfigs}");
        args.addArgument("methodName", "${methodName}");
        args.addArgument("methodParameters", "${methodParameters}");
        return args;
    }
    /**
     * 执行runTest()方法前会调用此方法,可放一些初始化代码
     */
    @Override
    public void setupTest(JavaSamplerContext context) {
//        logger.debug("setupTest 创建ReferenceConfig");
        ReferenceConfigThreadLocal.set(new ReferenceConfig<>());
        referenceConfig = ReferenceConfigThreadLocal.get();
    }
    /**
     * 执行runTest()方法后会调用此方法,可放一些资源释放代码
     */
    @Override
    public void teardownTest(JavaSamplerContext context) {
//        logger.debug("teardownTest 销毁ReferenceConfig");
        ReferenceConfigThreadLocal.remove();
    }
    /**
     * 性能测试时的线程运行体，执行的业务方法放在这里
     *
     * @param context javaSamplerContext
     * @return SampleResult
     */
    @Override
    public SampleResult runTest(JavaSamplerContext context) {
//        logger.debug("runTest");
        // 创建SampleResult对象，用于记录执行结果的状态，并返回
        SampleResult sampleResult = new SampleResult();

        // 获取JMeter中输入的用户参数
        String url = context.getParameter("url");
        String registryConfigs = context.getParameter("registryConfigs");
        String monitorConfig = context.getParameter("monitorConfig");
        String interfaceName = context.getParameter("interfaceName");
        String version = context.getParameter("version");
        String methodConfigs = context.getParameter("methodConfigs");
        String methodName = context.getParameter("methodName");
        String methodParameters = context.getParameter("methodParameters");

        //设置请求报文
        String samplerData = "url:" + url +
                "\r\n" + "registryConfigs:" + registryConfigs +
                "\r\n" + "monitorConfig:" + monitorConfig +
                "\r\n" + "interfaceName:" + interfaceName +
                "\r\n" + "version:" + version +
                "\r\n" + "methodConfigs:" + methodConfigs +
                "\r\n" + "methodName:" + methodName +
                "\r\n" + "methodParameters:" + methodParameters;
        sampleResult.setSamplerData(samplerData);

        // 开始 记录 dubbo 接口调用开始时间
        sampleResult.sampleStart();

        //dubbo invoke
        Object result = "";
        try {
            ApplicationConfig application = new ApplicationConfig("jmeter-plugins-dubbo");
            referenceConfig.setApplication(application);
            referenceConfig.setUrl(url);//点对点直连
            referenceConfig.setInterface(interfaceName);
            referenceConfig.setVersion(version);
            if (registryConfigs != null && !registryConfigs.equals("")) {
                List<RegistryConfig> registryConfigList = JSON.parseArray(registryConfigs, RegistryConfig.class);
                referenceConfig.setRegistries(registryConfigList);//通过注册中心连接
            }
            if (monitorConfig != null && !monitorConfig.equals("")) {
                MonitorConfig monitorConfigBean = JSON.parseObject(monitorConfig, MonitorConfig.class);
                referenceConfig.setMonitor(monitorConfigBean);//配置监控
            }
            if (methodConfigs != null && !methodConfigs.equals("")) {
                List<MethodConfig> methodConfigList = JSON.parseArray(methodConfigs, MethodConfig.class);
                referenceConfig.setMethods(methodConfigList);
            } else {
                List<MethodConfig> defaultMethodConfigs = new ArrayList<>();
                MethodConfig defaultMethodConfig = new MethodConfig();
                defaultMethodConfig.setName(methodName);
                defaultMethodConfig.setTimeout(60000);
                defaultMethodConfig.setRetries(0);
                defaultMethodConfigs.add(defaultMethodConfig);
                referenceConfig.setMethods(defaultMethodConfigs);
            }
            Object interfaceObject = referenceConfig.get();

            JSONArray jsonArray = JSON.parseArray(methodParameters);
            int size = jsonArray.size();
            Object[] params = new Object[size];

            Class<?>[] interfaces = interfaceObject.getClass().getInterfaces();

            for (Class interfaceType : interfaces){
                if (interfaceName.equals(interfaceType.getName())) {
                    Method[] methods = interfaceType.getMethods();
                    for (Method method : methods) {
                        if (methodName.equals(method.getName())) {
                            //获取方法参数普通类
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            //获取方法参数泛型类
                            Type[] genericParameterTypes = method.getGenericParameterTypes();

                            if (size == parameterTypes.length) {
                                for (int i = 0; i < size; i++) {
                                    String paramString = (String)jsonArray.get(i);
                                    Object paramObject = null;
                                    Type genericParameterTyp = genericParameterTypes[i];
                                    if (genericParameterTyp instanceof ParameterizedType){
                                        try {
                                            paramObject = JSON.parseObject(paramString, genericParameterTyp);
                                        }catch (Exception e){
                                            log.error("反序列化方法参数为泛型类异常:{}",e);
                                        }
                                    } else {
                                        try {
                                            paramObject = JSON.parseObject(paramString, parameterTypes[i]);
                                        } catch (Exception e) {
                                            log.error("反序列化方法参数为普通类异常:{}",e);
                                        }
                                    }
                                    params[i] = paramObject;
                                }
                            }
                            result = method.invoke(interfaceObject,params);
                        }
                    }
                }
            }


        } catch (Exception e){
            log.error("dubbo请求异常:{}",e);
        }

        // 暂停
        // sampleResult.samplePause();

        // 重启
        // sampleResult.sampleResume();

        // 结束 记录 dubbo 接口调用结束时间
        sampleResult.sampleEnd();

        sampleResult.setResponseCode("200");
        sampleResult.setResponseMessage("OK");
        //设置响应报文
        String resultStr;
        try {
            resultStr = JSON.toJSONString(result);
        } catch (Exception e){
            log.error("序列化返回对象异常:{}",e);
            resultStr = result.toString();
        }
        sampleResult.setResponseData(resultStr,"UTF-8");
        sampleResult.setSuccessful(true);

        // 返回
        return sampleResult;
    }
}
