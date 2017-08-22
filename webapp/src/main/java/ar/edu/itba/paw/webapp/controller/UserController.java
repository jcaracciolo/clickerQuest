package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.ClanDTO;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

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
    @Path("/")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listUsers() {
//        final Paginating<User> allUsers = us.globalUsers(1,20);
//        return Response.ok(
//                new PaginantingDTO<>(allUsers, (u) -> new UserDTO(u,uriInfo.getBaseUri()))
//
//        ).build();

        final Paginating<Clan> allUsers = cs.globalClans(1,20);
        return Response.ok(
                new PaginantingDTO<>(allUsers, (u) -> new ClanDTO(u,uriInfo.getBaseUri()))

        ).build();

//        final Clan clan = cs.globalClans(1,20).getItems().get(0);
//        return Response.ok(
//new ClanDTO(clan, uriInfo.getBaseUri())
//        ).build();
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
//    @GET
//    @Path("/{id}")
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response getById(@PathParam("id") final long id) {
//        final User user = us.findById(id);
//        if (user != null) {
//            return Response.ok(new UserDTO(user)).build()
//                    ;
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
}