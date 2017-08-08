package ar.edu.itba.paw.webapp.DTO;

import ar.edu.itba.paw.model.User;
import org.glassfish.jersey.server.Uri;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class UserDTO {

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

    @XmlElement(name = "factories")
    private List<FactoryDTO> factories;

    @XmlElement(name = "wealth")
    private WealthDTO wealth;

    public UserDTO(){}

    public UserDTO(User user, URI baseUri){
        id = user.getId();
        username = user.getUsername();
        profileImageUrl = baseUri.resolve("resources/" + "profile_images/" + user.getProfileImage());
        score = user.getScore();
        clanId = user.getClanId();
        factories = user.getFactories().stream()
                    .map(FactoryDTO::new).collect(Collectors.toList());
        wealth = new WealthDTO(user.getWealth());
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

    public List<FactoryDTO> getFactories() {
        return factories;
    }

    public WealthDTO getWealth() {
        return wealth;
    }
}
