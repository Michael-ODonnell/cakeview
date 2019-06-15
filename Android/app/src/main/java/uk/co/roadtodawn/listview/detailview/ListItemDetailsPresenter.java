package uk.co.roadtodawn.listview.detailview;

import com.android.volley.toolbox.NetworkImageView;

import uk.co.roadtodawn.listview.ListItem;

public interface ListItemDetailsPresenter {

    interface OnDismissedCallback {
        void onDismissed();
    }

    void destroy();

    void display(ListItem item);

    void loadImage(NetworkImageView imageView, String url);
}
