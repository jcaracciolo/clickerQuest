package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

@Path("users")
@Component
public class UserController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService us;
    @Autowired
    private ClanService cs;
    @Context
    private UriInfo uriInfo;

    static int userID = 1;

    @GET
    @Path("/all")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listUsers(
            @QueryParam("page") @DefaultValue("1") final int page,
            @QueryParam("pageSize") @DefaultValue("20") final int pageSize) {

        if(page<=0 || pageSize<=0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Paginating<User> allUsers = us.globalUsers(page,pageSize);
        if(allUsers==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(
                new PaginantingDTO<>(allUsers, (u) -> new UserDTO(u,uriInfo.getBaseUri()))
        ).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final User user = us.findById(id);
        if (user != null) {
            return Response.ok(new UserDTO(user, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/wealth")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getWealthById(@PathParam("id") final long id) {
        final Wealth wealth = us.getUserWealth(id);
        if (wealth != null) {
            return Response.ok(new WealthDTO(wealth, userID)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/factories")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getFactoriesById(@PathParam("id") final long id) {
        final Collection<Factory> factories = us.getUserFactories(id);
        if (factories != null) {
            return Response.ok(new FactoriesDTO(factories, userID, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{userId}/factories/{factoryId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getFactoryById(
            @PathParam("userId") final long id,
            @PathParam("factoryId") final int factoryId
    ) {
        final Factory factory = getSingleFactory(id,factoryId);
        if (factory != null) {
            return Response.ok(new FactoryDTO(factory, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{userId}/factories/{factoryId}/upgrade")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUpgradeOfFactory(
            @PathParam("userId") final long id,
            @PathParam("factoryId") final int factoryId
    ) {
        final Factory factory = getSingleFactory(id,factoryId);
        if (factory != null) {
            return Response.ok(new UpgradeDTO(factory.getNextUpgrade())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private Factory getSingleFactory(final long userID, final int factoryId) {
        Collection<Factory> factories = us.getUserFactories(userID);
        if (factories==null) return null;
        return factories.stream().filter(f -> f.getType().getId() == factoryId).findAny().orElse(null);
    }


//    @POST
//    @Path("/")
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response createUser(final UserDTO userDto) {
//        final User user = us.register(userDto.getUsername(), userDto.getPassword());
//        final URI uri = uriInfo.getAbsolutePathBuilder()
//                .path(String.valueOf(user.getId())).build
//                        ();
//        return Response.created(uri).build();
//    }

}