package in.afckstechnologies.afcksmaintenanceapp.utils;

import android.content.Context;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;




@SuppressWarnings({ "deprecation", "deprecation" })
public class WebClient {
    Context context;

        String TAG = "ServiceAccess";
        String response ="";
        String baseURL = "";
        @SuppressWarnings({ "deprecation", "resource" })
		public String SendHttpPost(String URL, JSONObject jsonObjSend) {

            try {
                HttpClient client = new DefaultHttpClient();

                HttpPost post = new HttpPost(URL);
               // HttpGet post = new HttpGet(URL);
                post.setHeader("Content-type", "application/json; charset=UTF-8");
                post.setHeader("Accept", "application/json");
                post.setEntity(new StringEntity(jsonObjSend.toString(), "UTF-8"));
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10*1000); 
                HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),10*1000); 
                HttpResponse response = client.execute(post);
                Log.i(TAG,"resoponse"+response);
                HttpEntity entity = response.getEntity();

                return EntityUtils.toString(entity);

            }catch (Exception e) {
                // TODO: handle exception
                Log.i(TAG, "exception" + e);
            }
            Log.i(TAG, "response" + response);
            return response;
        }

    }


