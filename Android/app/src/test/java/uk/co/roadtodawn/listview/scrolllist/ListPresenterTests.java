package uk.co.roadtodawn.listview.scrolllist;

import org.junit.Test;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class ListPresenterTests {

    abstract ListPresenter createListPresenter();

    //event handlers

    @Test
    public void onItemClicked_OnInvoked_TriggersCallback() {
        ListPresenter presenter = createListPresenter();
        ListPresenter.ItemSelectedObserver observer = mock(ListPresenter.ItemSelectedObserver.class);
        presenter.registerOnItemSelectedObserver(observer);
        ListItem item = new ListItem("Title", "https://not.a.url", "Description");
        presenter.selectItem(item);
        verify(observer, times(1)).onItemSelected(item);
    }

    //TODO test unregister callbacks

    //endregion
}
