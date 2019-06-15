package uk.co.roadtodawn.listview.detailview;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;

public class ModalListItemDetailsPresenter implements ListItemDetailsPresenter {

    private ListItemDetailsView m_listItemDetailsView;
    private ImageLoader m_imageLoader;

    private ListPresenter m_listPresenter;
    private ListPresenter.ItemSelectedObserver m_itemSelectedObserver;

    public ModalListItemDetailsPresenter(ListPresenter listPresenter, ListItemDetailsView listItemDetailsView, ImageLoader imageLoader){
        m_listItemDetailsView = listItemDetailsView;
        m_imageLoader = imageLoader;
        m_listItemDetailsView.setPresenter(this);
        m_listItemDetailsView.dismiss();
        m_listPresenter = listPresenter;

        m_itemSelectedObserver = new ListPresenter.ItemSelectedObserver() {
            @Override
            public void onItemSelected(ListItem item) {
                display(item);
            }
        };

        listPresenter.registerOnItemSelectedObserver(m_itemSelectedObserver);
    }

    @Override
    public void destroy() {
        m_listPresenter.unregisterOnItemSelectedObserver(m_itemSelectedObserver);
    }

    @Override
    public void display(ListItem item) {
        m_listItemDetailsView.displayItem(item, new OnDismissedCallback() {
            @Override
            public void onDismissed() {
                m_listItemDetailsView.dismiss();
            }
        });
    }

    @Override
    public void loadImage(NetworkImageView imageView, String url) {
        imageView.setImageUrl(url, m_imageLoader);
    }
}
