package sa.edu.yamamh.riyadhgo.network;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.time.LocalDateTime;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.data.RouteModel;

/**
 * The trip client is used to send the trip data to the server
 */
public class TripClient extends BaseApiClient {//This class extends the BaseApiClient and provides
    // methods specifically for interacting with trip data on the backend API

    public static void doMove(RouteModel route, DataArrivedListener listener,int requestCode) {
        route.setTime(LocalDateTime.now());
        try {
            /*
            Saves a new route point (user's movement) on the server.
- Uses an HTTP POST request to ApiConstants.ROUTES_URI.
- Sets the current timestamp on the provided route model.
- Sends the route information as a JSON object in the request body.
- Requires a valid CURRENT_TOKEN for authorization.
- Notifies the DataArrivedListener with a success message or an error message depending on the response code
             */
            JSONObject info = new JSONObject(route.toMap());
            StringEntity requestEntity = new StringEntity(
                    info.toString(),
                    ContentType.APPLICATION_JSON);
            HttpPost postMethod = new HttpPost(ApiConstants.ROUTES_URI);
            postMethod.setEntity(requestEntity);
            CloseableHttpResponse res  = (CloseableHttpResponse) HTTP_CLIENT.execute(postMethod);
            if (res.getCode() == HttpStatus.SC_OK)
            {
                listener.onDataArrived(ApiConstants.SUCCESS,requestCode);
            }
            else if(res.getCode() == HttpStatus.SC_UNAUTHORIZED)
            {
                listener.onError(ApiConstants.UN_AUTHORIZED,requestCode);
            }
        } catch (Exception e) {
            listener.onError(e.getMessage(),requestCode);
        }
    }

}
