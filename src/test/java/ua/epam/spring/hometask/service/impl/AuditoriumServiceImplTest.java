package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.service.AuditoriumService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuditoriumServiceImplTest {
    @Autowired
    private ApplicationContext ctx;
    private AuditoriumService auditoriumService;

    @Before
    public void setUp() throws Exception {
        auditoriumService = ctx.getBean("auditoriumServiceImpl", AuditoriumService.class);
    }

    @Test
    public void getAll() throws Exception {
        System.out.println(auditoriumService.getAll());
    }

    @Test
    public void getByName() throws Exception {
    }

}