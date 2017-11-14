package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.MarketService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.DTO.market.MarketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("market")
@Component
public class MarketController {
    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    private int userID = 1;

    @GET
    @Path("/")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response listPrices() {
        return Response.ok(new MarketDTO()).build();

    }

    //Money can't be purchased
    @POST
    @Path("/purchase")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response purchaseResource(
            @FormParam("resource_type") final Integer resourceType,
            @FormParam("amount") final Double amount
    ) {
        if(resourceType == null || amount == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(amount<0 || ResourceType.fromId(resourceType) == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (us.purchaseResourceType(userID,ResourceType.fromId(resourceType),amount)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    //Money can't be bought
    @POST
    @Path("/sell")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response sellResource(
            @FormParam("resource_type") final Integer resourceType,
            @FormParam("amount") final Double amount
    ) {
        if(resourceType == null || amount == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(amount<0 || ResourceType.fromId(resourceType) == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (us.sellResourceType(userID,ResourceType.fromId(resourceType),amount)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}