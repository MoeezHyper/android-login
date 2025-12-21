package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public ProductDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, product.getId());
        values.put(DatabaseHelper.COLUMN_TITLE, product.getTitle());
        values.put(DatabaseHelper.COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, product.getDescription());
        values.put(DatabaseHelper.COLUMN_CATEGORY, product.getCategory());
        values.put(DatabaseHelper.COLUMN_IMAGE, product.getImage());
        values.put(DatabaseHelper.COLUMN_RATE, product.getRate());
        values.put(DatabaseHelper.COLUMN_COUNT, product.getCount());
        db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = cursorToProduct(cursor);
                products.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return products;
    }

    public void deleteAllProducts() {
        db.delete(DatabaseHelper.TABLE_PRODUCTS, null, null);
    }

    private Product cursorToProduct(Cursor cursor) {
        Product product = new Product();
        product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
        product.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
        product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)));
        product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
        product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY)));
        product.setImage(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE)));
        product.setRate(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RATE)));
        product.setCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COUNT)));
        return product;
    }
}