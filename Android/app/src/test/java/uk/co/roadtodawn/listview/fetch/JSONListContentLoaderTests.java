package uk.co.roadtodawn.listview.fetch;

import static org.mockito.Mockito.mock;

public class JSONListContentLoaderTests extends ListContentLoaderTests {

    @Override
    ListContentLoader getListContentLoader(){
        JSONFetcher jsonFetcher = mock(JSONFetcher.class);
        return new JSONListContentLoader(jsonFetcher);
    }

}