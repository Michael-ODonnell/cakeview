package uk.co.roadtodawn.listview.detailview;

import com.android.volley.toolbox.ImageLoader;

import uk.co.roadtodawn.listview.ListPresenter;

import static org.mockito.Mockito.mock;

class ModalListItemDetailsPresenterTests extends ListItemDetailsPresenterTests {

    @Override
    ListItemDetailsPresenter getListItemDetailsPresenterForView(ListItemDetailsView view) {
        ListPresenter listPresenter = mock(ListPresenter.class);
        ImageLoader imageLoader = mock(ImageLoader.class);
        return new ModalListItemDetailsPresenter(listPresenter, view, imageLoader);
    }

    // TODO test listPresenter.selectItem triggers ModalListItemDetailsPresenter.displayItem (with same item)
}