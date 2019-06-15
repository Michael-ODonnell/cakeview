package uk.co.roadtodawn.listview.fetch;

public interface JSONArrayHttpRequestHandler {
    void makeRequest(final String url, final JSONFetcher.Callback callback);
}
