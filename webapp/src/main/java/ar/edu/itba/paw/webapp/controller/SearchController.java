package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.packages.BuyLimits;
import ar.edu.itba.paw.model.packages.Paginating;
import ar.edu.itba.paw.webapp.DTO.PaginantingDTO;
import ar.edu.itba.paw.webapp.DTO.clans.ClanDTO;
import ar.edu.itba.paw.webapp.DTO.factories.BuyLimitsDTO;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/")
    public Response searchQuery(@QueryParam("query") final String query) {
        if (query == null || query.equals("")) return Response.status(Response.Status.BAD_REQUEST).build();

        Collection<User> users = us.findByKeyword(query);
        Collection<String> clans = cs.findByKeyword(query);

        Collection<SearchResultDTO> results = Stream.concat(
                users.stream().map((u) -> new SearchResultDTO(
                        USER,
                        uriInfo.getBaseUri().resolve(String.format(UserDTO.url, u.getId())),
                        u.getUsername())),
                clans.stream().map((c) -> new SearchResultDTO(
                        CLAN,
                        uriInfo.getBaseUri().resolve(String.format(ClanDTO.url, cs.getClanByName(c).getId())),
                        c))
        ).collect(Collectors.toList());

        return Response.ok(new SearchDTO(results)).build();
    }
}
