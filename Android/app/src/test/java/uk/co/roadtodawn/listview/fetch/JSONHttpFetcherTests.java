package uk.co.roadtodawn.listview.fetch;

import org.json.JSONArray;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JSONHttpFetcherTests extends JSONFetcherTests {

    private boolean m_willRequestSucceed = true;

    @Override
    JSONFetcher getJsonFetcher() {
        JSONArrayHttpRequestHandler requestHandler = mock(JSONArrayHttpRequestHandler.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                JSONFetcher.Callback callback = invocation.getArgument(1);
                if(m_willRequestSucceed){
                    callback.onJSONArrayFetched(new JSONArray());
                }
                else{
                    callback.onFetchFailed();
                }
                return null;
            }
        }).when(requestHandler).makeRequest(any(String.class), any(JSONFetcher.Callback.class));

        return new JSONHttpFetcher("https://not.a.url", requestHandler);
    }

    @Override
    void setRequestSuccess(boolean willSucceed) {
        m_willRequestSucceed = willSucceed;
    }
}