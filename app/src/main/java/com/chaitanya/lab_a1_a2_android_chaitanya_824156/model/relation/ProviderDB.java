package com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.relation;

import androidx.room.Relation;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

import java.util.List;

public class ProviderDB extends Provider {
    @Relation(parentColumn = "provider_id",entity = Product.class,entityColumn = "provider_id")
    private List<Product> products;
}
