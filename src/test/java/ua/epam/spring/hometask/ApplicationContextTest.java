package ua.epam.spring.hometask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class ApplicationContextTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void givenApplicationContextReturnBeanImplementations() {
        assertTrue(ctx.getBean("bookingServiceImpl") instanceof BookingServiceImpl);
    }

    @Test
    public void givenApplicationContextReturnBeanImplementations2() {
        Object bookingServiceImpl = ctx.getBean("bookingServiceImpl");



        assertTrue(bookingServiceImpl instanceof BookingServiceImpl);
    }

}