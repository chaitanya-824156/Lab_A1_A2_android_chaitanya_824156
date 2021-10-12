package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments.ProductListFragment;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

import java.util.Objects;

public class ProviderProductsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_products);
        AppCompatTextView txtCall = findViewById(R.id.phoneCall);
        AppCompatTextView txtMail = findViewById(R.id.mail_id);
        txtCall.setOnClickListener(this);
        txtMail.setOnClickListener(this);
        if (getIntent() != null) {
            int providerId = getIntent().getIntExtra("provider_id", 0);
            ProductListFragment productListFragment = new ProductListFragment();
            Bundle providerBundle = new Bundle();
            providerBundle.putInt("providerId", providerId);
            productListFragment.setArguments(providerBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.productFragment, productListFragment).commit();
            Provider provider = DbClient.getInstance(this).getAppDb().providerDao().getProviderByIds(providerId);
            if (provider != null) {
                txtCall.setText(provider.getProvider_phone());
                txtMail.setText(provider.getProvider_email());
                Objects.requireNonNull(getSupportActionBar()).setTitle(provider.getProvider_name());
            }

        }
    }


    public void passProductIdToDetailActivity(Product product) {
        Intent productIntent = new Intent(ProviderProductsActivity.this, ProductDetailActivity.class);
        productIntent.putExtra("productId", product.getProduct_id());
        startActivity(productIntent);
    }

    @Override
    public void onClick(View v) {
        AppCompatTextView txt = (AppCompatTextView) v;
        if (v.getId() == R.id.phoneCall) {
            Intent callAction = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txt.getText().toString().trim()));
            startActivity(callAction);
        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, txt.getText().toString().trim());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public void startAddProductScreen(int productId) {
        Intent productIntent = new Intent(ProviderProductsActivity.this, ProductAddUpdateActivity.class);
        productIntent.putExtra("productId", productId);
        startActivity(productIntent);
    }
}