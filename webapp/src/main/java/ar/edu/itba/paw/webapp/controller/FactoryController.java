package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Upgrade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.packages.BuyLimits;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.factories.BuyLimitsDTO;
import ar.edu.itba.paw.webapp.DTO.search.SearchDTO;
import ar.edu.itba.paw.webapp.DTO.search.SearchResultDTO;
import ar.edu.itba.paw.webapp.DTO.users.FactoriesDTO;
import ar.edu.itba.paw.webapp.DTO.users.UpgradeDTO;
import ar.edu.itba.paw.webapp.DTO.users.UserDTO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ar.edu.itba.paw.webapp.DTO.search.SearchResultType.CLAN;
import static ar.edu.itba.paw.webapp.DTO.search.SearchResultType.USER;

@Path("factories")
@Component
public class FactoryController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    static int userID = 1;

    @GET
    @Path("/{factoryId}/buyLimits")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getFactoriesById(@PathParam("factoryId") final int factoryId) {
        final User user = us.findById(userID);
        if (user != null) {
            final Factory factory = user.getFactories().stream().filter((f) -> f.getType().getId() == factoryId).findFirst().orElse(null);
            if (factory != null) {
                return Response.ok(new BuyLimitsDTO(new BuyLimits(user.getWealth(), factory),userID,factoryId)).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private static class PurchaseFactoryQuery {
        Integer factoryId;
        Integer amount;
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Path("/{factoryId}/purchase")
    public Response purchaseFactory(PurchaseFactoryQuery query) {

        if(query==null || query.amount<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        if (FactoryType.fromId(query.factoryId) != null) {
            if(us.purchaseFactory(userID,FactoryType.fromId(query.factoryId),query.amount)) {
                Response.ok();
            } else {
                Response.status(Response.Status.CONFLICT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private static class UpgradeFactoryQuery {
        Integer factoryId;
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Path("/{factoryId}/upgrade")
    public Response upgradeFactory(UpgradeFactoryQuery query) {
        if(query==null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (FactoryType.fromId(query.factoryId) != null) {
            if (us.purchaseUpgrade(userID, FactoryType.fromId(query.factoryId))) {
                Response.ok();
            } else {
                Response.status(Response.Status.CONFLICT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}