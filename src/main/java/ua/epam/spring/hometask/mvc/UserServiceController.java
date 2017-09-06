package ua.epam.spring.hometask.mvc;

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

    @Autowired
    private Jaxb2Marshaller marshaller;

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
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        ModelAndView mav = new ModelAndView("saveUserForm");
        mav.addObject("firstName", firstName);
        mav.addObject("lastName", lastName);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("files") MultipartFile[] files, ModelMap modelMap) throws IOException {
        modelMap.addAttribute("files", files);
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.getOriginalFilename().isEmpty()) {
                User user = (User) marshaller.unmarshal(new StreamSource(file.getInputStream()));
                userService.save(user);
            }

        }
        return "uploadResult";
    }

    @RequestMapping(value = "/upload")
    public String upload() {
        return "upload";
    }

    @RequestMapping(value = "/pdf")
    public ModelAndView pdf() {
        ModelAndView mav = new ModelAndView("pdfView");
        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        user.setFirstName("John");
        users.add(user);
        mav.addObject("users", users);
        mav.setView(new PdfView());
        return mav;
    }
}
