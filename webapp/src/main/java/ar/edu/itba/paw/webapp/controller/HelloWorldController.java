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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
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
    @Autowired
    private AuthenticationProvider authProvider;

    // INDEX
    @RequestMapping("/login")
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form, Principal principal) {
        if(principal != null){
            return new ModelAndView("redirect:/game");
        }
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerPOST(){
        return new ModelAndView("redirect:/create");
    }



    // CREATE
    @RequestMapping(value = "/create", method = { RequestMethod.GET })
    public ModelAndView createGET( @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
         ModelAndView mav = new ModelAndView("registerForm");
        mav.addObject("userform",new UserForm());
        return mav;

    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView createPOST( @Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult rawErrors, HttpServletRequest request) {
        BindingResult errors = prioritizeErrors(form,rawErrors);
        if (errors.hasErrors()) {
            return createGET(form,errors);
        }
        int imageID = Math.abs(new Random().nextInt() % 11);
        final User u = userService.create(form.getUsername(), form.getPassword(),imageID + ".jpg");


        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());

        authRequest.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = this.authProvider.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView("redirect:/game");
    }


    // GAME
    @RequestMapping(value = "/game")
    public ModelAndView mainGameView( Principal principal){
        if(principal == null || principal.getName() == null){
            return new ModelAndView("redirect:/login");
        }
        ModelAndView mav = new ModelAndView("game");
        User u = userService.findByUsername(principal.getName());
        if(u == null){
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.error("{} tried to skip login",u.getId());
            return mav;
        }
        Set<Factory> factories = new TreeSet(userService.getUserFactories(u.getId()));

        mav.addObject("user", userService.findById(u.getId()));
        mav.addObject("storage",userService.getUserStorage(u.getId()));
        mav.addObject("factories",factories);
        mav.addObject("productions",userService.getUserProductions(u.getId()));
        return mav;
    }

    @RequestMapping(value = "/buyFactory")
    @ResponseBody
    public ModelAndView purchaseFactory(Principal principal, @RequestParam("factoryId") final int factoryId){
        userService.purchaseFactory(userService.findByUsername(principal.getName()).getId(), FactoryType.fromId(factoryId));
        return new ModelAndView("redirect:/game");
    }

    @RequestMapping(value = "/upgradeFactory")
    @ResponseBody
    public ModelAndView upgradeFactory(Principal principal, @RequestParam("factoryId") final int factoryId){
        userService.purchaseUpgrade(userService.findByUsername(principal.getName()).getId(), FactoryType.fromId(factoryId));
        return new ModelAndView("redirect:/game");
    }

    @RequestMapping(value = "/buyFromMarket", method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView buyFromMarket(Principal principal, @RequestParam("resourceId") final String resourceName,
                                      @RequestParam("quantity")final double quantity) {
        // DO STUFF

        System.out.println("Buying: " + userService.findByUsername(principal.getName()).getId() + " " + resourceName + " " + quantity);
        return null;
    }

    @RequestMapping(value = "/sellToMarket", method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView sellToMarket(Principal principal, @RequestParam("resourceId") final String resourceName,
                                      @RequestParam("quantity")final double quantity) {
        // DO STUFF
        System.out.println("Selling: " + userService.findByUsername(principal.getName()).getId() + " " + resourceName + " " + quantity);
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

    private BindingResult prioritizeErrors(UserForm form, BindingResult errors){
        BindingResult prioritizedErrors = errors;
//        BindingResult prioritizedErrors = new BeanPropertyBindingResult(errors.getTarget(),errors.getObjectName());
//        errors.getFieldErrors("username").stream().filter(objectError -> !form.getUsername().isEmpty() ||
//                Stream.of(objectError.getCodes()).anyMatch(x -> x.equals("NotBlank")))
//                .forEach(objectError -> prioritizedErrors.addError(objectError));
//        errors.getFieldErrors("password").stream().filter(objectError -> !form.getPassword().isEmpty() ||
//                Stream.of(objectError.getCodes()).anyMatch(x -> x.equals("NotBlank")))
//                .forEach(objectError -> prioritizedErrors.addError(objectError));
        if(!form.getPassword().equals(form.getRepeatPassword())){
            prioritizedErrors.addError(form.getMismatchedPasswordsError());
        }
        if(errors.getFieldErrorCount("username")==0 && userService.findByUsername(form.getUsername()) != null){
            prioritizedErrors.addError(form.getUsedUsernameError());
        }

        return prioritizedErrors;
    }
}