package live.gaskell.baco.ItemFast;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;
import com.mikepenz.materialize.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.gaskell.baco.R;

public class ItemFastCategoria extends AbstractItem<ItemFastCategoria, ItemFastCategoria.ViewHolder> {
    public StringHolder Nombre, Id;

    @NonNull
    @Override
    public ItemFastCategoria.ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    //Devuelve el id asignado
    @Override
    public int getType() {
        return R.id.item_categoria_id;
    }

    //Devuelve el layout
    @Override
    public int getLayoutRes() {
        return R.layout.item_carta_categoria;
    }


    public ItemFastCategoria withNombre(String nombre) {
        this.Nombre = new StringHolder(nombre);
        return this;
    }

    public ItemFastCategoria withId(String id) {
        this.Id = new StringHolder(id);
        return this;
    }

    public String getId() {
        return String.valueOf(this.Id);
    }

    public String getNombre() {
        return String.valueOf(this.Nombre);
    }

    protected static class ViewHolder extends FastAdapter.ViewHolder<ItemFastCategoria> {
        protected View view;

        @BindView(R.id.textViewNombre)
        TextView nombre;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }

        @Override
        public void bindView(ItemFastCategoria item, List<Object> payloads) {
            Context ctx = itemView.getContext();
            UIUtils.setBackground(view, FastAdapterUIUtils.getSelectableBackground(ctx, Color.LTGRAY, true));
            StringHolder.applyTo(item.Nombre, nombre);
        }

        @Override
        public void unbindView(ItemFastCategoria item) {
        }
    }
}
