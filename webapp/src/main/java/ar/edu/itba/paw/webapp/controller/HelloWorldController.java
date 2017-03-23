package ar.edu.itba.paw.webapp.controller;

/**
 * Created by juanfra on 22/03/17.
 */

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    public ModelAndView helloWorld(@RequestParam int id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting",userDao.findById(id).getId());
        mav.addObject("message","have a nice day");
        return mav;
    }

}