package uk.co.roadtodawn.listview.fetch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class JSONArrayHttpRequestQueue implements JSONArrayHttpRequestHandler {
    private RequestQueue m_requestQueue;

    public JSONArrayHttpRequestQueue(RequestQueue requestQueue) {
        m_requestQueue = requestQueue;
    }

    @Override
    public void makeRequest(final String url, final JSONFetcher.Callback callback) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onJSONArrayFetched(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String reason;
                        Throwable cause = error.getCause();
                        if(cause != null) {
                            reason = cause.getLocalizedMessage();
                        }
                        else{
                            reason = error.getLocalizedMessage();
                        }

                        callback.onFetchFailed(reason);
                    }
                });
        m_requestQueue.add(jsonArrayRequest);
    }
}
