package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.Upgrade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.packages.BuyLimits;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.factories.BuyLimitsDTO;
import ar.edu.itba.paw.webapp.DTO.users.FactoriesDTO;
import ar.edu.itba.paw.webapp.DTO.users.UpgradeDTO;
import ar.edu.itba.paw.webapp.DTO.users.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

@Path("factories")
@Component
public class FactoryController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{factoryId}/buyLimits")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getFactoriesById(@PathParam("factoryId") final long factoryId) {
        final User user = us.globalUsers(0,1).getItems().get(0);
        if (user != null) {
            final Factory factory = user.getFactories().stream().filter((f) -> f.getType().getId() == factoryId).findFirst().orElse(null);
            if (factory != null) {
                return Response.ok(new BuyLimitsDTO(new BuyLimits(user.getWealth(), factory))).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Factory getSingleFactory(long userID, long factoryId) {
        return us.getUserFactories(userID).stream().filter(f -> f.getType().getId() == factoryId).findAny().orElse(null);
    }

}