//package org.albert.websocketsonetoonechat.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        return mapper;
//    }
//
////    @Bean
////    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
////        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
////        builder.modulesToInstall(new JavaTimeModule());
////        return builder;
////    }
//}
