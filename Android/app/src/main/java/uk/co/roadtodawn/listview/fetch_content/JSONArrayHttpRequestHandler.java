package uk.co.roadtodawn.listview.fetch_content;

public interface JSONArrayHttpRequestHandler {
    void makeRequest(final String url, final JSONFetcher.Callback callback);
}
