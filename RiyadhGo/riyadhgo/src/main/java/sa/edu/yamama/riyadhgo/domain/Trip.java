package sa.edu.yamama.riyadhgo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trips")
public class Trip implements Serializable {

    @Id
    @Column(name = "trip_id")
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "start_lat")
    private float startLat;

    @Column(name = "start_lng")
    private float startLng;

    @Column(name = "dest_lat")
    private float destLat;

    @Column(name = "dest_lng")
    private float destLng;

    @Column(name = "trip_duration")
    private double duration;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endTime;

    @Column(name = "transport_method_id")
    private Long transportMethodId;

    @ManyToOne
    @JoinColumn(name = "transport_method_id", insertable = false, updatable = false)
    private TransportMethod transportMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getStartLat() {
        return startLat;
    }

    public void setStartLat(float startLat) {
        this.startLat = startLat;
    }

    public float getStartLng() {
        return startLng;
    }

    public void setStartLng(float startLng) {
        this.startLng = startLng;
    }

    public float getDestLat() {
        return destLat;
    }

    public void setDestLat(float destLat) {
        this.destLat = destLat;
    }

    public float getDestLng() {
        return destLng;
    }

    public void setDestLng(float destLng) {
        this.destLng = destLng;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getTransportMethodId() {
        return transportMethodId;
    }

    public void setTransportMethodId(Long transportMethodId) {
        this.transportMethodId = transportMethodId;
    }

    public TransportMethod getTransportMethod() {
        return transportMethod;
    }

    public void setTransportMethod(TransportMethod transportMethod) {
        this.transportMethod = transportMethod;
    }

}
