package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.webapp.config.ExposedResourceBundleMessageSource;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Qualifier("clanServiceImpl")
    @Autowired
    private ClanService clanService;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationProvider authProvider;
    @Autowired
    private ExposedResourceBundleMessageSource messageSource;
    // INDEX
    @RequestMapping(value = {"/login","/"})
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form, Principal principal) {
        if(principal != null) return new ModelAndView("redirect:/game");
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    // INDEX
    @RequestMapping(value = "/loginRedirect")
    public ModelAndView loginRedirect( Principal principal) {
        User u = userService.findByUsername(principal.getName());
        userService.calculateUserWealth(u.getId());
        return new ModelAndView("redirect:/game");
    }

    // CREATE
    @RequestMapping(value = "/create", method = { RequestMethod.GET })
    public ModelAndView createGET( @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        ModelAndView mav = new ModelAndView("registerForm");
        mav.addObject("userform", new UserForm());
        return mav;

    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView createPOST( @Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult rawErrors
            , HttpServletRequest request) {
        BindingResult errors = prioritizeErrors(form,rawErrors);
        if (errors.hasErrors()) return createGET(form, errors);

        int imageID = Math.abs(new Random().nextInt() % 11);
        final User u = userService.create(form.getUsername(), form.getPassword(),imageID + ".jpg");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());

        authRequest.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = this.authProvider.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView("redirect:/game");
    }

    // PROFILE
    @RequestMapping(value = "/u/{username}", method = { RequestMethod.GET })
    public ModelAndView profile(Principal principal, @PathVariable(value="username") String username){
        ModelAndView mav = new ModelAndView("profile");
        User u = userService.findByUsername(username);

        if(u == null){
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.error("Looking for inexistent user profile: " + username);
            return mav;
        }

        Integer clanid = u.getClanId();
        if (clanid != null) {
            mav.addObject("clan", clanService.getClanById(clanid));
        }

        Set<Factory> factories = new TreeSet(userService.getUserFactories(u.getId()));
        Wealth wealth = userService.getUserWealth(u.getId());
        mav.addObject("user", u);
        mav.addObject("storage",wealth.getStorage());
        mav.addObject("factories",factories);
        mav.addObject("productions",wealth.getProductions());
        mav.addObject("globalRanking",userService.getGlobalRanking(u.getId()));
        return mav;
    }

    @RequestMapping(value = "/myProfile", method = { RequestMethod.GET })
    public ModelAndView myProfile(Principal principal) {
        return profile(principal, principal.getName());
    }

    // GAME
    @RequestMapping(value = "/game", method = { RequestMethod.GET })
    public ModelAndView mainGameView( Principal principal){
//        if(principal == null || principal.getName() == null){
//            return new ModelAndView("redirect:/login");
//        }
        ModelAndView mav = new ModelAndView("game");
        User u = userService.findByUsername(principal.getName());

        if(u == null){
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "401");
            LOGGER.warn("Tried to skip login in game: " + principal.getName());
            return mav;
        }

        Integer clanId = u.getClanId();
        if (clanId != null) {
            mav.addObject("clan", clanService.getClanById(clanId));
        }

        Set<Factory> factories = new TreeSet(userService.getUserFactories(u.getId()));
        Wealth wealth = userService.getUserWealth(u.getId());
        mav.addObject("user", u);
        mav.addObject("storage",wealth.getStorage());
        mav.addObject("factories",factories);
        mav.addObject("productions",wealth.getProductions());
        mav.addObject("messages",getStaticStrings(LocaleContextHolder.getLocale()));
        return mav;
    }

    @RequestMapping(value = "/search", method = { RequestMethod.POST })
    @ResponseBody
    public String search(Principal principal, @RequestParam("search") final String search){
        long userId = userService.findByUsername(principal.getName()).getId();
        Collection<User> users = userService.findByKeyword(search);
        Collection<String> clans = clanService.findByKeyword(search);

        JSONArray usersJson = new JSONArray();
        JSONObject j = new JSONObject();
        users.stream().forEach(user -> usersJson.add(user.getUsername()));
        JSONArray clansJson = new JSONArray();
        clans.stream().forEach(clan -> clansJson.add(clan));
        j.put("type", "search");
        j.put("result", users.size() + clans.size() > 0);
        j.put("users",usersJson);
        j.put("clans",clans);
        return j.toJSONString();
    }

    // ERRORS
    // Whenever you use a wrong route or you are have insufficient privileges
    @RequestMapping(value = "/errors", method = { RequestMethod.GET })
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
            case 500:
            default:
                errorMsg = "500";   // Server Error
                break;
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

    private JSONObject getStaticStrings(Locale locale){
        Set<String> keys = messageSource.getKeys(locale);

        JSONObject j = new JSONObject();
        keys.forEach(k -> j.put(k,messageSource.getMessage(k,null,locale)));
        JSONObject resources = new JSONObject();
        Stream.of(ResourceType.values()).forEach(rType -> resources.put(rType.getId(),rType.getNameCode()));
        JSONObject factories = new JSONObject();
        Arrays.stream(FactoryType.values()).forEach(fType -> factories.put(fType.getId(),fType.getNameCode()));
        j.put("resources",resources);
        j.put("factories",factories);
        return j;
    }
}