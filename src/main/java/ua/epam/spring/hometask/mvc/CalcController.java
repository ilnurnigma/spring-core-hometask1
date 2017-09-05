package ua.epam.spring.hometask.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.service.BookingService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/5/2017.
 */
@Controller
public class CalcController {
    @Autowired
    BookingService bookingService;

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

    @RequestMapping("/calculate2")
    public ModelAndView calculate2(HttpServletRequest request) {
        String first = request.getParameter("first");
        String second = request.getParameter("second");
        int firstInt = Integer.parseInt(first);
        int secondInt = Integer.parseInt(second);
        ModelAndView mav = new ModelAndView("displaySum2");
        mav.addObject("sum", Integer.toString(firstInt + secondInt));
        return mav;
    }
}
