package uk.co.roadtodawn.listview.scrolllist;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;
import uk.co.roadtodawn.listview.ListView;
import uk.co.roadtodawn.listview.fetch.JSONFetcher;
import uk.co.roadtodawn.listview.fetch.JSONListContentLoader;
import uk.co.roadtodawn.listview.fetch.ListContentLoader;

public class ScrollListPresenter implements ListPresenter {

    private ArrayList<ItemSelectedObserver> m_itemSelectedObservers;

    private ListContentLoader m_listContentLoader;

    private ImageLoader m_imageLoader;

    private ListView m_listView;

    private ListContentLoader.Observer m_contentObserver;

    public ScrollListPresenter(ListView listView,
                               JSONFetcher jsonFetcher,
                               ImageLoader imageLoader){

        m_imageLoader = imageLoader;
        m_itemSelectedObservers = new ArrayList<>();
        m_listContentLoader = new JSONListContentLoader(jsonFetcher);

        m_listView = listView;
        m_listView.setPresenter(this);

        m_contentObserver = new ListContentLoader.Observer() {
            @Override
            public void onContentReady(ListItem[] content) {
                m_listView.displayList(content);
            }

            @Override
            public void onLoadFailed() {
                m_listView.displayLoadFailedError();
            }
        };

        m_listContentLoader.registerListContentObserver(m_contentObserver);

        m_listContentLoader.loadContent();
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
    public void registerOnItemSelectedObserver(ItemSelectedObserver observer) {
        m_itemSelectedObservers.add(observer);
    }

    @Override
    public void unregisterOnItemSelectedObserver(ItemSelectedObserver observer) {
        m_itemSelectedObservers.remove(observer);
    }
}