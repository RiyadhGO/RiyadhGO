package sa.edu.yamama.riyadhgo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "routes")
public class Route implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="route_id")
    private Long id;

    @Column(name = "point_time")
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

     @Column(name="trip_id")
    private Long tripId;

    @ManyToOne
    @JoinColumn(name="trip_id", insertable =  false, updatable = false)
    private Trip trip;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    

    public Long getTripId() {
        return tripId;
    }
    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    
    
    
}
