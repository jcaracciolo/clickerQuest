package ar.edu.itba.paw.model.clan;

import ar.edu.itba.paw.model.clan.Clan;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by juanfra on 17/06/17.
 */
@Entity
@Table(name = "clansbattle")
public class ClanBattle implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clanid")
    private Clan clan;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "versusid")
    private Clan versus;

    @Column(name = "initialscore", nullable = false)
    private double initialScore;

    ClanBattle(){}

    public ClanBattle(Clan clan, Clan versus, double initialScore) {
        this.clan = clan;
        this.versus = versus;
        this.initialScore = initialScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClanBattle that = (ClanBattle) o;

        if (Double.compare(that.initialScore, initialScore) != 0) return false;
        if (clan != null ? !clan.equals(that.clan) : that.clan != null) return false;
        return versus != null ? versus.equals(that.versus) : that.versus == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = clan != null ? clan.hashCode() : 0;
        result = 31 * result + (versus != null ? versus.hashCode() : 0);
        temp = Double.doubleToLongBits(initialScore);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
