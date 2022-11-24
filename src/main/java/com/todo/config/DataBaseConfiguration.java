package com.todo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:db.properties")
@Configuration
public class DataBaseConfiguration {
    @Value("${CONNECTION_STRING}")
    public String CONNECTION_STRING;
    @Value("${USER_NAME}")
    public String USER_NAME;
    @Value("${PASSWORD}")
    public String PASSWORD;
    @Value("${JDBC_DRIVER}")
    public String JDBC_DRIVER;
}
