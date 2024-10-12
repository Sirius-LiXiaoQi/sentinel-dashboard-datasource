package com.sirius.sentinel.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Sirius
 * @since 1.8.6
 */
@Configuration
public class NacosConfig {

    private final static ObjectMapper jmapper = new ObjectMapper();

    @Bean("flowRuleEntityEncoder")
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return s -> {
            try {
                return jmapper.writeValueAsString(s);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean("flowRuleEntityDecoder")
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> {
            try {
                return jmapper.readValue(s, new TypeReference<List<FlowRuleEntity>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean("degradeRuleEntityEncoder")
    public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
        return s -> {
            try {
                return jmapper.writeValueAsString(s);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean("degradeRuleEntityDecoder")
    public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
        return s -> {
            try {
                return jmapper.readValue(s, new TypeReference<List<DegradeRuleEntity>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception {
        String localVariableNacosServerAddr = System.getProperty("nacos.server.addr");
        String serverAddr = localVariableNacosServerAddr == null || "".equals(localVariableNacosServerAddr) ? "localhost" : localVariableNacosServerAddr;

        ConfigService configService = NacosFactory.createConfigService(serverAddr);
        return configService;
    }
}
