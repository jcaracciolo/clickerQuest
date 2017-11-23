package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.webapp.DTO.factories.AllBaseFactories;
import ar.edu.itba.paw.webapp.DTO.factories.BaseFactoryDTO;
import ar.edu.itba.paw.webapp.DTO.factories.BaseFactoryRecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    @Path("/all")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getAllBaseFactories() {
        return Response.ok(new AllBaseFactories(uriInfo.getBaseUri())).build();
    }

    @GET
    @Path("/{factoryId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getBaseFactoryById(@PathParam("factoryId") final int factoryId) {
        final FactoryType factory= FactoryType.fromId(factoryId);
        if (factory != null) {
            return Response.ok(new BaseFactoryDTO(factory, uriInfo.getBaseUri())).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{factoryId}/recipe")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getBaseFactoryRecipeById(@PathParam("factoryId") final int factoryId) {
        final FactoryType factory= FactoryType.fromId(factoryId);
        if (factory != null) {
            return Response.ok(new BaseFactoryRecipeDTO(factory)).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private static class PurchaseFactoryQuery {
        Integer factoryID;
        Integer amount;
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Path("/purchase")
    public Response purchaseFactory(PurchaseFactoryQuery query) {

        if(query==null || query.amount<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        if (FactoryType.fromId(query.factoryID) != null) {
            if(us.purchaseFactory(userID,FactoryType.fromId(query.factoryID),query.amount)) {
                Response.ok();
            } else {
                Response.status(Response.Status.CONFLICT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    static class UpgradeFactoryQuery {
        Integer factoryId;
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Path("/upgrade")
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