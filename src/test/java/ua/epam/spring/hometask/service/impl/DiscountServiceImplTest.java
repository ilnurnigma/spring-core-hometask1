package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.DiscountService;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DiscountServiceImplTest {
    @Autowired
    private ApplicationContext ctx;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getDiscount() throws Exception {
        DiscountService discountService = ctx.getBean("discountServiceImpl", DiscountService.class);
        discountService.getDiscount(null, new Event(), LocalDateTime.now(), 1L);
    }

}