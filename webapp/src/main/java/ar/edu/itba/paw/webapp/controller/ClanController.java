package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.clan.ClanBattle;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.clans.ClanBattleDTO;
import ar.edu.itba.paw.webapp.DTO.clans.ClanDTO;
import ar.edu.itba.paw.webapp.DTO.clans.ClanUsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by juanfra on 18/06/17.
 */
@Path("clans")
@Component
public class ClanController {

    @Autowired
    private UserService us;
    @Autowired
    private ClanService cs;
    @Context
    private UriInfo uriInfo;

    static int userID = 41;

    @GET
    @Path("/all")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response listClans(
            @QueryParam("page") @DefaultValue("1") final Integer page,
            @QueryParam("pageSize") @DefaultValue("20") final Integer pageSize) {

        if(page<=0 || pageSize<=0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Paginating<Clan> allClans = cs.globalClans(page, pageSize);
        if(allClans!=null) {
            return Response.ok(
                    new PaginantingDTO<>(allClans, (c) -> new ClanDTO(c, cs.getClanBattle(c.getId()), uriInfo.getBaseUri()))
            ).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final int id) {
        final Clan clan = cs.getClanById(id);
        if (clan != null) {
            return Response.ok(new ClanDTO(clan, cs.getClanBattle(clan.getId()), uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/users")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getClanById(@PathParam("id") final int id) {
        final Clan clan = cs.getClanById(id);
        if (clan != null) {
            return Response.ok(new ClanUsersDTO(clan, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/battle")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getClanBattleById(@PathParam("id") final int id) {
        final Clan clan = cs.getClanById(id);
        if (clan != null) {
            ClanBattle battle = cs.getClanBattle(id);
            if(battle!=null) {
                ClanBattle opponent = null;
                if(battle.getVersus() != null) {
                    opponent = cs.getClanBattle(battle.getVersus().getId());
                }
                return Response.ok(new ClanBattleDTO(battle,opponent,uriInfo.getBaseUri())).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private static class JoinClanQuery {
        Integer id;
    }

    @POST
    @Path("/{id}/join")
    @Consumes(value = { MediaType.APPLICATION_JSON, })
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response joinClan(JoinClanQuery query) {
        User user = us.findById(userID);
        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.getClanId()!=null) return Response.status(Response.Status.CONFLICT).entity("User is already part of a clan.").build();

        final Clan clan = cs.addUserToClan(query.id,userID);
        if (clan != null) {
            return Response.ok(new ClanUsersDTO(clan, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    static private class CreateClanQuery {
        private String name;
    }

    @POST
    @Path("/create")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response createClan(CreateClanQuery query) {
        if(query ==null || query.name==null) return Response.status(Response.Status.BAD_REQUEST).build();
        String name = query.name;

        User user = us.findById(userID);
        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.getClanId()!=null) return Response.status(Response.Status.CONFLICT).entity("User is already part of a clan.").build();
        if(cs.getClanByName(name)!=null) return Response.status(Response.Status.CONFLICT).entity("Clan with name " + name + " already exists").build();

        final Clan clan = cs.createClan(name);
        if (clan != null) {
            return Response.ok(new ClanUsersDTO(clan, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/leave")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response leaveClan() {
        User user = us.findById(userID);
        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.getClanId() == null) return Response.status(Response.Status.CONFLICT).entity("User is no part of a clan.").build();

        if (cs.deleteFromClan(userID)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
