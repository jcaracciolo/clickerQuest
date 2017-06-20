package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by juanfra on 18/06/17.
 */
@Controller
public class RankController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RankController.class);

    @Qualifier("clanServiceImpl")
    @Autowired
    private ClanService clanService;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    // GLOBAL RANKING
    @RequestMapping(value = "/worldRanking/{page}", method = { RequestMethod.GET })
    public ModelAndView globalRanking(Principal principal, @PathVariable("page") Integer page){
        ModelAndView mav = new ModelAndView("globalRanking");

        if (page <= 0) {
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            LOGGER.warn("Invalid page index");
            return mav;
        }

        User u = userService.findByUsername(principal.getName());
        if(u == null) {
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "401");
            LOGGER.warn("Not logged in watching global ranking");
            return mav;
        }


        Integer clanId = u.getClanId();
        if (clanId != null) {
            mav.addObject("clan", clanService.getClanById(clanId));
        }

        mav.addObject("pageNumber", page);
        mav.addObject("user", u);
        mav.addObject("globalRanking", userService.globalUsers(page,10));
        return mav;
    }

    // GLOBAL RANKING
    @RequestMapping(value = "/clanRanking/{page}", method = { RequestMethod.GET })
    public ModelAndView globalClanRanking(Principal principal, @PathVariable("page") Integer page){
        ModelAndView mav = new ModelAndView("clanRanking");
        if (page <= 0) {
            LOGGER.warn("Invalid page index");
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "404");
            return mav;
        }
        User u = userService.findByUsername(principal.getName());
        if(u == null) {
            LOGGER.warn("Not logged in watching global clan ranking");
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "401");
            return mav;
        }

        Integer clanId = u.getClanId();
        if (clanId != null) {
            mav.addObject("clan", clanService.getClanById(clanId));
        }

        mav.addObject("pageNumber", page);
        mav.addObject("user", u);
        mav.addObject("globalRanking", clanService.globalClans(page,10));
        return mav;
    }
}
