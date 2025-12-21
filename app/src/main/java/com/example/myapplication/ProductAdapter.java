package com.example.myapplication;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Product;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView title, description, price;
        ImageButton optionsButton;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.productTitle);
            description = view.findViewById(R.id.productDescription);
            price = view.findViewById(R.id.productPrice);
            optionsButton = view.findViewById(R.id.optionsButton);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(products.get(position));
                }
            });
            view.setOnCreateContextMenuListener(this);

            optionsButton.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.action_add_to_cart) {
                        Toast.makeText(v.getContext(), "Add to cart is not yet implemented", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (itemId == R.id.action_share) {
                        Toast.makeText(v.getContext(), "Share is not yet implemented", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
                popup.show();
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = item -> {
            switch (item.getItemId()) {
                case 1:
                    Toast.makeText(itemView.getContext(), "Edit is not yet implemented", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(itemView.getContext(), "Delete is not yet implemented", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = products.get(position);
        holder.title.setText(p.getTitle());
        holder.description.setText(p.getDescription());
        holder.price.setText(String.format(Locale.getDefault(), "$%.2f", p.getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
