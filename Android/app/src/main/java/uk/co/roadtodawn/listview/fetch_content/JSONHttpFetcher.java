package uk.co.roadtodawn.listview.fetch_content;

public class JSONHttpFetcher implements JSONFetcher {

    private String m_jsonUrl;
    private JSONArrayHttpRequestHandler m_requestHandler;

    public JSONHttpFetcher(String url, JSONArrayHttpRequestHandler requestHandler) {
        m_jsonUrl = url;
        m_requestHandler = requestHandler;
    }

    @Override
    public void fetchJSONArray(final Callback callback) {
        m_requestHandler.makeRequest(m_jsonUrl, callback);
    }
}
