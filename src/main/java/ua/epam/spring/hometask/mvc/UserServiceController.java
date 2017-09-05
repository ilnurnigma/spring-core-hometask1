package ua.epam.spring.hometask.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/5/2017.
 */
@Controller
@RequestMapping("/user")
public class UserServiceController {
    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    // [GET] http://host.com/example/calculate?first=123&second=456
    @RequestMapping("/calculate")
    public ModelAndView calculate(HttpServletRequest request) {
        String first = request.getParameter("first");
        String second = request.getParameter("second");
        int firstInt = Integer.parseInt(first);
        int secondInt = Integer.parseInt(second);
        ModelAndView mav = new ModelAndView("displaySum");
        mav.addObject("sum", Integer.toString(firstInt + secondInt));
        return mav;
    }

    @RequestMapping("/save")
    public ModelAndView add(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        ModelAndView mav = new ModelAndView("saveUserForm");
        mav.addObject("firstName",firstName);
        mav.addObject("lastName",lastName);
        return mav;
    }

}
