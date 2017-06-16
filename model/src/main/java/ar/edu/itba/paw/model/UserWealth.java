package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class needed for being able to map our database to the object paradigm, since Wealth did not
 * have any Id in the dabatase but was rather a collection of these "UserWealths" whose id was userid
 * and resourceType
 * Created by juanfra on 15/06/17.
 */
@Table(name = "wealths")
@Entity
class UserWealth {
    private double production;
    private double storage;
    private long lastupdated;

    @EmbeddedId
    UserAndResource _id;

    @Embeddable
    private static class UserAndResource implements Serializable {
        @Column(name = "userid", nullable = false)
        long userid;
        @Column(name = "resourcetype", nullable = false)
        int resourcetype;
        UserAndResource(){};
        UserAndResource(long userid, int _type){
            this.userid = userid;
            this.resourcetype = _type;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserAndResource that = (UserAndResource) o;

            if (userid != that.userid) return false;
            return resourcetype == that.resourcetype;

        }

        @Override
        public int hashCode() {
            int result = (int) (userid ^ (userid >>> 32));
            result = 31 * result + resourcetype;
            return result;
        }
    }

    UserWealth(){}

    public void setId(long userid,int resourceType){
        _id = new UserAndResource(userid,resourceType);
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage = storage;
    }

    public long getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(long lastupdated) {
        this.lastupdated = lastupdated;
    }

    public long getUserId(){
        return _id.userid;
    }

    public ResourceType getResourceType(){
        return ResourceType.fromId(_id.resourcetype);
    }
}
