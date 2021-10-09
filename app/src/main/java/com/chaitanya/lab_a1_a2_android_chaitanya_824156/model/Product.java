package com.chaitanya.lab_a1_a2_android_chaitanya_824156.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "products", foreignKeys = {
        @ForeignKey(entity = Provider.class,
                parentColumns = "provider_id", childColumns = "provider_id", onDelete = ForeignKey.CASCADE)
})
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int product_id;

    @ColumnInfo(name = "product_name")
    public String product_name;

    @ColumnInfo(name = "product_description")
    public String product_description;

    @ColumnInfo(name = "product_price")
    public int product_price;

    @NonNull
    @ColumnInfo(name = "provider_id")
    public int provider_id;
    @Ignore
    private Provider productProvider;

    public Product() {
    }

    public Product( String product_name, String product_description, int product_price, int provider_id) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.provider_id = provider_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public Provider getProductProvider() {
        return productProvider;
    }

    public void setProductProvider(Provider productProvider) {
        this.productProvider = productProvider;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}