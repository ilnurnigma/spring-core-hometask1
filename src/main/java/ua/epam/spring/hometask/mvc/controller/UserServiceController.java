package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.UserService;

/**
 * Created on 9/5/2017.
 */
@Controller
public class UserServiceController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @RequestMapping("/userServiceForm")
    public String saveUserForm() {
        return "userServiceForm";
    }

    @RequestMapping(path = "/saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email) {

        User user = new User(firstName, lastName, email);
        userService.save(user);

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", "User " + firstName + " " + lastName + " was saved.");
        return mav;
    }

    @RequestMapping(path = "/getUserByEmail", method = RequestMethod.POST)
    public ModelAndView getUserByEmail(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);

        String msg;
        if (user != null) {
            msg = "Found user " + user.getFirstName() + " " + user.getLastName() + ".";
        } else {
            msg = "Did not find user with an email: " + email;
        }

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", msg);
        return mav;
    }

    @RequestMapping(path = "/deleteUserByEmail", method = RequestMethod.POST)
    public ModelAndView deleteUserByEmail(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);

        String msg;
        if (user != null) {
            msg = "Found and deleted user " + user.getFirstName() + " " + user.getLastName() + ".";
            userService.remove(user);
        } else {
            msg = "Did not find user with an email: " + email;
        }

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", msg);
        return mav;
    }

}
