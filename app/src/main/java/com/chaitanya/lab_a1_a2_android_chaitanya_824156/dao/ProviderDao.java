package com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.relation.ProviderDB;

import java.util.List;

@Dao
public interface ProviderDao {
    @Query("SELECT * FROM providers")
    List<Provider> getAllProviders();

    @Query("SELECT * FROM providers")
    List<ProviderDB> getAllProvidersDB();

    @Query("SELECT * FROM providers WHERE provider_id =:provider_ids")
    Provider getProviderByIds(int provider_ids);

    @Query("SELECT * FROM providers WHERE provider_id IN (:provider_ids)")
    List<Provider> getAllByProviderIds(int[] provider_ids);

    @Query("SELECT * FROM providers WHERE provider_name LIKE :name AND " + "provider_email LIKE :email LIMIT 100")
    Provider findByNameAndEmail(String name, String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProvider(Provider providers);

    @Delete
    void deleteProvider(Provider provider);

    @Query("select provider_id from providers limit 1")
    int getDummyProviderId();

    @Update
    void updateProvider(Provider newProvider);
}
