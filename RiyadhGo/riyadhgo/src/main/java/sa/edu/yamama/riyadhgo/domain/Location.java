package sa.edu.yamama.riyadhgo.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//The creation of table "locations" in the database
@Entity
@Table(name="locations")
public class Location implements Serializable{
 
//The creation of the variables Location ID, name, longitude, latitude and type in the "location" table  
    @Id
    @GeneratedValue
    @Column(name="loc_id")
    private Long id;

    @Column(name="name", length = 100)
    private String name;
    
    @Column(name="loc_lng")
    private float longitude;
    
    @Column(name="loc_lat")
    private float latitude;

    @Column(name = "loc_type")
    private TransportMethodTypes locationType;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public TransportMethodTypes getLocationType() {
        return locationType;
    }

    public void setLocationType(TransportMethodTypes locationType) {
        this.locationType = locationType;
    }

    
}
