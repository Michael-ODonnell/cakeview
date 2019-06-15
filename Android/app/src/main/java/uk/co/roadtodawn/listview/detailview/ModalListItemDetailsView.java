package uk.co.roadtodawn.listview.detailview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.R;

public class ModalListItemDetailsView extends ConstraintLayout implements ListItemDetailsView {

    private TextView m_titleView;
    private NetworkImageView m_imageView;
    private TextView m_descriptionView;

    private ListItemDetailsPresenter m_listItemDetailsPresenter;

    private ListItemDetailsPresenter.OnDismissedCallback m_onDismissed;

    public ModalListItemDetailsView(Context context) {
        super(context);
    }

    public ModalListItemDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModalListItemDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View modal = getViewById(R.id.list_item_details_modal);
        View container = getViewById(R.id.list_item_detail_container);
        m_titleView = (TextView) getViewById(R.id.list_item_detail_title);
        m_imageView = (NetworkImageView) getViewById(R.id.list_item_detail_image);
        m_descriptionView = (TextView) getViewById(R.id.list_item_detail_description);

        OnClickListener dismissListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                m_onDismissed.onDismissed();
            }
        };

        modal.setOnClickListener(dismissListener);
        container.setOnClickListener(dismissListener);
    }

    @Override
    public void setPresenter(ListItemDetailsPresenter listItemDetailsPresenter) {
        m_listItemDetailsPresenter = listItemDetailsPresenter;
    }

    @Override
    public void displayItem(ListItem item, ListItemDetailsPresenter.OnDismissedCallback onDismissed) {
        m_onDismissed = onDismissed;
        m_titleView.setText(item.getTitle());
        m_listItemDetailsPresenter.loadImage(m_imageView, item.getImageUrl());
        m_imageView.setContentDescription(item.getDescription());
        m_descriptionView.setText(item.getDescription());
        fadeIn();
    }

    @Override
    public void dismiss(){
        fadeOut();
    }

    private void fadeIn() {
        setVisibility(VISIBLE);
    }

    private void fadeOut() {
        setVisibility(GONE);
    }
}
