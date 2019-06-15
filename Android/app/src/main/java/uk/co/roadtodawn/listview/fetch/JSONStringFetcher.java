package uk.co.roadtodawn.listview.fetch;

import org.json.JSONArray;
import org.json.JSONException;

public class JSONStringFetcher implements JSONFetcher {

    private String m_serializedJson;

    public JSONStringFetcher(String serializedJson) {
        m_serializedJson = serializedJson;
    }

    @Override
    public void fetchJSONArray(final Callback callback) {
        try {
            JSONArray array = new JSONArray(m_serializedJson);
            callback.onJSONArrayFetched(array);
        } catch (JSONException e) {
            callback.onFetchFailed("Invalid JSON string");
        }
    }
}
