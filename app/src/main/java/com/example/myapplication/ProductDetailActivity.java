package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Product;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        if (savedInstanceState != null) {
            if (Build.VERSION.SDK_INT >= 33) {
                product = savedInstanceState.getParcelable("product", Product.class);
            } else {
                product = savedInstanceState.getParcelable("product");
            }
        } else {
            if (Build.VERSION.SDK_INT >= 33) {
                product = getIntent().getParcelableExtra("product", Product.class);
            } else {
                product = getIntent().getParcelableExtra("product");
            }
        }

        ImageView productImage = findViewById(R.id.productImage);
        TextView productTitle = findViewById(R.id.productTitle);
        TextView productCategory = findViewById(R.id.productCategory);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productRating = findViewById(R.id.productRating);
        Button viewOnWebsiteButton = findViewById(R.id.viewOnWebsiteButton);

        if (product != null) {
            Glide.with(this).load(product.getImage()).into(productImage);
            productTitle.setText(product.getTitle());
            productCategory.setText(product.getCategory());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            productRating.setText(String.format(Locale.getDefault(), "Rating: %.2f (%d)", product.getRate(), product.getCount()));

            viewOnWebsiteButton.setOnClickListener(v -> {
                String url = "https://dummyjson.com/products/" + product.getId();
                Toast.makeText(this, "Loading URL: " + url, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProductDetailActivity.this, WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("product", product);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change_theme) {
            Toast.makeText(this, "Theme change is not yet implemented", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_logout) {
            Toast.makeText(this, "Logout is not yet implemented", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}