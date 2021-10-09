package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProductDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.relation.ProductDB;

public class ProductDetailActivity extends AppCompatActivity {
    private AppCompatTextView txtProductName, txtProductDesc, txtProductSp,
            txtProviderName, txtPhone, txtMail;
    public AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        txtProductName = findViewById(R.id.product_name);
        txtProductDesc = findViewById(R.id.product_description);
        txtProductSp = findViewById(R.id.productPrice);
        txtProviderName = findViewById(R.id.provider_name);
        txtPhone = findViewById(R.id.phone_number);
        txtMail = findViewById(R.id.mail_id);
        database = DbClient.getInstance(this).getAppDb();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            int productId = intent.getIntExtra("productId", 0);
            getProductById(productId);
        }
    }

    private void getProductById(int productId) {
        ProductDao productDao = database.productDao();
        ProductDB productDB = productDao.getProductDetailById(productId);
        if (productDB != null) {
            txtProductName.setText(productDB.getProduct_name());
            txtProductDesc.setText(productDB.getProduct_description());
            String spValue = productDB.getProduct_price()+"";
            txtProductSp.setText(spValue);
            txtProviderName.setText(productDB.getProductProvider().getProvider_name());
            txtPhone.setText(productDB.getProductProvider().getProvider_phone());
            txtMail.setText(productDB.getProductProvider().getProvider_email());
        }
    }
}