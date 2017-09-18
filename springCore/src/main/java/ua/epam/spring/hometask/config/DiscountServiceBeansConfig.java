package ua.epam.spring.hometask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.impl.DiscountServiceImpl;
import ua.epam.spring.hometask.service.strategies.BirthdayDiscountStrategy;
import ua.epam.spring.hometask.service.strategies.DiscountStrategy;
import ua.epam.spring.hometask.service.strategies.Every10thTicketDiscountStrategy;

import java.util.ArrayList;

@Configuration
public class DiscountServiceBeansConfig {
    @Bean
    public DiscountService discountService() {
        ArrayList<DiscountStrategy> discountStrategies = new ArrayList<>();
        discountStrategies.add(every10thDiscountStrategy());
        discountStrategies.add(birthdayDiscountStrategy());
        return new DiscountServiceImpl(discountStrategies);
    }

    @Bean
    public DiscountStrategy every10thDiscountStrategy() {
        return new Every10thTicketDiscountStrategy();
    }

    @Bean
    public DiscountStrategy birthdayDiscountStrategy() {
        return new BirthdayDiscountStrategy();
    }
}
