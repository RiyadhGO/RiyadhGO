package sa.edu.yamamh.riyadhgo.network;

import android.util.Log;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.BasicHttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.data.LoginModel;
import sa.edu.yamamh.riyadhgo.data.RouteModel;
import sa.edu.yamamh.riyadhgo.data.UserModel;

public class BaseApiClient {

    public static Executor EXECUTOR = Executors.newSingleThreadExecutor();
    public static HttpClient HTTP_CLIENT = HttpClients.createDefault();
    public static String CURRENT_TOKEN = "";
    public static boolean IS_LOGGED_IN = false;


    /**
     * This method is used to login the user
     * @param model the login model
     * @return
     */
    public static void doLogin(LoginModel model, DataArrivedListener listener, int requestCode) {

        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    String token = "";

                    JSONObject info = new JSONObject(model.toMap());

                    StringEntity requestEntity = new StringEntity(
                            info.toString(),
                            ContentType.APPLICATION_JSON);
                    HttpPost postMethod = new HttpPost(ApiConstants.LOGIN_URI);
                    postMethod.setEntity(requestEntity);

                    CloseableHttpResponse res  = (CloseableHttpResponse) HTTP_CLIENT.execute(postMethod);

                    if (res.getCode() == HttpStatus.SC_OK)
                    {
                        byte[] data = new byte[(int)res.getEntity().getContentLength()];
                        BufferedInputStream is = new BufferedInputStream(res.getEntity().getContent());
                        is.read(data, 0, data.length);
                        token = new String(data, "UTF-8");
                        CURRENT_TOKEN = token.trim();
                        IS_LOGGED_IN = true;
                        listener.onDataArrived(token,requestCode);
                    }
                    else if(res.getCode() == HttpStatus.SC_UNAUTHORIZED)
                    {
                        listener.onError("Login error : user name or password is incorrect",requestCode);
                    }
                } catch (Exception e) {
                    Log.e("BaseApiClient", "doLogin: ", e);
                    listener.onError("Exception: " + e.getMessage(), requestCode);
                }
            }
        });


    }

    public static void doRegister(UserModel model, DataArrivedListener listener, int requestCode){
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject info = new JSONObject(model.toMap());
                    StringEntity requestEntity = new StringEntity(
                            info.toString(),
                            ContentType.APPLICATION_JSON);
                    HttpPost postMethod = new HttpPost(ApiConstants.REGISTER_URI);
                    postMethod.setEntity(requestEntity);
                    CloseableHttpResponse res = (CloseableHttpResponse) HTTP_CLIENT.execute(postMethod);
                    if (res.getCode() == HttpStatus.SC_OK) {

                        listener.onDataArrived(ApiConstants.SUCCESS,requestCode);
                    } else if (res.getCode() == HttpStatus.SC_UNAUTHORIZED) {
                        listener.onError("Registration error : please review your inputs.",requestCode);
                    }
                } catch (Exception e) {
                    listener.onError(e.getMessage(),requestCode);
                }
            }});
    }

    public static JSONObject responseEntityToJsonObject(HttpEntity entity) {
        try {
            if (entity != null) {
                String retSrc = EntityUtils.toString(entity);
                Log.i("BaseAPIClientJSONObject",retSrc);
                JSONObject result = new JSONObject(retSrc); //Convert String to JSON Object

                return result;
            }
        } catch (Exception ex) {
            Log.e("EntityToObjectEx", ex.getMessage(), ex);

        }
        return new JSONObject();
    }

    public static JSONArray responseEntityToJsonArray(HttpEntity entity) {
        try {
            if (entity != null) {
                String retSrc = EntityUtils.toString(entity, Charset.defaultCharset());
                Log.i("BaseAPIClientJSONArray",retSrc);
                JSONArray result = new JSONArray(retSrc); //Convert String to JSON Object

                return result;
            }
        } catch (Exception ex) {
            Log.e("EntityToObjectEx", ex.getMessage(), ex);

        }
        return new JSONArray();
    }
}
