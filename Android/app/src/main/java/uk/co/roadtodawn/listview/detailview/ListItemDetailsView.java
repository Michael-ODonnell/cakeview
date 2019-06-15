package uk.co.roadtodawn.listview.detailview;

import uk.co.roadtodawn.listview.ListItem;

public interface ListItemDetailsView {
    void displayItem(ListItem item, ListItemDetailsPresenter.OnDismissedCallback onDismissed);
    void dismiss();
    void setPresenter(ListItemDetailsPresenter presenter);
}
