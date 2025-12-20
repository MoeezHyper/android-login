package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, price;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.productTitle);
            description = view.findViewById(R.id.productDescription);
            price = view.findViewById(R.id.productPrice);
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
        holder.price.setText("Price: $" + p.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
