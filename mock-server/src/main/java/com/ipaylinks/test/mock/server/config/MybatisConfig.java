package com.ipaylinks.test.mock.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author QiuHua Yang
 * @version Created on 2017/11/20
 */
@Configuration
@MapperScan(basePackages = "com.ipaylinks.test.mock.server.dao.mapper")
public class MybatisConfig {

}