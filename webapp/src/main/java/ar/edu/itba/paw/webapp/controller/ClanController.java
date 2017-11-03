package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.clans.ClanDTO;
import ar.edu.itba.paw.webapp.DTO.clans.ClanUsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by juanfra on 18/06/17.
 */
@Path("clans")
@Component
public class ClanController {

    @Autowired
    private ClanService cs;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/all")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response listClans() {
        final Paginating<Clan> allUsers = cs.globalClans(1, 20);
        return Response.ok(
                new PaginantingDTO<>(allUsers, (c) -> new ClanDTO(c, uriInfo.getBaseUri()))
        ).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final int id) {
        final Clan clan = cs.getClanById(id);
        if (clan != null) {
            return Response.ok(new ClanDTO(clan, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/users")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUsersById(@PathParam("id") final int id) {
        final Clan clan = cs.getClanById(id);
        if (clan != null) {
            return Response.ok(new ClanUsersDTO(clan, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



}


//public class ClanController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ClanController.class);
//
//    @Qualifier("clanServiceImpl")
//    @Autowired
//    private ClanService clanService;
//    @Qualifier("userServiceImpl")
//    @Autowired
//    private UserService userService;
//
//    // CLAN
//    @RequestMapping(value = "/clan/{clanName}", method = { RequestMethod.GET })
//    public ModelAndView clan(Principal principal, @PathVariable(value="clanName") String clanName){
//        ModelAndView mav = new ModelAndView("clan");
//
//        Clan clan = clanService.getClanByName(clanName);
//        if (clan == null) {
//            mav = new ModelAndView("errorPage");
//            mav.addObject("errorMsg", "404");
//            LOGGER.info("Clan does not exist " + clanName);
//            return mav;
//        }
//
//        User u = userService.findByUsername(principal.getName());
//        if(u == null) {
//            mav = new ModelAndView("errorPage");
//            mav.addObject("errorMsg", "401");
//            LOGGER.warn("Tried to skip login in clan: " + principal.getName());
//            return mav;
//        }
//
//        ClanBattle clanBattle1 = clanService.getClanBattle(clan.getId());
//
//        if (clanBattle1 != null && clanBattle1.getVersus() != null) {
//            ClanBattle clanBattle2 = clanService.getClanBattle(clanBattle1.getVersus().getId());
//            mav.addObject("clanBattle2", clanBattle2);
//        }
//
//        mav.addObject("ranking", clanService.getGlobalRanking(clan.getId()));
//        mav.addObject("clanBattle1", clanBattle1);
//        mav.addObject("user", u);
//        if (u.getClanId() != null) {
//            mav.addObject("userClan", clanService.getClanById(u.getClanId()));
//        }
//        mav.addObject("clan", clan);
//        return mav;
//    }
//
//    @RequestMapping(value = "/createClan", method = { RequestMethod.POST })
//    @ResponseBody
//    public String createClan(Principal principal, @RequestParam("clanName") final String clanName){
//        ModelAndView mav = new ModelAndView("clan");
//        if (clanService.getClanByName(clanName) != null) {
//            JSONObject j = new JSONObject();
//            j.put("result", "exists");
//            return j.toJSONString();
//        }
//
//        Clan clan = clanService.createClan(clanName);
//        User u = userService.findByUsername(principal.getName());
//
//        if(u == null){
//            JSONObject j = new JSONObject();
//            j.put("result","noUser");
//            return j.toJSONString();
//        }
//        clanService.addUserToClan(clan.getId(), u.getId());
//
//        JSONObject j = new JSONObject();
//        j.put("result","ok");
//        return j.toJSONString();
//    }
//
//    @RequestMapping(value = "/leaveClan", method = { RequestMethod.POST })
//    @ResponseBody
//    public void leaveClan(Principal principal, @RequestParam("clanName") final String clanName){
//        ModelAndView mav = new ModelAndView("clan");
//
//        User u = userService.findByUsername(principal.getName());
//
//        if(u == null){
//            return;
//        }
//        if(u.getClanId() == null) {
//            return;
//        }
//
//        Clan clan = clanService.getClanByName(clanName);
//
//        if (clan == null) {
//            return;
//        }
//
//        clanService.deleteFromClan(u.getId());
//    }
//
//    @RequestMapping(value = "/joinClan", method = { RequestMethod.POST })
//    @ResponseBody
//    public void joinClan(Principal principal, @RequestParam("clanName") final String clanName){
//        ModelAndView mav = new ModelAndView("clan");
//
//        User u = userService.findByUsername(principal.getName());
//
//        if(u == null){
//            return;
//        }
//        if(u.getClanId() != null) {
//            return;
//        }
//
//        Clan clan = clanService.getClanByName(clanName);
//
//        if (clan == null) {
//            return;
//        }
//
//        clanService.addUserToClan(clan.getId(), u.getId());
//    }
//
//}
