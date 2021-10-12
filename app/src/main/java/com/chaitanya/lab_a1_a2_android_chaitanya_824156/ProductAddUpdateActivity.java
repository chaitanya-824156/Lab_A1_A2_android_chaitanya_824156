package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProductDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProviderDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

import java.util.List;

public class ProductAddUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName, etDescription, etPrice;
    private Spinner spinner;
    private ProductDao productDao;
    private ProviderDao providerDao;
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_update);
        etName = findViewById(R.id.productName);
        etDescription = findViewById(R.id.productDescription);
        etPrice = findViewById(R.id.productPrice);
        spinner = findViewById(R.id.providerSpin);

        AppDatabase database = DbClient.getInstance(this).getAppDb();
        productDao = database.productDao();
        providerDao = database.providerDao();
        Button addUpdate = findViewById(R.id.addProduct);
        addUpdate.setOnClickListener(this);
        if (getIntent() != null) {
            productId = getIntent().getIntExtra("productId", -1);
        }
        if (productId > 0) {
            addUpdate.setText("Update Product");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Provider> providerList = providerDao.getAllProviders();
        ArrayAdapter<Provider> providerArrayAdapter = new ArrayAdapter<Provider>(this, android.R.layout.simple_list_item_1, providerList);
        spinner.setAdapter(providerArrayAdapter);
        if(productId>0){
            Product product = productDao.getProductDetailById(productId);
            etName.setText(product.getProduct_name());
            etDescription.setText(product.getProduct_description());
            etPrice.setText(product.getProduct_price()+"");
            spinner.setSelection(providerList.indexOf(product.getProductProvider()));
        }
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        Provider provider = (Provider) spinner.getSelectedItem();
        int productPrice = 0;
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Enter Price", Toast.LENGTH_SHORT).show();
        } else if (provider == null) {
            Toast.makeText(this, "Select Provider", Toast.LENGTH_SHORT).show();
        } else {
            productPrice = Integer.parseInt(price);
            Product product = new Product(name, description, productPrice, provider.getProvider_id());
            if (productId > 0) {
                product.setProduct_id(productId);
            }
            productDao.insertAllProducts(new Product[]{product});
            Toast.makeText(this, "Product Update Successful ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}