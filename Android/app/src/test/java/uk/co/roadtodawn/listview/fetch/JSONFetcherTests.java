package uk.co.roadtodawn.listview.fetch;

import org.json.JSONArray;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class JSONFetcherTests {

    abstract JSONFetcher getJsonFetcher();
    abstract void setRequestSuccess(boolean willSucceed);

    @Test
    public void fetchJsonArray_successfulFetch_callSuccessCallback(){
        JSONFetcher.Callback callback = mock(JSONFetcher.Callback.class);
        JSONFetcher jsonFetcher = getJsonFetcher();
        setRequestSuccess(true);
        jsonFetcher.fetchJSONArray(callback);
        verify(callback, times(1)).onJSONArrayFetched(any(JSONArray.class));
    }

    //TODO check content matches against expected values

    @Test
    public void fetchJsonArray_failedFetch_callFailureCallback(){
        JSONFetcher.Callback callback = mock(JSONFetcher.Callback.class);
        JSONFetcher jsonFetcher = getJsonFetcher();
        setRequestSuccess(false);
        jsonFetcher.fetchJSONArray(callback);
        verify(callback, times(1)).onFetchFailed(any(String.class));
    }

    //TODO check wrong callbacks are not called on success/failure
}