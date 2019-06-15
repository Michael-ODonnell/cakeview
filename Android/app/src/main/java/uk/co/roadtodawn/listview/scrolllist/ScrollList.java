package uk.co.roadtodawn.listview.scrolllist;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Toast;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;
import uk.co.roadtodawn.listview.ListView;
import uk.co.roadtodawn.listview.R;

public class ScrollList extends ConstraintLayout implements ListView {

    private ScrollListAdapter m_scrollListAdapter;
    private SwipeRefreshLayout m_refreshLayout;
    private RecyclerView m_recyclerView;

    public ScrollList(Context context) {
        super(context);
    }

    public ScrollList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPresenter(final ListPresenter presenter) {
        m_scrollListAdapter = new ScrollListAdapter(
            (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
            presenter
        );

        m_recyclerView = findViewById(R.id.recycler_view);
        m_recyclerView.setAdapter(m_scrollListAdapter);
        m_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        m_recyclerView.scheduleLayoutAnimation();

        m_refreshLayout = findViewById(R.id.swipeRefreshLayout);
        m_refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    @Override
    public void displayList(ListItem[] items) {
        m_refreshLayout.setRefreshing(false);
        m_recyclerView.scheduleLayoutAnimation();
        m_scrollListAdapter.displayList(items);
    }

    @Override
    public void displayLoadFailedError(String reason) {
        if(reason == null) {
            reason = getResources().getString(R.string.error_message);
        }
        Toast.makeText(getContext(), reason, Toast.LENGTH_LONG).show();
        m_refreshLayout.setRefreshing(false);
    }
}
