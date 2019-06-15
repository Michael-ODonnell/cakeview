package uk.co.roadtodawn.listview.scrolllist;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;
import uk.co.roadtodawn.listview.ListView;
import uk.co.roadtodawn.listview.fetch.JSONFetcher;
import uk.co.roadtodawn.listview.fetch.JSONListContentLoader;
import uk.co.roadtodawn.listview.fetch.JSONStringFetcher;
import uk.co.roadtodawn.listview.fetch.ListContentLoader;

public class ScrollListPresenter implements ListPresenter {

    public interface StateStore {
        void persistState(String state);
    }

    private ArrayList<ItemSelectedObserver> m_itemSelectedObservers;

    private ImageLoader m_imageLoader;

    private ListView m_listView;

    private ListContentLoader.Observer m_contentObserver;

    private String m_state;

    private StateStore m_stateStore;

    private ListContentLoader m_listContentLoader;

    public ScrollListPresenter(ListView listView,
                               JSONFetcher jsonFetcher,
                               ImageLoader imageLoader){

        init(listView, imageLoader);

        m_listContentLoader = new JSONListContentLoader(jsonFetcher, m_stateStore);
        m_listContentLoader.registerListContentObserver(m_contentObserver);
        m_listContentLoader.loadContent();
    }

    public ScrollListPresenter(ListView listView,
                               JSONFetcher jsonFetcher,
                               ImageLoader imageLoader,
                               String savedState){
        init(listView, imageLoader);
        loadSavedState(savedState, jsonFetcher);
    }

    private void init(ListView listView,
                      ImageLoader imageLoader) {

        m_imageLoader = imageLoader;
        m_itemSelectedObservers = new ArrayList<>();

        m_listView = listView;
        m_listView.setPresenter(this);

        m_contentObserver = new ListContentLoader.Observer() {
            @Override
            public void onContentReady(ListItem[] content) {
                m_listView.displayList(content);
            }

            @Override
            public void onLoadFailed(String reason) {
                m_listView.displayLoadFailedError(reason);
            }
        };

        m_state = null;

        m_stateStore = new ScrollListPresenter.StateStore() {
            @Override
            public void persistState(String state) {
                m_state = state;
            }
        };
    }

    private void loadSavedState(final String savedState, final JSONFetcher jsonFetcher){

        JSONStringFetcher stringFetcher = new JSONStringFetcher(savedState);
        stringFetcher.fetchJSONArray(new JSONFetcher.Callback() {
            @Override
            public void onJSONArrayFetched(JSONArray array) {
                m_listContentLoader = new JSONListContentLoader(jsonFetcher, array, m_stateStore);
                m_listContentLoader.registerListContentObserver(m_contentObserver);
                m_state = array.toString();
                try{
                    m_listView.displayList(ListItem.parseJsonArray(array));
                } catch (JSONException e) {
                    //TODO inject string lookup abstraction
                    m_listView.displayLoadFailedError("Failed to parse saved list");
                }
            }

            @Override
            public void onFetchFailed(String reason) {
                m_listView.displayLoadFailedError(reason);
                m_listContentLoader = new JSONListContentLoader(jsonFetcher, m_stateStore);
                m_listContentLoader.registerListContentObserver(m_contentObserver);
                m_listContentLoader.loadContent();
            }
        });
    }

    @Override
    public void destroy() {
        m_listContentLoader.unregisterListContentObserver(m_contentObserver);
    }

    @Override
    public void selectItem(ListItem item) {
        for(ItemSelectedObserver observer : m_itemSelectedObservers) {
            observer.onItemSelected(item);
        }
    }

    @Override
    public void loadItems() {
        m_listContentLoader.loadContent();
    }

    @Override
    public void refresh() {
        m_listView.displayList(new ListItem[0]);
        loadItems();
    }

    @Override
    public void loadImage(NetworkImageView imageView, String url) {
        imageView.setImageUrl(url, m_imageLoader);
    }

    @Override
    public String getSaveState() {
        return m_state;
    }

    @Override
    public void registerOnItemSelectedObserver(ItemSelectedObserver observer) {
        m_itemSelectedObservers.add(observer);
    }

    @Override
    public void unregisterOnItemSelectedObserver(ItemSelectedObserver observer) {
        m_itemSelectedObservers.remove(observer);
    }
}
