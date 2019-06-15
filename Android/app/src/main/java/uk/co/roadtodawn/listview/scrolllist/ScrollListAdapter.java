package uk.co.roadtodawn.listview.scrolllist;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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

    ScrollListAdapter(LayoutInflater inflater,
                      ListPresenter presenter
    ) {
        super(DIFF_CALLBACK);
        m_items = new ListItem[0];
        m_inflater = inflater;
        m_listPresenter = presenter;
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
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
        listItemViewHolder.bind(m_items[i]);

        Animation animation = AnimationUtils.loadAnimation(m_inflater.getContext(), android.R.anim.slide_in_left);
        listItemViewHolder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return m_items.length;
    }

    void displayList(ListItem[] items) {
        m_items = items;
        notifyDataSetChanged();
    }
}
