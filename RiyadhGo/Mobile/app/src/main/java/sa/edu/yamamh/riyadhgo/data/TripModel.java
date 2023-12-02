package sa.edu.yamamh.riyadhgo.data;

import android.util.Log;

import com.google.maps.model.LatLng;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class TripModel {
    private Long id;
    private Long userId;
    private UserModel user;
    private float startLat;
    private float startLng;
    private float destLat;
    private float destLng;
    private double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long transportMethodId;
    private TransportMethodModel transportMethod;

    private List<RouteModel> route;

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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
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

    public TransportMethodModel getTransportMethod() {
        return transportMethod;
    }

    public void setTransportMethod(TransportMethodModel transportMethod) {
        this.transportMethod = transportMethod;
    }

    public void setRoute(List<RouteModel> route){
        this.route = route;
    }
    public List<RouteModel> getRoute(){
        return this.route;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("id", this.getId());
        data.put("userId", this.getUserId());
        data.put("startLat", this.getStartLat());
        data.put("startLng", this.getStartLng());
        data.put("destLat", this.getDestLat());
        data.put("destLng", this.getDestLng());
        data.put("duration", this.getDuration());
        data.put("startTime", this.getStartTime().format(MappingUtils.DATE_TIME_FORMATTER));
        data.put("endTime", this.getEndTime().format(MappingUtils.DATE_TIME_FORMATTER));
        data.put("transportMethodId", this.getTransportMethodId());
        return  data;
    }

    public static  TripModel fromMap(Map<String,Object> data){
        TripModel trip = new TripModel();
        if(data == null)
            return trip;
        trip.setId(MappingUtils.getLong("id", data));
        trip.setUserId(MappingUtils.getLong("userId", data));
        trip.setStartLat(MappingUtils.getFloat("startLat", data));
        trip.setStartLng(MappingUtils.getFloat("startLng", data));
        trip.setDuration(MappingUtils.getLong("duration", data));
        trip.setStartTime(MappingUtils.getLocalDateTime("startTime",data));
        trip.setEndTime(MappingUtils.getLocalDateTime("endTime",data));
        trip.setTransportMethodId(MappingUtils.getLong("transportMethodId", data));
        trip.setDestLat(MappingUtils.getFloat("destLat",data));
        trip.setDestLng(MappingUtils.getFloat("destLng",data));
        return trip;
    }

    public static  TripModel fromJSONObject(JSONObject data){
        TripModel trip = new TripModel();
        if(data == null)
            return trip;
        trip.setId(MappingUtils.getLong("id", data));
        trip.setUserId(MappingUtils.getLong("userId", data));
        trip.setStartLat(MappingUtils.getFloat("startLat", data));
        trip.setStartLng(MappingUtils.getFloat("startLng", data));
        trip.setDuration(MappingUtils.getLong("duration", data));
        trip.setStartTime(MappingUtils.getLocalDateTime("startTime",data));
        trip.setEndTime(MappingUtils.getLocalDateTime("endTime",data));
        trip.setTransportMethodId(MappingUtils.getLong("transportMethodId", data));
        trip.setDestLat(MappingUtils.getFloat("destLat",data));
        trip.setDestLng(MappingUtils.getFloat("destLng",data));
        return trip;
    }


    public void addRoute(LatLng location){
        this.addRoute((float)location.lat,(float)location.lng);
    }

    public void addRoute(float lat, float lng){
        if(this.route == null){
            this.route = new ArrayList<>();
        }
        RouteModel model = new RouteModel();
        model.setLongitude(lng);
        model.setLatitude(lat);
        model.setTime(LocalDateTime.now());
        model.setTrip(this);
        model.setTripId(this.id);
        this.route.add(model);
    }

}
