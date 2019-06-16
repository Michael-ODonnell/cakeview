package uk.co.roadtodawn.listview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import uk.co.roadtodawn.listview.detailview.ListItemDetailsPresenter;
import uk.co.roadtodawn.listview.detailview.ListItemDetailsView;
import uk.co.roadtodawn.listview.detailview.ModalListItemDetailsPresenter;
import uk.co.roadtodawn.listview.fetch.JSONArrayHttpRequestQueue;
import uk.co.roadtodawn.listview.fetch.JSONFetcher;
import uk.co.roadtodawn.listview.fetch.JSONHttpFetcher;
import uk.co.roadtodawn.listview.scrolllist.ScrollListPresenter;

public class MainActivity extends AppCompatActivity {

    private ListPresenter m_listPresenter;
    private ListItemDetailsPresenter m_listItemDetailsPresenter;

    private final String ListPresenterStateKey = "listPresenterState";

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putString(ListPresenterStateKey, m_listPresenter.getSaveState());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        ImageLoader imageLoader = new ImageLoader(requestQueue,
            new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap>
                        cache = new LruCache<>(20);

                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            }
        );

        ViewGroup main = findViewById(R.id.main);

        final String listUrl = getResources().getString(R.string.list_url);
        JSONFetcher jsonFetcher = new JSONHttpFetcher(listUrl, new JSONArrayHttpRequestQueue(requestQueue));

        getLayoutInflater().inflate(R.layout.list, main, true);
        ListView listView = main.findViewById(R.id.scroll_list);
        if(hasSavedState(savedInstanceState)) {
            m_listPresenter = new ScrollListPresenter(listView, jsonFetcher, imageLoader, savedInstanceState.getString(ListPresenterStateKey));
        }
        else{
            m_listPresenter = new ScrollListPresenter(listView, jsonFetcher, imageLoader);
        }

        getLayoutInflater().inflate(R.layout.list_item_details, main, true);
        ListItemDetailsView listItemDetailsView = main.findViewById(R.id.list_item_details_modal);
        m_listItemDetailsPresenter = new ModalListItemDetailsPresenter(m_listPresenter, listItemDetailsView, imageLoader);
    }

    private boolean hasSavedState(Bundle savedInstanceState ){
        return (savedInstanceState != null &&
                savedInstanceState.containsKey(ListPresenterStateKey) &&
                savedInstanceState.getString(ListPresenterStateKey) != null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_listItemDetailsPresenter.destroy();
        m_listPresenter.destroy();
    }
}
