package com.example.myapplication;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.dao.ProductDao;
import com.example.myapplication.model.Product;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private final ProductDao productDao;
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ProductViewModel(Application application) {
        super(application);
        productDao = new ProductDao(application);
        fetchProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<String> getError() {
        return error;
    }

    private void fetchProducts() {
        new Thread(() -> {
            List<Product> fetchedProducts = ApiClient.fetchProducts();
            if (fetchedProducts != null) {
                productDao.open();
                productDao.deleteAllProducts();
                for (Product product : fetchedProducts) {
                    productDao.addProduct(product);
                }
                productDao.close();
                products.postValue(fetchedProducts);
            } else {
                productDao.open();
                List<Product> offlineProducts = productDao.getAllProducts();
                productDao.close();
                if (offlineProducts.isEmpty()) {
                    error.postValue("No products available offline. Please check your network connection.");
                } else {
                    products.postValue(offlineProducts);
                    error.postValue("Network error. Loading offline data.");
                }
            }
        }).start();
    }
}
