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

    private Context m_context;
    private ScrollListAdapter m_ScrollListAdapter;
    SwipeRefreshLayout m_refreshLayout;
    public ScrollList(Context context) {
        super(context);
        m_context = context;
    }

    public ScrollList(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_context = context;
    }

    public ScrollList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        m_context = context;
    }

    @Override
    public void setPresenter(final ListPresenter presenter) {
        m_ScrollListAdapter = new ScrollListAdapter(
            (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
            presenter
        );

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(m_ScrollListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(m_context, DividerItemDecoration.VERTICAL));

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
        m_ScrollListAdapter.displayList(items);
        m_refreshLayout.setRefreshing(false);
    }

    @Override
    public void displayLoadFailedError() {
        String message = getResources().getString(R.string.error_message);
        Toast.makeText(m_context, message, Toast.LENGTH_LONG).show();
        m_refreshLayout.setRefreshing(false);
    }
}
