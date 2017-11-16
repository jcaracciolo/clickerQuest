package ar.edu.itba.paw.webapp.DTO.clans;

import ar.edu.itba.paw.model.clan.ClanBattle;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

@XmlRootElement
@XmlType(name = "ClanBattle")
public class ClanBattleDTO {

    public static String url = "clans/%s/battle";


    @XmlElement(name = "clanId")
    private Integer clanId;

    @XmlElement(name = "oppponent_id")
    private Integer opponentId;

    @XmlElement(name = "clan_url")
    private URI clanURL;

    @XmlElement(name = "opponent_url")
    private URI opposingClanURL;

    @XmlElement(name = "initial_score")
    private Double initialScore;

    @XmlElement(name = "opponent_initial_score")
    private Double opponentInitialScore;

    @XmlElement(name = "opponent_clan_battle_url")
    private URI opponentClanBattleURL;

    public ClanBattleDTO(){}

    public ClanBattleDTO(ClanBattle battleOwner, @Nullable ClanBattle battleVersus, URI baseUri){
        clanURL = baseUri.resolve(String.format(ClanDTO.url, battleOwner.getClan().getId()));
        clanId = battleOwner.getClan().getId();
        initialScore = battleOwner.getInitialScore();
        if(battleVersus!=null) {
            opponentId = battleOwner.getVersus().getId();
            opponentClanBattleURL = baseUri.resolve(String.format(ClanDTO.url, battleVersus.getClan().getId()));
            opponentClanBattleURL = baseUri.resolve(String.format(ClanBattleDTO.url, battleVersus.getClan().getId()));
            opponentInitialScore = battleVersus.getInitialScore();
        }
    }

}
