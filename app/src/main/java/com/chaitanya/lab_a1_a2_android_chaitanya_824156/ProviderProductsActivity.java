package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments.ProductListFragment;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;

public class ProviderProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_products);
        if (getIntent() != null) {
            ProductListFragment productListFragment = new ProductListFragment();
            Bundle providerBundle = new Bundle();
            providerBundle.putInt("providerId", getIntent().getIntExtra("provider_id", 0));
            productListFragment.setArguments(providerBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.productFragment, productListFragment).commit();
        }
    }

    public void passProductIdToDetailActivity(Product product) {
        Intent productIntent = new Intent(ProviderProductsActivity.this, ProductDetailActivity.class);
        productIntent.putExtra("productId", product.getProduct_id());
        startActivity(productIntent);
    }
}