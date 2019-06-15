package uk.co.roadtodawn.listview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import uk.co.roadtodawn.listview.detailview.ListItemDetailsPresenter;
import uk.co.roadtodawn.listview.detailview.ListItemDetailsView;
import uk.co.roadtodawn.listview.detailview.ModalListItemDetailsPresenter;
import uk.co.roadtodawn.listview.fetch.JSONArrayHttpRequestQueue;
import uk.co.roadtodawn.listview.fetch.JSONFetcher;
import uk.co.roadtodawn.listview.fetch.JSONHttpFetcher;
import uk.co.roadtodawn.listview.scrolllist.ScrollListPresenter;

public class MainActivity extends AppCompatActivity {

    RequestQueue m_requestQueue;
    ListPresenter m_listPresenter;
    ListItemDetailsPresenter m_listItemDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_requestQueue = Volley.newRequestQueue(this);

        ImageLoader imageLoader = new ImageLoader(m_requestQueue,
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
        JSONFetcher jsonFetcher = new JSONHttpFetcher(listUrl, new JSONArrayHttpRequestQueue(m_requestQueue));

        getLayoutInflater().inflate(R.layout.list, main, true);
        ListView listView = main.findViewById(R.id.scroll_list);
        m_listPresenter = new ScrollListPresenter(listView, jsonFetcher, imageLoader);

        getLayoutInflater().inflate(R.layout.list_item_details, main, true);
        ListItemDetailsView listItemDetailsView = main.findViewById(R.id.list_item_details_modal);
        m_listItemDetailsPresenter = new ModalListItemDetailsPresenter(m_listPresenter, listItemDetailsView, imageLoader);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_listItemDetailsPresenter.destroy();
        m_listPresenter.destroy();
    }
}
