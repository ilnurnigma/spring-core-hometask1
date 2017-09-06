package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.util.XmlHelper;

import java.io.IOException;

/**
 * Created on 9/6/2017.
 */
@Controller
@RequestMapping("/batch")
public class BatchLoadingController {

    @Autowired
    private UserService userService;

    @Autowired
    private Jaxb2Marshaller marshaller;

    @Autowired
    public XmlHelper xmlHelper;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("files") MultipartFile[] files, ModelMap modelMap) throws IOException {
        modelMap.addAttribute("files", files);
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.getOriginalFilename().isEmpty()) {
                User user = xmlHelper.getUser(file);
                userService.save(user);
            }

        }
        return "uploadResult";
    }
}
