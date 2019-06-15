package uk.co.roadtodawn.listview.detailview;

abstract class ListItemDetailsPresenterTests {

    abstract ListItemDetailsPresenter getListItemDetailsPresenterForView(ListItemDetailsView view);

    //TODO test displayItem passes call to ListItemDetailsView.displayItem
    //TODO test invoking OnDismissedCallback passed to ListItemDetailsView.displayItem leads to call on ListItemDetailsView.dismiss
}