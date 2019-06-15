package uk.co.roadtodawn.listview.fetch;

import uk.co.roadtodawn.listview.ListItem;

public interface ListContentLoader {
    interface Observer {
        void onContentReady(ListItem[] content);
        void onLoadFailed();
    }

    void loadContent();
    void registerListContentObserver(ListContentLoader.Observer observer);
    void unregisterListContentObserver(ListContentLoader.Observer observer);
}
