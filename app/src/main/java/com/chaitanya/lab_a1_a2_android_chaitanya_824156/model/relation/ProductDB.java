package com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.relation;

import androidx.room.Relation;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

public class ProductDB extends Product {
    @Relation(parentColumn = "provider_id",entity = Provider.class,entityColumn = "provider_id")
    private Provider productProvider;
}
