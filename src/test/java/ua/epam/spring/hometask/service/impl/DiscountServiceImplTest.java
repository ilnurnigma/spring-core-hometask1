package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountServiceImplTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getDiscount() throws Exception {
        DiscountServiceImpl discountService = new DiscountServiceImpl();
        discountService.getDiscount(null, new Event(), LocalDateTime.now(), 1L);
    }

}