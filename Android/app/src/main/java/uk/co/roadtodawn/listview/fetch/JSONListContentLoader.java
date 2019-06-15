package uk.co.roadtodawn.listview.fetch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

import uk.co.roadtodawn.listview.ListItem;

public class JSONListContentLoader implements ListContentLoader {

    private ArrayList<ListContentLoader.Observer> m_observers;
    private JSONFetcher m_jsonFetcher;
    private JSONFetcher.Callback m_onFetchCompleteCallback;

    public JSONListContentLoader(JSONFetcher jsonFetcher) {
        m_observers = new ArrayList<>();
        m_jsonFetcher = jsonFetcher;
        m_onFetchCompleteCallback = new JSONFetcher.Callback(){

            @Override
            public void onJSONArrayFetched(JSONArray array) {
                parseJsonArray(array);
            }

            @Override
            public void onFetchFailed(String reason) {
                notifyFetchFailed(reason);
            }
        };
    }

    @Override
    public void loadContent() {
        m_jsonFetcher.fetchJSONArray(m_onFetchCompleteCallback);
    }

    @Override
    public void registerListContentObserver(ListContentLoader.Observer observer) {
        m_observers.add(observer);
    }

    @Override
    public void unregisterListContentObserver(ListContentLoader.Observer observer) {
        m_observers.remove(observer);
    }

    private void parseJsonArray(JSONArray array) {
        CopyOnWriteArraySet<ListItem> items = new CopyOnWriteArraySet<>();
        try {
            for(int i = 0; i < array.length(); ++i){
                JSONObject jsonObject = array.getJSONObject(i);
                items.add(new ListItem(
                        jsonObject.getString("title"),
                        jsonObject.getString("image"),
                        jsonObject.getString("desc")));
            }
        } catch (JSONException e) {
            notifyFetchFailed("Failed to parse list");
            return;
        }
        ListItem[] itemsArray = new ListItem[items.size()];
        items.toArray(itemsArray);
        Arrays.sort(itemsArray);
        for(Observer observer: m_observers) {
            observer.onContentReady(itemsArray);
        }
    }

    private void notifyFetchFailed(String reason) {
        for(Observer observer: m_observers) {
            observer.onLoadFailed(reason);
        }
    }
}
