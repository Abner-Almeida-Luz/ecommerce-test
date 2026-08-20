package com.AbnerTest.ecommerce_test.elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContextText {

    @Autowired
    ApplicationContext context;

    @Test
    void testBeans() {
        System.out.println(context.getBeanDefinitionCount());

        System.out.println(context.getBeansOfType(ObjectMapper.class));
    }
}