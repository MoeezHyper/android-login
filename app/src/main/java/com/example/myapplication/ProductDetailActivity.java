package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Product;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product product;
        if (Build.VERSION.SDK_INT >= 33) {
            product = getIntent().getParcelableExtra("product", Product.class);
        } else {
            product = getIntent().getParcelableExtra("product");
        }

        ImageView productImage = findViewById(R.id.productImage);
        TextView productTitle = findViewById(R.id.productTitle);
        TextView productCategory = findViewById(R.id.productCategory);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productRating = findViewById(R.id.productRating);

        if (product != null) {
            Glide.with(this).load(product.getImage()).into(productImage);
            productTitle.setText(product.getTitle());
            productCategory.setText(product.getCategory());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            productRating.setText(String.format(Locale.getDefault(), "Rating: %.2f (%d)", product.getRate(), product.getCount()));
        }
    }
}