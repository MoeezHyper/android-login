package com.example.myapplication;

import com.example.myapplication.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String API_URL =
            "https://dummyjson.com/products";

    public static List<Product> fetchProducts() {

        List<Product> products = new ArrayList<>();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                reader.close();

                JSONObject root = new JSONObject(json.toString());
                JSONArray array = root.getJSONArray("products");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    Product product = new Product();
                    product.setId(obj.getInt("id"));
                    product.setTitle(obj.getString("title"));
                    product.setPrice(obj.getDouble("price"));
                    product.setDescription(obj.getString("description"));
                    product.setCategory(obj.getString("category"));
                    product.setImage(obj.getString("thumbnail"));
                    product.setRate(obj.getDouble("rating"));
                    product.setCount(obj.getInt("stock"));

                    products.add(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Network error
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return products;
    }
}
