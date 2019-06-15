package uk.co.roadtodawn.listview;

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
}
