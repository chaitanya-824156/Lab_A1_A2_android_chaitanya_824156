package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.ProductProviderPageAdapter;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProductDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProviderDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ProductProviderPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ProductProviderPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager pager = findViewById(R.id.mainTabHolder);
        TabLayout tabLayout = findViewById(R.id.mainTab);
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppDatabase appDatabase = DbClient.getInstance(this).getAppDb();
        ProviderDao providerDao = appDatabase.providerDao();
        ProductDao productDao = appDatabase.productDao();
        List<Provider> providers = providerDao.getAllProviders();
        if (providers.size() == 0) {
            Provider provider = new Provider("Chaitanya's Store", "chai@gmail.com", "9742003744", 43.13, -77.54);
            providerDao.insertProvider(provider);
            int providerId = providerDao.getDummyProviderId();
            String[] productNames = new String[]{"Toothpaste", "Soap", "IceCream", "Chocos", "Tata Power"};
            Product[] productsList = new Product[productNames.length];
            for (int i = 0; i < productNames.length; i++) {
                Product product = new Product(productNames[i], productNames[i] + " For Sale", (i + 1) * 34, providerId);
                productsList[i] = product;
            }
            productDao.insertAllProducts(productsList);
        }

    }

    public void passProductIdToDetailActivity(Product product) {
        Intent productIntent = new Intent(MainActivity.this, ProductDetailActivity.class);
        productIntent.putExtra("productId", product.getProduct_id());
        startActivity(productIntent);
    }
    public void passProviderIdToProductsActivity(Provider provider) {
        Intent productIntent = new Intent(MainActivity.this, ProviderProductsActivity.class);
        productIntent.putExtra("provider_id", provider.getProvider_id());
        startActivity(productIntent);
    }

    public void startAddProductScreen(int productId) {
        Intent productIntent = new Intent(MainActivity.this, ProductAddUpdateActivity.class);
        productIntent.putExtra("productId",productId);
        startActivity(productIntent);
    }
    public void startAddProviderScreen(int providerId) {
        Intent providerIntent = new Intent(MainActivity.this, ProviderAddUpdateActivity.class);
        providerIntent.putExtra("providerId",providerId);
        startActivity(providerIntent);
    }

    public void showProviderOnMap() {
        Intent providerMapIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(providerMapIntent);
    }
}