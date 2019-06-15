package uk.co.roadtodawn.listview;

public interface ListView {
    void displayList(ListItem[] items);
    void displayLoadFailedError();
    void setPresenter(final ListPresenter presenter);
}
