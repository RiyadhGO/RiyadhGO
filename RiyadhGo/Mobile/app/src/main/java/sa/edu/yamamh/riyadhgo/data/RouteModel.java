package sa.edu.yamamh.riyadhgo.data;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class RouteModel {

    private Long id;
    private LocalDateTime time = LocalDateTime.now();
    private float latitude;
    private float longitude;
    private Long tripId;
    private TripModel trip;


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

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }
    public float getLongitude(){
        return  this.longitude;
    }
    public void setLongitude(float longitude){
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

    public static RouteModel fromMap(Map<String,Object> data){
        RouteModel route = new RouteModel();
        route.setId(MappingUtils.getLong("id", data));
        route.setTime(MappingUtils.getLocalDateTime("time", data));
        route.setLatitude(MappingUtils.getFloat("latitude", data));
        route.setLongitude(MappingUtils.getFloat("longitude", data));
        route.setTripId(MappingUtils.getLong("tripId", data));
        route.setTrip(TripModel.fromMap(MappingUtils.getMap("trip", data)));
        return route;
    }

    public static RouteModel fromJSONObject(JSONObject data){
        RouteModel route = new RouteModel();
        route.setId(MappingUtils.getLong("id", data));
        route.setTime(MappingUtils.getLocalDateTime("time", data));
        route.setLatitude(MappingUtils.getFloat("latitude", data));
        route.setLongitude(MappingUtils.getFloat("longitude", data));
        route.setTripId(MappingUtils.getLong("tripId", data));
        route.setTrip(TripModel.fromMap(MappingUtils.getMap("trip", data)));
        return route;
    }

}
