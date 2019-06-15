package uk.co.roadtodawn.listview;

import com.android.volley.toolbox.NetworkImageView;

public interface ListPresenter {

    interface ItemSelectedObserver {
        void onItemSelected(ListItem item);
    }

    void destroy();

    void selectItem(ListItem item);
    void loadItems();
    void refresh();
    void loadImage(NetworkImageView imageView, String url);
    String getSaveState();

    void registerOnItemSelectedObserver(ItemSelectedObserver observer);
    void unregisterOnItemSelectedObserver(ItemSelectedObserver observer);
}
