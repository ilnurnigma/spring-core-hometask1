package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

@Controller
@RequestMapping("/user")
public class UserServiceRESTController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

}
