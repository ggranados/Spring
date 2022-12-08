package edu.ggranados.spring.faas.unitconversion.weather;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class WeatherController {

    @Bean
    public Function<String, String> hello(){
        return (name -> "Hello " + name + " to UnitConversionFaasTool");
    }
}
