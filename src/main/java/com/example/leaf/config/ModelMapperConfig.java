package com.example.leaf.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    private <T,V> Converter<List<T>, List<V>> generateListConverter(Class<T> src, Class<V> dst, ModelMapper mapper) {
        return c -> {
            if (c.getSource() == null)
                return null;
            else
                return c.getSource().stream().map(m -> mapper.map(m, dst)).toList();
        };
    }
}
