package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, price;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.productTitle);
            description = view.findViewById(R.id.productDescription);
            price = view.findViewById(R.id.productPrice);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(products.get(position));
                }
            });
        }
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
