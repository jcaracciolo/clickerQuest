package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.clan.ClanBattle;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by juanfra on 18/06/17.
 */
@Controller
public class ClanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClanController.class);

    @Qualifier("clanServiceImpl")
    @Autowired
    private ClanService clanService;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

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
            mav.addObject("errorMsg", "401");
            LOGGER.warn("Tried to skip login in clan: " + principal.getName());
            return mav;
        }

        ClanBattle clanBattle1 = clanService.getClanBattle(clan.getId());

        if (clanBattle1 != null && clanBattle1.getVersus() != null) {
            ClanBattle clanBattle2 = clanService.getClanBattle(clanBattle1.getVersus().getId());
            mav.addObject("clanBattle2", clanBattle2);
        }

        mav.addObject("ranking", clanService.getGlobalRanking(clan.getId()));
        mav.addObject("clanBattle1", clanBattle1);
        mav.addObject("user", u);
        mav.addObject("userClan", clanService.getClanById(u.getClanId()));
        mav.addObject("clan", clan);
        return mav;
    }

    @RequestMapping(value = "/createClan", method = { RequestMethod.POST })
    @ResponseBody
    public String createClan(Principal principal, @RequestParam("clanName") final String clanName){
        ModelAndView mav = new ModelAndView("clan");
        if (clanService.getClanByName(clanName) != null) {
            JSONObject j = new JSONObject();
            j.put("result", "exists");
            return j.toJSONString();
        }

        Clan clan = clanService.createClan(clanName);
        User u = userService.findByUsername(principal.getName());

        if(u == null){
            JSONObject j = new JSONObject();
            j.put("result","noUser");
            return j.toJSONString();
        }
        clanService.addUserToClan(clan.getId(), u.getId());

        JSONObject j = new JSONObject();
        j.put("result","ok");
        return j.toJSONString();
    }

    @RequestMapping(value = "/leaveClan", method = { RequestMethod.POST })
    @ResponseBody
    public void leaveClan(Principal principal, @RequestParam("clanName") final String clanName){
        ModelAndView mav = new ModelAndView("clan");

        User u = userService.findByUsername(principal.getName());

        if(u == null){
            return;
        }
        if(u.getClanId() == null) {
            return;
        }

        Clan clan = clanService.getClanByName(clanName);

        if (clan == null) {
            return;
        }

        clanService.deleteFromClan(u.getId());
    }

    @RequestMapping(value = "/joinClan", method = { RequestMethod.POST })
    @ResponseBody
    public void joinClan(Principal principal, @RequestParam("clanName") final String clanName){
        ModelAndView mav = new ModelAndView("clan");

        User u = userService.findByUsername(principal.getName());

        if(u == null){
            return;
        }
        if(u.getClanId() != null) {
            return;
        }

        Clan clan = clanService.getClanByName(clanName);

        if (clan == null) {
            return;
        }

        clanService.addUserToClan(clan.getId(), u.getId());
    }

}
