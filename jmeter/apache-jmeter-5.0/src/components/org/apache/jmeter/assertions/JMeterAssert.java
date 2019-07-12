package org.apache.jmeter.assertions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JMeterAssert {
    private static final Logger log = LoggerFactory.getLogger(JMeterAssert.class);

    public static String assertEquals(Map<String, Object> actualMap, Map<String, Object> expectedMap){
        StringBuilder failureMessageBuilder = new StringBuilder();
        for(Map.Entry<String, Object> expectedMapEntry : expectedMap.entrySet()){
            String key = expectedMapEntry.getKey();
            String actualValue = actualMap.get(key)==null?"": String.valueOf(actualMap.get(key));
            String expectedValue = expectedMapEntry.getValue() == null?"": String.valueOf(expectedMapEntry.getValue());
            if (!actualValue.equals(expectedValue)) {
                failureMessageBuilder.append("\r\n ");
                //实际结果
                failureMessageBuilder.append("actual ");
                failureMessageBuilder.append(key);
                failureMessageBuilder.append("=[");
                failureMessageBuilder.append(actualValue);
                failureMessageBuilder.append("]");
                //不等于
                failureMessageBuilder.append(" is not equals to ");
                //预期结果
                failureMessageBuilder.append("expected ");
                failureMessageBuilder.append(key);
                failureMessageBuilder.append("=[");
                failureMessageBuilder.append(expectedValue);
                failureMessageBuilder.append("]");
            }
        }
        String failureMessage = failureMessageBuilder.toString();
        log.debug("failureMessage:{}", failureMessage);
        return failureMessage;
    }

}
