package uk.co.roadtodawn.listview.fetch;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.scrolllist.ScrollListPresenter;

public class JSONListContentLoader implements ListContentLoader {

    private ScrollListPresenter.StateStore m_stateStore;
    private ArrayList<ListContentLoader.Observer> m_observers;
    private JSONFetcher m_jsonFetcher;
    private JSONFetcher.Callback m_onFetchCompleteCallback;

    public JSONListContentLoader(JSONFetcher jsonFetcher, ScrollListPresenter.StateStore stateStore) {
        init(jsonFetcher, stateStore);
    }

    public JSONListContentLoader(JSONFetcher jsonFetcher, JSONArray initialState, ScrollListPresenter.StateStore stateStore) {
        init(jsonFetcher, stateStore);
        parseJsonArray(initialState);
    }

    private void init(JSONFetcher jsonFetcher, ScrollListPresenter.StateStore stateStore){
        m_stateStore = stateStore;
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
        ListItem[] items;
        try{
            items = ListItem.parseJsonArray(array);
        } catch (JSONException e) {
            notifyFetchFailed("Failed to parse list");
            return;
        }
        m_stateStore.persistState(array.toString());
        for(Observer observer: m_observers) {
            observer.onContentReady(items);
        }
    }

    private void notifyFetchFailed(String reason) {
        for(Observer observer: m_observers) {
            observer.onLoadFailed(reason);
        }
    }
}
