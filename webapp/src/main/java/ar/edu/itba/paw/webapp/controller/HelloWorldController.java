package ar.edu.itba.paw.webapp.controller;

/**
 * Created by juanfra on 22/03/17.
 */

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HelloWorldController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/user")
    public ModelAndView helloWorld(@RequestParam("userId") int userId) {
        final ModelAndView mav = new ModelAndView("user");
        mav.addObject("username",userDao.findById(userId).getUsername());
        mav.addObject("userId",userDao.findById(userId).getId());
        return mav;
    }

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message","Congratulations, you successfully setup the project (quite an achievement)");
        return mav;
    }

    @RequestMapping(value = "/create", method = { RequestMethod.GET })
    public ModelAndView createGET(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        return new ModelAndView("registerForm");

    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView createPOST(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }

        final User u = userService.create(form.getUsername(), form.getPassword());
        return new ModelAndView("redirect:/user?userId=" + u.getId());
    }
}