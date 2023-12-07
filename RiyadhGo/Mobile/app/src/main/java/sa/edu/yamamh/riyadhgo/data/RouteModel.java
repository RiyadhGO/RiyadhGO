package sa.edu.yamamh.riyadhgo.data;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class RouteModel { // this class represents a Route Model, likely used within a trip

    private Long id; //unique identifier for the route
    private LocalDateTime time = LocalDateTime.now(); //Timestamp indicating the time when the route point was recorded
    private double latitude;//Geographic latitude coordinate of the route point
    private double longitude;//Geographic longitude coordinate of the route point
    private Long tripId;// ID of the trip this route point belongs to
    private TripModel trip;

    //Getters and Setters
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public double getLongitude(){
        return  this.longitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public TripModel getTrip() {
        return trip;
    }

    public void setTrip(TripModel trip) {
        this.trip = trip;
    }

    //Converts the RouteModel object to a Map for storage
    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("id", this.getId());
        data.put("time", this.getTime());
        data.put("latitude", this.getLatitude());
        data.put("longitude", this.getLongitude());
        data.put("tripId", this.getTripId());
        data.put("trip", this.getTrip());
        return  data;
    }
    //Creates a new RouteModel object from a Map representation
    public static RouteModel fromMap(Map<String,Object> data){
        RouteModel route = new RouteModel();
        route.setId(MappingUtils.getLong("id", data));
        route.setTime(MappingUtils.getLocalDateTime("time", data));
        route.setLatitude(MappingUtils.getDouble("latitude", data));
        route.setLongitude(MappingUtils.getDouble("longitude", data));
        route.setTripId(MappingUtils.getLong("tripId", data));
        route.setTrip(TripModel.fromMap(MappingUtils.getMap("trip", data)));
        return route;
    }

    public static RouteModel fromJSONObject(JSONObject data){
        RouteModel route = new RouteModel();
        route.setId(MappingUtils.getLong("id", data));
        route.setTime(MappingUtils.getLocalDateTime("time", data));
        route.setLatitude(MappingUtils.getDouble("latitude", data));
        route.setLongitude(MappingUtils.getDouble("longitude", data));
        route.setTripId(MappingUtils.getLong("tripId", data));
        route.setTrip(TripModel.fromMap(MappingUtils.getMap("trip", data)));
        return route;
    }

}
