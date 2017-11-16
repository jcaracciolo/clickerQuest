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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Path("market")
@Component
public class MarketController {
    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    private int userID = 1;

    @GET
    @Path("/prices")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response listPrices() {
        return Response.ok(new MarketDTO()).build();

    }

    private static class MarketQuery {
        @XmlElement(name = "resource_type")
        public String resourceType;
        public Double amount;
    }

    //Money can't be purchased
    @POST
    @Path("/purchase")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response purchaseResource(MarketQuery query) {
        if(query == null || query.resourceType == null || query.amount == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(query.amount<0 || !ResourceType.fromName(query.resourceType).isPresent()) return Response.status(Response.Status.BAD_REQUEST).build();

        if (us.purchaseResourceType(userID,ResourceType.fromName(query.resourceType).get(),query.amount)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    //Money can't be bought
    @POST
    @Path("/sell")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response sellResource(MarketQuery query) {
        if(query == null || query.resourceType == null || query.amount == null) return Response.status(Response.Status.BAD_REQUEST).build();
        Double amount = query.amount;
        String resourceType = query.resourceType;

        if(us.findById(userID) == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        if(amount<0 ||  !ResourceType.fromName(resourceType).isPresent()) return Response.status(Response.Status.BAD_REQUEST).build();

        if (us.sellResourceType(userID,ResourceType.fromName(resourceType).get(),amount)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}