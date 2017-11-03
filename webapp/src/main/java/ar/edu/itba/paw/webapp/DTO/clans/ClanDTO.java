package ar.edu.itba.paw.webapp.DTO.clans;

import ar.edu.itba.paw.model.clan.Clan;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

/**
 * Created by juanfra on 22/08/17.
 */
@XmlRootElement
@XmlType(name = "Clan")
public class ClanDTO {

    public static String url = "clans/%s";

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "score")
    private double score;

    @XmlElement(name = "users_url")
    private URI usersURL;

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
        this.usersURL = baseUri.resolve(String.format(ClanUsersDTO.url, clan.getId()));
    }


}
