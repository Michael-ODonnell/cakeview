package uk.co.roadtodawn.listview.scrolllist;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;
import uk.co.roadtodawn.listview.ListView;
import uk.co.roadtodawn.listview.fetch.JSONFetcher;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ScrollListPresenterTests extends ListPresenterTests {

    @Override
    ListPresenter createListPresenter() {
        ListView listView = mock(ListView.class);
        JSONFetcher jsonFetcher = mock(JSONFetcher.class);
        ImageLoader imageLoader = mock(ImageLoader.class);
        return new ScrollListPresenter(listView, jsonFetcher, imageLoader);
    }

    private JSONArray getTestData(String jsonResourcePath)  {
        try{
            ClassLoader classLoader = Objects.requireNonNull(this.getClass().getClassLoader());
            InputStream is = classLoader.getResourceAsStream(jsonResourcePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            return new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        return new JSONArray();
    }


    @Test
    public void startup_LoadSuccess_TellViewToDisplayList()
    {
        ListView listView = mock(ListView.class);
        JSONFetcher jsonFetcher = mock(JSONFetcher.class);
        ImageLoader imageLoader = mock(ImageLoader.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                JSONFetcher.Callback callback = invocation.getArgument(0);
                callback.onJSONArrayFetched(getTestData("defaultList.json"));
                return null;
            }
        }).when(jsonFetcher).fetchJSONArray(any(JSONFetcher.Callback.class));

        ListPresenter presenter = new ScrollListPresenter(listView, jsonFetcher, imageLoader);

        verify(listView, times(1)).displayList(any(ListItem[].class));
    }

    @Test
    public void startup_LoadSuccess_DisplayListIsSorted()
    {
        ListView listView = mock(ListView.class);
        JSONFetcher jsonFetcher = mock(JSONFetcher.class);
        ImageLoader imageLoader = mock(ImageLoader.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                JSONFetcher.Callback callback = invocation.getArgument(0);
            callback.onJSONArrayFetched(getTestData("unsortedList.json"));
            return null;
            }
        }).when(jsonFetcher).fetchJSONArray(any(JSONFetcher.Callback.class));

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
            ListItem[] items = invocation.getArgument(0);
            for(int i = 1; i < items.length; ++i){
                if(items[i-1].getTitle().compareTo(items[i].getTitle()) > 0){
                    fail();
                }
            }

            return null;
            }
        }).when(listView).displayList(any(ListItem[].class));

        ListPresenter presenter = new ScrollListPresenter(listView, jsonFetcher, imageLoader);
        verify(listView, times(1)).displayList(any(ListItem[].class));
    }

    @Test
    public void startup_LoadFailed_TellViewToDisplayError()
    {
        ListView listView = mock(ListView.class);
        JSONFetcher jsonFetcher = mock(JSONFetcher.class);
        ImageLoader imageLoader = mock(ImageLoader.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                JSONFetcher.Callback callback = invocation.getArgument(0);
                callback.onFetchFailed("Failure was inevitable");
                return null;
            }
        }).when(jsonFetcher).fetchJSONArray(any(JSONFetcher.Callback.class));

        ListPresenter presenter = new ScrollListPresenter(listView, jsonFetcher, imageLoader);

        verify(listView, times(1)).displayLoadFailedError(any(String.class));
    }
}
