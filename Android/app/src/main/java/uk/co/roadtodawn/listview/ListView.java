package uk.co.roadtodawn.listview;

public interface ListView {
    void displayList(ListItem[] items);
    void displayLoadFailedError(String reason);
    void setPresenter(final ListPresenter presenter);
}
