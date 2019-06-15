package uk.co.roadtodawn.listview.scrolllist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;
import uk.co.roadtodawn.listview.R;

class ListItemViewHolder extends RecyclerView.ViewHolder {
    private TextView m_textView;
    private NetworkImageView m_imageView;
    private ListPresenter m_listPresenter;

    private ListItem m_currentItem;

    ListItem getItem() { return m_currentItem; }

    ListItemViewHolder(@NonNull View itemView, ListPresenter listPresenter) {
        super(itemView);
        m_textView = itemView.findViewById(R.id.textView);
        m_imageView = itemView.findViewById(R.id.imageView);
        m_listPresenter = listPresenter;
    }

    void bind(ListItem item) {
        m_currentItem = item;
        m_textView.setText(item.getTitle());
        m_listPresenter.loadImage(m_imageView, item.getImageUrl());
        m_imageView.setContentDescription(item.getDescription());
    }
}
