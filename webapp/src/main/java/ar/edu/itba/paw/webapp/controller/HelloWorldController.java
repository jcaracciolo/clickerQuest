package ar.edu.itba.paw.webapp.controller;

/**
 * Created by juanfra on 22/03/17.
 */

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/user/{userId}")
    public ModelAndView helloWorld(@PathVariable int userId) {
        final ModelAndView mav = new ModelAndView("user");
        //TODO no me queda muy claro que vamos a guardar en el mav, ni si es necesario el userId
        mav.addObject("username",userDao.findById(userId).getUsername());
        mav.addObject("userId",userDao.findById(userId).getId());
        return mav;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("message","Congratulations, you successfully setup the project (quite an achievement)");
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String username) {
        final User u = userService.create(username,"password");
        return new ModelAndView("redirect:/user/" + u.getUsername());
    }
}