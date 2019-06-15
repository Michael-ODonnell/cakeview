package uk.co.roadtodawn.listview.fetch;

import uk.co.roadtodawn.listview.scrolllist.ScrollListPresenter;

import static org.mockito.Mockito.mock;

public class JSONListContentLoaderTests extends ListContentLoaderTests {

    @Override
    ListContentLoader getListContentLoader(){
        JSONFetcher jsonFetcher = mock(JSONFetcher.class);
        ScrollListPresenter.StateStore stateStore = mock(ScrollListPresenter.StateStore.class);
        return new JSONListContentLoader(jsonFetcher, stateStore);
    }

}