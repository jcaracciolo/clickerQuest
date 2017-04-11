package ar.edu.itba.paw.webapp.controller;

/**
 * Created by juanfra on 22/03/17.
 */

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
public class HelloWorldController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public ModelAndView helloWorld(@RequestParam("userId") int userId) {
        final ModelAndView mav = new ModelAndView("user");
        User user = userService.findById(userId);
        mav.addObject("username",user.getUsername());
        mav.addObject("userId",user.getId());
        mav.addObject("userIcon",userService.getProfileImage(user.getId()));
        return mav;
    }

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message","Congratulations, you successfully setup the project (quite an achievement)");
        return mav;
    }

    @RequestMapping(value = "/startGame", method = { RequestMethod.POST })
    public ModelAndView startGamePOST(@RequestParam("username") final String username){
        // TODO: get id of user with username
        System.out.println("username: " + username);
        return new ModelAndView("redirect:/" + "1" + "/game");
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerPOST(){
        System.out.println("redirecting to /register...");
        return new ModelAndView("redirect:/create");
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

        final User u = userService.create(form.getUsername(), form.getPassword(),"1.jpg");
        return new ModelAndView("redirect:/" + u.getId() + "/game");
    }

    @RequestMapping(value = "/{userId}/game")
    public ModelAndView mainGameView(@PathVariable long userId){
        ModelAndView mav = new ModelAndView("game");

        List<Factory> factories = new ArrayList<>(userService.getUserFactories(userId));
        Collections.sort(factories, (f1,f2) -> f1.getType().getId() - f2.getType().getId());

        mav.addObject("user", userService.findById(userId));
        mav.addObject("storage",userService.getUserStorage(userId));
        mav.addObject("factories",factories);
        mav.addObject("productions",userService.getUserProductions(userId));
        return mav;
    }

    @RequestMapping(value = "/{userId}/buyFactory")
    public ModelAndView purchaseDemo(@PathVariable long userId, @RequestParam("factoryId") final int factoryId){
        userService.purchaseFactory(userId, FactoryType.fromId(factoryId));
        return new ModelAndView("redirect:/" + userId + "/game");
    }

}