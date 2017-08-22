package ar.edu.itba.paw.webapp.DTO;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.model.clan.Clan;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by juanfra on 22/08/17.
 */
@XmlRootElement
@XmlType(name = "Clan")
public class ClanDTO {

    @XmlElement(name = "users")
    private List<UserDTO> users = new ArrayList<>();

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "score")
    private double score;

    @XmlElement(name = "wins")
    private int wins = 0;

    @XmlElement(name = "battles")
    private int battles = 0;

    @XmlElement(name = "image")
    private URI image;

    public ClanDTO(){}

    public ClanDTO(Clan clan, URI baseUri){
        this.id = clan.getId();
        this.name = clan.getName();
        this.score = clan.getScore();
        this.wins = clan.getWins();
        this.battles = clan.getBattles();
        //TODO change this grou_icons
        this.image = baseUri.resolve("resources/" + "group_icons/" + clan.getImage());
        clan.getUsers().forEach((u) -> users.add(new UserDTO(u,baseUri)));
    }


}
