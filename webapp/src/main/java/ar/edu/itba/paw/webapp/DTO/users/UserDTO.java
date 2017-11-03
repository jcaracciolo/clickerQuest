package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.DTO.clans.ClanDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
@XmlType(name = "User")
public class UserDTO {

    public static String url = "users/%s";

    @XmlElement(name = "id")
    private  long id;

    @XmlElement(name = "username")
    private  String username;

    @XmlElement(name = "profile_image_url")
    private URI profileImageUrl;

    @XmlElement(name = "score")
    private  double score;

    @XmlElement(name = "clan_id")
    private Integer clanId;

    @XmlElement(name = "clan_url")
    private URI clanUrl;

    @XmlElement(name = "factories_url")
    private URI factoriesUrl;

    @XmlElement(name = "wealth_url")
    private URI wealthUrl;

    public UserDTO(){}

    public UserDTO(User user, URI baseUri){
        id = user.getId();
        username = user.getUsername();
        profileImageUrl = baseUri.resolve("resources/" + "profile_images/" + user.getProfileImage());
        score = user.getScore();
        clanId = user.getClanId();
        clanUrl = baseUri.resolve(String.format(ClanDTO.url, clanId));
        factoriesUrl = baseUri.resolve(String.format(FactoriesDTO.url, user.getId()));
        wealthUrl = baseUri.resolve(String.format(WealthDTO.url, user.getId()));

    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public URI getProfileImageUrl() {
        return profileImageUrl;
    }

    public double getScore() {
        return score;
    }

    public Integer getClanId() {
        return clanId;
    }

    public URI getClanUrl() {
        return clanUrl;
    }

    public URI getFactoriesUrl() {
        return factoriesUrl;
    }

    public URI getWealthUrl() {
        return wealthUrl;
    }
}
