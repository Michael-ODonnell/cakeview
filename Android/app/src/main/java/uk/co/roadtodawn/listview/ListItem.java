package uk.co.roadtodawn.listview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

public class ListItem implements Comparable<ListItem> {
    private String m_title;
    private String m_imageUrl;
    private String m_description;

    public ListItem(String title, String imageUrl, String description){
        m_title = title;
        m_imageUrl = imageUrl;
        m_description = description;
    }

    public String getTitle() { return m_title; }
    public String getImageUrl() { return m_imageUrl; }
    public String getDescription() { return m_description; }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ListItem)) {
            return false;
        }

        ListItem otherListItem = (ListItem) other;

        return  this.m_title.equals(otherListItem.m_title) &&
                this.m_imageUrl.equals(otherListItem.m_imageUrl) &&
                this.m_description.equals(otherListItem.m_description);
    }

    @Override
    public int compareTo(ListItem listItem) {
        return getTitle().compareTo(listItem.getTitle());
    }

    public static ListItem[] parseJsonArray(JSONArray array) throws JSONException {
        CopyOnWriteArraySet<ListItem> itemSet = new CopyOnWriteArraySet<>();
        for(int i = 0; i < array.length(); ++i){
            JSONObject jsonObject = array.getJSONObject(i);
            itemSet.add(new ListItem(
                    jsonObject.getString("title"),
                    jsonObject.getString("image"),
                    jsonObject.getString("desc")));
        }
        ListItem[] items = new ListItem[itemSet.size()];
        itemSet.toArray(items);
        Arrays.sort(items);
        return items;
    }
}
