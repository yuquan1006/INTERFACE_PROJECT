package com.ipaylinks.test.mock.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author yangqiuhua
 */
@Configuration
public class MockMvcConfig{
    @Autowired
    private WebApplicationContext wac;

    @Bean(name = "mockMvc")
    public MockMvc mockMvc(){
        return MockMvcBuilders.webAppContextSetup(wac).build();
    }

}
