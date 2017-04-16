package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by juanfra on 22/03/17.
 */
@Controller
public class HelloWorldController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;


    // INDEX
    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message","Congratulations, you successfully setup the project (quite an achievement)");
        return mav;
    }

    @RequestMapping(value = "/startGame", method = { RequestMethod.GET })
    public ModelAndView startGamePOST(@RequestParam("username") final String username){
        User u = userService.findByUsername(username);
        if(u!= null){
            System.out.println("username: " + username);
            return new ModelAndView("redirect:/" + u.getId() + "/game");
        }
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerPOST(){
        System.out.println("redirecting to /register...");
        return new ModelAndView("redirect:/create");
    }



    // CREATE
    @RequestMapping(value = "/create", method = { RequestMethod.GET })
    public ModelAndView createGET(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        return new ModelAndView("registerForm");

    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView createPOST(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }

        int imageID = Math.abs(new Random().nextInt() % 11);
        final User u = userService.create(form.getUsername(), form.getPassword(),imageID + ".jpg");
        if(u==null){
            //TODO Correct error handling
            return new ModelAndView("redirect:/create");
        } else {
            return new ModelAndView("redirect:/" + u.getId() + "/game");
        }
    }



    // GAME
    @RequestMapping(value = "/{userId}/game")
    public ModelAndView mainGameView(@PathVariable long userId){
        ModelAndView mav = new ModelAndView("game");

        Set<Factory> factories = new TreeSet(userService.getUserFactories(userId));

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