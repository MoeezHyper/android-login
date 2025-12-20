package com.example.myapplication;

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

                    Product product = new Product(
                            obj.getInt("id"),
                            obj.getString("title"),
                            obj.getString("description"),
                            obj.getInt("price")
                    );

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
