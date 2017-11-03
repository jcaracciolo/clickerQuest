package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.Upgrade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.users.*;
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

    @GET
    @Path("/all")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listUsers() {
        final Paginating<User> allUsers = us.globalUsers(1,20);
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
            return Response.ok(new WealthDTO(wealth)).build();
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
            return Response.ok(new FactoriesDTO(factories, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{userId}/factories/{factoryId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getFactoryById(@PathParam("userId") final long id, @PathParam("factoryId") final long factoryId) {
        final Factory factory = us.getUserFactories(id).stream().filter(f -> f.getType().getId() == factoryId).findAny().orElse(null);
        if (factory != null) {
            return Response.ok(new FactoryDTO(factory, uriInfo.getBaseUri())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{userId}/factories/{factoryId}/upgrade")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUpgradeById(@PathParam("userId") final long id, @PathParam("factoryId") final long factoryId) {
        final Upgrade upgrade = us.getUserFactories(id).stream().filter(f -> f.getType().getId() == factoryId).findAny().map(Factory::getNextUpgrade).orElse(null);
        if (upgrade != null) {
            return Response.ok(new UpgradeDTO(upgrade)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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