package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created on 9/5/2017.
 */
@Controller
@RequestMapping("/user")
public class UserServiceController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @RequestMapping("/save")
    public ModelAndView add(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        ModelAndView mav = new ModelAndView("saveUserForm");
        mav.addObject("firstName", firstName);
        mav.addObject("lastName", lastName);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/pdf",headers = "Accept=application/pdf")
    public ModelAndView pdf() {
        ModelAndView mav = new ModelAndView("pdfView");
        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        user.setFirstName("John");
        users.add(user);
        mav.addObject("users", users);
//        mav.setView(new PdfView());
        return mav;
    }
}
