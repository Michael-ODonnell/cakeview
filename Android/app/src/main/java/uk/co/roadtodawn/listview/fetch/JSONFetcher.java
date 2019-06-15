package uk.co.roadtodawn.listview.fetch;

import org.json.JSONArray;

public interface JSONFetcher {
    interface Callback {
        void onJSONArrayFetched(JSONArray array);
        void onFetchFailed();
    }

    void fetchJSONArray(Callback callback);
}
