package com.yukms.easy.test;

import com.yukms.easy.test.mock.mock.MockDataAspectJ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yukms 763803382@qq.com 2019/6/28 14:34
 */
@Configuration
public class TestConfiguration {
    @Bean
    public MockDataAspectJ getMockAspectJ() {
        return new MockDataAspectJ();
    }
}
