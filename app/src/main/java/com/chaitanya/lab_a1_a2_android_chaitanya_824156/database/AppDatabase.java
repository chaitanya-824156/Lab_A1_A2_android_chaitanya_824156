package com.chaitanya.lab_a1_a2_android_chaitanya_824156.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProductDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProviderDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

@Database(entities = {Provider.class, Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    public abstract ProviderDao providerDao();
}
