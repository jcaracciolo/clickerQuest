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


import org.apache.log4j.Logger;

import javax.validation.Valid;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by juanfra on 22/03/17.
 */
@Controller
public class HelloWorldController {

    private static final Logger logger = Logger.getLogger(HelloWorldController.class);

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
        if(u != null) return new ModelAndView("redirect:/" + u.getId() + "/game");
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerPOST(){
        return new ModelAndView("redirect:/create");
    }



    // CREATE
    @RequestMapping(value = "/create", method = { RequestMethod.GET })
    public ModelAndView createGET(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        ModelAndView mav = new ModelAndView("registerForm");
        mav.addObject("userform",new UserForm());
        return mav;

    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView createPOST(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            logger.error("Error at POST /create", new Exception("LOGGER found an error!"));
            return index(form);
        }

        int imageID = Math.abs(new Random().nextInt() % 11);
        final User u = userService.create(form.getUsername(), form.getPassword(),imageID + ".jpg");

        if(u == null) {
            //TODO Correct error handling
            // First approach on error handling:
            logger.error("User is null", new Exception("LOGGER found an error!"));
            return new ModelAndView("redirect:/create");
        } else {
            if(logger.isDebugEnabled()) logger.debug("LOGGER: POST /create is successful");
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