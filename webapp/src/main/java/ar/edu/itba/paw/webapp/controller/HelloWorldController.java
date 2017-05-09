package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by juanfra on 22/03/17.
 */
@Controller
public class HelloWorldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

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
    public ModelAndView startGame(@RequestParam("username") final String username){
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
            return new ModelAndView("redirect:/create");
        }

        int imageID = Math.abs(new Random().nextInt() % 11);
        final User u = userService.create(form.getUsername(), form.getPassword(),imageID + ".jpg");

        if(u == null) {
            //TODO Correct error handling
            // First approach on error handling:
            ModelAndView modelAndView = new ModelAndView("redirect:/create");
            modelAndView.addObject("error","userAlreadyInUse");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/" + u.getId() + "/game");
        }
    }

    // GAME
    @RequestMapping(value = "/{userId}/game")
    public ModelAndView mainGameView(@PathVariable long userId){
        ModelAndView mav = new ModelAndView("game");
        if(userService.findById(userId) == null){
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.error("{} tried to skip login",userId);
            return mav;
        }
        Set<Factory> factories = new TreeSet(userService.getUserFactories(userId));

        mav.addObject("user", userService.findById(userId));
        mav.addObject("storage",userService.getUserStorage(userId));
        mav.addObject("factories",factories);
        mav.addObject("productions",userService.getUserProductions(userId));
        return mav;
    }

    @RequestMapping(value = "/{userId}/buyFactory")
    public ModelAndView purchaseFactory(@PathVariable long userId, @RequestParam("factoryId") final int factoryId){
        userService.purchaseFactory(userId, FactoryType.fromId(factoryId));
        return new ModelAndView("redirect:/" + userId + "/game");
    }

    @RequestMapping(value = "/{userId}/upgradeFactory")
    public ModelAndView upgradeFactory(@PathVariable long userId, @RequestParam("factoryId") final int factoryId){
        userService.purchaseUpgrade(userId, FactoryType.fromId(factoryId));
        return new ModelAndView("redirect:/" + userId + "/game");
    }

    @RequestMapping(value = "/{userId}/buyFromMarket", method = { RequestMethod.POST })
    public ModelAndView buyFromMarket(@PathVariable long userId, @RequestParam("resourceId") final String resourceName,
                                      @RequestParam("quantity")final double quantity) {
        // DO STUFF
        System.out.println("Buying: " + userId + " " + resourceName + " " + quantity);
        return null;
    }

    @RequestMapping(value = "/{userId}/sellToMarket", method = { RequestMethod.POST })
    public ModelAndView sellToMarket(@PathVariable long userId, @RequestParam("resourceId") final String resourceName,
                                      @RequestParam("quantity")final double quantity) {
        // DO STUFF
        System.out.println("Selling: " + userId + " " + resourceName + " " + quantity);
        return null;
    }

    // ERRORS
    @RequestMapping(value = "/errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "400"; // Bad Request
                break;
            }
            case 401: {
                errorMsg = "401"; // Unauthorized
                break;
            }
            case 404: {
                errorMsg = "404"; // Not Found
                break;
            }
            case 500: {
                errorMsg = "500";   // Server Error
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}