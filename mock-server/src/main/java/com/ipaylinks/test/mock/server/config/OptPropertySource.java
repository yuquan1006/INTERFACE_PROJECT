package com.ipaylinks.test.mock.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(name = "env.properties", value = {
    "file:/opt/env.properties"}, ignoreResourceNotFound = false, encoding = "UTF-8")
@Configuration
public class OptPropertySource {
}
