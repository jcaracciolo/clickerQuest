package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.webapp.config.ExposedResourceBundleMessageSource;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
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
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form, Principal principal,final BindingResult errors) {
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
    public ModelAndView createPOST( @Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult rawErrors, HttpServletRequest request) {
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
    @RequestMapping(value = "/{username}", method = { RequestMethod.GET })
    public ModelAndView profile(Principal principal, @PathVariable(value="username") String username){
        ModelAndView mav = new ModelAndView("profile");
        User u = userService.findByUsername(username);

        if(u == null){
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.error("Looking for unexistant user profile: " + username);
            return mav;
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

    // CLAN
    @RequestMapping(value = "/clan/{clanName}", method = { RequestMethod.GET })
    public ModelAndView clan(Principal principal, @PathVariable(value="clanName") String clanName){
        ModelAndView mav = new ModelAndView("clan");

        Clan clan = clanService.getClanByName(clanName);
        if (clan == null) {
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.info("Clan does not exist " + clanName);
            return mav;
        }

        User u = userService.findByUsername(principal.getName());
        if(u == null) {
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.warn("Tried to skip login in clan: " + principal.getName());
            return mav;
        }

        mav.addObject("user", u);
        mav.addObject("clan", clan);
        return mav;
    }

    @RequestMapping(value = "/createClan", method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView createClan(Principal principal, @RequestParam("clanName") final String clanName){
        ModelAndView mav = new ModelAndView("clan");
        Clan clan = clanService.createClan(clanName);

        if (clan == null) {
            // TODO: clan is null (return 'clan already exists')
        }

        User u = userService.findByUsername(principal.getName());

        if(u == null){
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.warn("Creating clan without username " + principal.getName());
            return mav;
        }
        clanService.addUserToClan(clan.getId(), u.getId());

        mav.addObject("user", u);
        mav.addObject("clan", clan);
        return mav;
    }

    @RequestMapping(value = "/leaveClan", method = { RequestMethod.POST })
    @ResponseBody
    public void leaveClan(Principal principal, @RequestParam("clanName") final String clanName){
        ModelAndView mav = new ModelAndView("clan");

        User u = userService.findByUsername(principal.getName());

        if(u == null){
            // TODO: not logged user trying to leave clan
        }
        if(u.getClanIdentifier() == null) {
            // TODO: user without clan trying to leave a clan
        }

        Clan clan = clanService.getClanByName(clanName);

        if (clan == null) {
            // TODO: return 'clan does not exist'
        }

        clanService.deleteFromClan(u.getId());
    }

    @RequestMapping(value = "/joinClan", method = { RequestMethod.POST })
    @ResponseBody
    public void joinClan(Principal principal, @RequestParam("clanName") final String clanName){
        ModelAndView mav = new ModelAndView("clan");

        User u = userService.findByUsername(principal.getName());

        if(u == null){
            // TODO: not logged user trying to leave clan
        }
        if(u.getClanIdentifier() != null) {
            // TODO: user with clan trying to join another clan
        }

        Clan clan = clanService.getClanByName(clanName);

        if (clan == null) {
            // TODO: return 'clan does not exist'
        }

        clanService.addUserToClan(clan.getId(), u.getId());
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
            mav.addObject("errorMsg", "404");
            LOGGER.warn("Tried to skip login in game: " + principal.getName());
            return mav;
        }

        Integer clanId = u.getClanIdentifier();
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

    @RequestMapping(value = "/buyFactory", method = { RequestMethod.POST })
    @ResponseBody
    public String purchaseFactory(Principal principal, @RequestParam("factoryId") final int factoryId){
        Boolean result = userService.purchaseFactory(userService.findByUsername(principal.getName()).getId(), FactoryType.fromId(factoryId));
        JSONObject j = new JSONObject();
        j.put("result",result);
        j.put("type", "purchaseFactory");
        j.put("factoryId",factoryId);
        return j.toJSONString();
    }

    @RequestMapping(value = "/upgradeFactory", method = { RequestMethod.POST })
    @ResponseBody
    public String upgradeFactory(Principal principal, @RequestParam("factoryId") final int factoryId){
        long userId = userService.findByUsername(principal.getName()).getId();
        Boolean result = userService.purchaseUpgrade(userId, FactoryType.fromId(factoryId));
        long factoryLevel = userService.getUserFactories(userId).stream().filter(f -> f.getType().getId() == factoryId).findAny().get().getLevel();
        JSONObject j = new JSONObject();
        j.put("result",result);
        j.put("type", "upgradeFactory");
        j.put("factoryId",factoryId);
        j.put("level",factoryLevel);
        return j.toJSONString();
    }

    @RequestMapping(value = "/buyFromMarket", method = { RequestMethod.POST })
    @ResponseBody
    public String buyFromMarket(Principal principal, @RequestParam("resourceId") final int resourceId,
                                      @RequestParam("quantity")final double quantity) {
        User u = userService.findByUsername(principal.getName());
        ResourceType resource =  ResourceType.fromId(resourceId);
        Boolean result = false;
        if (resource != null) {
            result = userService.purchaseResourceType(u.getId(), resource, quantity);
        }
        JSONObject j = new JSONObject();
        j.put("result", result);
        j.put("type", "buyFromMarket");
        j.put("resourceId",resourceId);
        j.put("quantity", quantity);
        return j.toJSONString();
    }

    @RequestMapping(value = "/sellToMarket", method = { RequestMethod.POST })
    @ResponseBody
    public String sellToMarket(Principal principal, @RequestParam("resourceId") final int resourceId,
                                      @RequestParam("quantity")final double quantity) {
        User u = userService.findByUsername(principal.getName());
        ResourceType resource =  ResourceType.fromId(resourceId);
        Boolean result = false;

        if (resource != null) {
            result = userService.sellResourceType(u.getId(), resource, quantity);
        }
        JSONObject j = new JSONObject();
        j.put("result", result);
        j.put("type", "sellToMarket");
        j.put("resourceId",resourceId);
        j.put("quantity", quantity);
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