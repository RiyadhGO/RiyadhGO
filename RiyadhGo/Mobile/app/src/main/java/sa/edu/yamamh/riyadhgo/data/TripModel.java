package sa.edu.yamamh.riyadhgo.data;

import android.util.Log;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.DistanceUtils;
import sa.edu.yamamh.riyadhgo.MappingUtils;

public class TripModel {
    private Long id;//Unique identifier for the trip
    private Long userId;// ID of the user who took the trip
    private UserModel user;//Reference to the UserModel object associated with the user
    private double startLat;//Latitude coordinate of the trip's starting point
    private double startLng;//Longitude coordinate of the trip's starting point
    private double destLat;//Latitude coordinate of the trip's destination
    private double destLng;//Longitude coordinate of the trip's destination
    private double duration;//Total duration of the trip in minutes
    private LocalDateTime startTime;//Timestamp indicating the start time of the trip
    private LocalDateTime endTime;//Timestamp indicating the end time of the trip
    private Long transportMethodId;//ID of the transportation method used for the trip
    private TransportMethodModel transportMethod;//Reference to the TransportMethodModel object representing the transportation method

    private List<RouteModel> route;// List of RouteModel objects representing the route taken during the trip

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

    //sets the user associated with the trip
    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public double getDestLat() {
        return destLat;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public double getDestLng() {
        return destLng;
    }

    public void setDestLng(double destLng) {
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
        trip.setStartLat(MappingUtils.getDouble("startLat", data));
        trip.setStartLng(MappingUtils.getDouble("startLng", data));
        trip.setDuration(MappingUtils.getDouble("duration", data));
        trip.setStartTime(MappingUtils.getLocalDateTime("startTime",data));
        trip.setEndTime(MappingUtils.getLocalDateTime("endTime",data));
        trip.setTransportMethodId(MappingUtils.getLong("transportMethodId", data));
        trip.setDestLat(MappingUtils.getDouble("destLat",data));
        trip.setDestLng(MappingUtils.getDouble("destLng",data));
        return trip;
    }

    public static  TripModel fromJSONObject(JSONObject data){
        TripModel trip = new TripModel();
        if(data == null)
            return trip;
        trip.setId(MappingUtils.getLong("id", data));
        trip.setUserId(MappingUtils.getLong("userId", data));
        trip.setStartLat(MappingUtils.getDouble("startLat", data));
        trip.setStartLng(MappingUtils.getDouble("startLng", data));
        trip.setDuration(MappingUtils.getLong("duration", data));
        trip.setStartTime(MappingUtils.getLocalDateTime("startTime",data));
        trip.setEndTime(MappingUtils.getLocalDateTime("endTime",data));
        trip.setTransportMethodId(MappingUtils.getLong("transportMethodId", data));
        trip.setDestLat(MappingUtils.getDouble("destLat",data));
        trip.setDestLng(MappingUtils.getDouble("destLng",data));
        return trip;
    }


    public void addRoute(LatLng location){
        this.addRoute(location.latitude,location.longitude);
    }
    //Adds a route point to the trip based on a LatLng object
    public void addRoute(double lat, double lng){//Adds a route point to the trip based on latitude and longitude values
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

    public double getDistanceInKM()//Calculates the total distance of the trip in kilometers
    {
        LatLng start = new LatLng(this.getStartLat(), this.getStartLng());
        LatLng end = new LatLng(this.getDestLat(), this.getDestLng());
        double distanceKM = DistanceUtils.getDistanceInKM(start,end);
        return distanceKM;
    }

    public long getDurationInMinutes()//Calculates the total duration of the trip in minutes
    {

        return ChronoUnit.MINUTES.between(this.startTime, this.endTime);
    }

}
