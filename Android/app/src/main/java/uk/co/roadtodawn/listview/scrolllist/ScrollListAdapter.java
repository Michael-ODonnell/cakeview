package uk.co.roadtodawn.listview.scrolllist;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.roadtodawn.listview.ListItem;
import uk.co.roadtodawn.listview.ListPresenter;
import uk.co.roadtodawn.listview.R;

class ScrollListAdapter extends ListAdapter<ListItem, ListItemViewHolder> {

    private static final DiffUtil.ItemCallback<ListItem> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<ListItem>() {
            @Override
            public boolean areItemsTheSame(
                    @NonNull ListItem item1, @NonNull ListItem item2) {
                return item1.equals(item2);
            }
            @Override
            public boolean areContentsTheSame(
                    @NonNull ListItem item1, @NonNull ListItem item2) {
                return item1.equals(item2);
            }
        };

    private LayoutInflater m_inflater;
    private ListItem[] m_items;
    private ListPresenter m_listPresenter;
    private int m_selected;

    ScrollListAdapter(LayoutInflater inflater,
                      ListPresenter presenter
    ) {
        super(DIFF_CALLBACK);
        m_items = new ListItem[0];
        m_inflater = inflater;
        m_listPresenter = presenter;
        m_selected = -1;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = m_inflater.inflate(R.layout.list_item, viewGroup, false);

        final ListItemViewHolder viewHolder = new ListItemViewHolder(view, m_listPresenter);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_listPresenter.selectItem(viewHolder.getItem());
                notifyItemChanged(m_selected);
                m_selected = viewHolder.getLayoutPosition();
                notifyItemChanged(m_selected);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
        listItemViewHolder.bind(m_items[i], m_selected == i);
    }

    @Override
    public int getItemCount() {
        return m_items.length;
    }

    void displayList(ListItem[] items) {
        m_selected = -1;
        m_items = items;
        notifyDataSetChanged();
    }
}
