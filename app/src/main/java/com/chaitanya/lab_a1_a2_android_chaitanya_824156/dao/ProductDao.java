package com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.relation.ProductDB;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products where product_name like :search Or product_description like :search")
    List<Product> getAllProducts(String search);

    @Query("SELECT * FROM products WHERE product_id IN (:product_ids)")
    List<Product> getAllByIds(int[] product_ids);

    @Query("SELECT * FROM products WHERE provider_id IN (:provider_ids)")
    List<Product> getAllByProviderIds(int[] provider_ids);

    @Query("SELECT * FROM products WHERE provider_id IN (:provider_ids) and (product_name like :search Or product_description like :search)")
    List<Product> getAllByProviderIds(int[] provider_ids, String search);

    @Query("SELECT * FROM products WHERE product_name LIKE :name AND " + "product_description LIKE :description LIMIT 100")
    Product findByNameAndDescription(String name, String description);

    @Insert
    void insertAllProducts(Product... products);

    @Delete
    void deleteProduct(Product product);

    @Query("select * from products where product_id =:productId")
    ProductDB getProductDetailById(int productId);
}
