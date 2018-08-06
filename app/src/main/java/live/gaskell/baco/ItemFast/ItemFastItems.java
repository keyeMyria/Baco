package live.gaskell.baco.ItemFast;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.util.UIUtils;

import java.util.List;

public class ItemFastItems extends AbstractItem<ItemFastItems, ItemFastItems.ViewHolder> {

    @NonNull
    @Override
    public ItemFastItems.ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    protected static class ViewHolder extends FastAdapter.ViewHolder<ItemFastItems> {
        protected View view;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(ItemFastItems item, List<Object> payloads) {
            Context ctx = itemView.getContext();
            UIUtils.setBackground(view, FastAdapterUIUtils.getSelectableBackground(ctx, Color.LTGRAY, true));

        }

        @Override
        public void unbindView(ItemFastItems item) {

        }
    }
}
