package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.Event;
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

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @RequestMapping(value = "/delete/{email}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("email") String email) {
        userService.remove(userService.getUserByEmail(email));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user) {
        userService.remove(userService.getUserByEmail(user.getEmail()));
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
