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
import sa.edu.yamamh.riyadhgo.data.SelectTransportMethodModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodModel;

public class TransportMethodClient extends BaseApiClient {

    public static void selectTransportMethods(DataArrivedListener listener, int requestCode) {
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet getMethod = new HttpGet(ApiConstants.TRANSPORT_METHODS_SELECT_URI);
                    getMethod.addHeader(HttpHeaders.AUTHORIZATION, "Bearer: " + BaseApiClient.CURRENT_TOKEN);
                    CloseableHttpResponse res = (CloseableHttpResponse) HTTP_CLIENT.execute(getMethod);
                    if (res.getCode() == HttpStatus.SC_OK) {
                        List<SelectTransportMethodModel> arrived = httpEntityToSelectMethodList(res.getEntity());
                        listener.onDataArrived(arrived, requestCode);
                    } else {
                        listener.onError(res.toString(), requestCode);
                    }
                } catch (Exception e) {
                    Log.e("GetTransMethods", e.getMessage(), e);
                    listener.onError(e.toString(), requestCode);
                }
            }});
    }
    public static void findTransportMethodsByType(String type, DataArrivedListener listener, int requestCode) {
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet getMethod = new HttpGet(ApiConstants.TRANSPORT_METHODS_FIND_URI + "/" + type);
                    getMethod.addHeader(HttpHeaders.AUTHORIZATION, "Bearer: " + BaseApiClient.CURRENT_TOKEN);
                    CloseableHttpResponse res = (CloseableHttpResponse) HTTP_CLIENT.execute(getMethod);
                    if (res.getCode() == HttpStatus.SC_OK) {
                        List<TransportMethodModel> arrived = httpEntityToMethodList(res.getEntity());
                        listener.onDataArrived(arrived, requestCode);
                    } else {
                        listener.onError(res.toString(), requestCode);
                    }
                } catch (Exception e) {
                    Log.e("GetTransMethods", e.getMessage(), e);
                    listener.onError(e.toString(), requestCode);
                }
            }});
    }
    private static List<SelectTransportMethodModel> httpEntityToSelectMethodList(HttpEntity entity)
    {
        List<SelectTransportMethodModel> list = new ArrayList<>();
        try {

            JSONArray mJson = responseEntityToJsonArray(entity);
            for(int i = 0; i < mJson.length(); i++)
            {
                JSONObject obj = mJson.getJSONObject(i);
                SelectTransportMethodModel model = SelectTransportMethodModel.fromJSONObject(obj);
                list.add(model);
            }
        }catch (Exception ex)
        {
            Log.e("TransportMethodClient", ex.getMessage());

        }
        finally {
            return list;
        }
    }
    private static List<TransportMethodModel> httpEntityToMethodList(HttpEntity entity)
    {
        List<TransportMethodModel> list = new ArrayList<>();
        try {

                JSONArray mJson = responseEntityToJsonArray(entity);
                for(int i = 0; i < mJson.length(); i++)
                {
                    JSONObject obj = mJson.getJSONObject(i);
                    TransportMethodModel model = TransportMethodModel.fromJSONObject(obj);
                    list.add(model);
                }
        }catch (Exception ex)
        {
            Log.e("TransportMethodClient", ex.getMessage());

        }
        finally {
            return list;
        }
    }

    public static void addTransportMethod(TransportMethodModel transportMethod, DataArrivedListener listener,int requestCode) {
        try {
            JSONObject info = new JSONObject(transportMethod.toMap());
            StringEntity requestEntity = new StringEntity(
                    info.toString(),
                    ContentType.APPLICATION_JSON);
            HttpPost postMethod = new HttpPost(ApiConstants.TRANSPORT_METHODS_URI);
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
