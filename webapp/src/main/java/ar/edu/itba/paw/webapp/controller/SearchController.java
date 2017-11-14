package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.packages.BuyLimits;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.factories.BuyLimitsDTO;
import ar.edu.itba.paw.webapp.DTO.post.SearchQuery;
import ar.edu.itba.paw.webapp.DTO.search.SearchDTO;
import ar.edu.itba.paw.webapp.DTO.search.SearchResultDTO;
import ar.edu.itba.paw.webapp.DTO.search.SearchResultType;
import ar.edu.itba.paw.webapp.DTO.users.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ar.edu.itba.paw.webapp.DTO.search.SearchResultType.CLAN;
import static ar.edu.itba.paw.webapp.DTO.search.SearchResultType.USER;

@Path("search")
@Component
public class SearchController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService us;
    @Qualifier("clanServiceImpl")
    @Autowired
    private ClanService cs;
    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/")
    public Response searchQuery(final SearchQuery query) {
        if (query.query != null) {
            Collection<User> users = us.findByKeyword(query.query);
            Collection<String> clans = cs.findByKeyword(query.query);

            Collection<SearchResultDTO> results = Stream.concat(
                    users.stream().map((u) -> new SearchResultDTO(USER, u.getUsername())),
                    clans.stream().map((c) -> new SearchResultDTO(CLAN, c))
            ).collect(Collectors.toList());

            return Response.ok(new SearchDTO(results)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
