package sa.edu.yamamh.riyadhgo.network;

import android.util.Log;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.data.FavoriteLocationModel;
import sa.edu.yamamh.riyadhgo.data.LocationModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodModel;

public class LocationClient  extends BaseApiClient {

    public static void findLocation(String methodType, DataArrivedListener listener, int requestCode) {
        /*
        Retrieves locations based on the provided methodType.
Uses an HTTP GET request to ApiConstants.LOCATIONS_FIND_URI with the method type in the path.
Requires a valid CURRENT_TOKEN for authorization.
Parses the response JSON and returns a list of LocationModel objects.
Notifies the DataArrivedListener with the retrieved locations or an error message
         */
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet getMethod = new HttpGet(ApiConstants.LOCATIONS_FIND_URI + "/" + methodType);
                    getMethod.addHeader(HttpHeaders.AUTHORIZATION, "Bearer: " + BaseApiClient.CURRENT_TOKEN);
                    CloseableHttpResponse res = (CloseableHttpResponse) HTTP_CLIENT.execute(getMethod);
                    if (res.getCode() == HttpStatus.SC_OK) {
                        HttpEntity entity = res.getEntity();
                        JSONArray json = responseEntityToJsonArray(entity);
                        List<LocationModel> arrived = new ArrayList<>();
                        for(int i = 0; i < json.length(); i++)
                        {
                            LocationModel mmodel = LocationModel.fromJSONObject(json.getJSONObject(i));
                            arrived.add(mmodel);
                        }
                        //LocationModel arrived = LocationModel.fromJSONObject(json);
                        listener.onDataArrived(arrived,requestCode);
                    } else {
                        listener.onError(res.getCode() + " : " + res.toString(),requestCode);
                    }
                } catch (Exception e) {
                    Log.e("LocationClient", "addToFavorites: ", e);
                    listener.onError("Exception: " + e.getMessage(),requestCode);
                }
            }});
    }
    public static void addToFavorites(LocationModel place, DataArrivedListener listener, int requestCode) {
        EXECUTOR.execute(new Runnable() {
            /*
            Adds a location to the user's favorites.
Uses an HTTP POST request to ApiConstants.FAV_LOCATIONS_ADD_URI.
Sends the place information as a JSON object in the request body.
Requires a valid CURRENT_TOKEN for authorization.
Parses the response JSON and returns the updated LocationModel object.
Notifies the DataArrivedListener with the updated location or an error message
             */
            @Override
            public void run() {
                try {
                    HttpPost postMethod = new HttpPost(ApiConstants.FAV_LOCATIONS_ADD_URI);
                    JSONObject info = new JSONObject(place.toMap());

                    StringEntity requestEntity = new StringEntity(
                            info.toString(),
                            ContentType.APPLICATION_JSON);
                    postMethod.setEntity(requestEntity);
                    postMethod.addHeader("Authorization", "Bearer: " + BaseApiClient.CURRENT_TOKEN);

                    CloseableHttpResponse res = (CloseableHttpResponse) HTTP_CLIENT.execute(postMethod);
                    if (res.getCode() == HttpStatus.SC_OK) {
                        HttpEntity entity =  res.getEntity();

                        JSONObject json = responseEntityToJsonObject(entity);
                        Log.i("addFavorite", "ResponseToJson: " +  json);
                        LocationModel arrived = LocationModel.fromJSONObject(json);

                        listener.onDataArrived(arrived,requestCode);
                    } else {
                        listener.onError(res.getCode() + " : " + res.toString(),requestCode);
                    }
                } catch (Exception e) {
                    Log.e("LocationClient", "findLocation: ", e);
                    listener.onError("Exception: " + e.getMessage(),requestCode);
                }
            }});
    }

    public static void getMyFavoriteLocations(DataArrivedListener listener, int requestCode) {
        EXECUTOR.execute(new Runnable() {
            @Override
            /*
            Retrieves all of the user's favorite locations.
Uses an HTTP GET request to ApiConstants.FAV_LOCATIONS_ME_URI.
Requires a valid CURRENT_TOKEN for authorization.
Parses the response JSON and returns a list of LocationModel objects.
Notifies the DataArrivedListener with the retrieved locations or an error message
             */
            public void run() {
                try {
                    HttpGet getMethod = new HttpGet(ApiConstants.FAV_LOCATIONS_ME_URI);
                    getMethod.addHeader(HttpHeaders.AUTHORIZATION, "Bearer: " + BaseApiClient.CURRENT_TOKEN);
                    CloseableHttpResponse res = (CloseableHttpResponse) HTTP_CLIENT.execute(getMethod);
                    if (res.getCode() == HttpStatus.SC_OK) {
                        HttpEntity entity = res.getEntity();
                        JSONArray json = responseEntityToJsonArray(entity);
                        List<LocationModel> arrived = new ArrayList<>();
                        for(int i = 0; i < json.length(); i++)
                        {
                            LocationModel mmodel = LocationModel.fromJSONObject(json.getJSONObject(i));
                            arrived.add(mmodel);
                        }

                        listener.onDataArrived(arrived,requestCode);
                    } else {
                        listener.onError(res.getCode() + " : " + res.toString(),requestCode);
                    }
                } catch (Exception e) {
                    Log.e("LocationClient", "addToFavorites: ", e);
                    listener.onError("Exception: " + e.getMessage(),requestCode);
                }
            }});
    }
}
