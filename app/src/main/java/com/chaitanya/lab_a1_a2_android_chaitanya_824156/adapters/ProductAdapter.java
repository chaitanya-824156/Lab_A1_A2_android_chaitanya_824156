package com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.R;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private ProductClickListener clickListener;

    public ProductAdapter() {
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void setClickListener(ProductClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        String name = product.getProduct_name();
        String description = product.getProduct_description();
        String sp = "Price :" + product.getProduct_price();
        holder.txtName.setText(name);
        holder.txtDescription.setText(description);
        holder.txtSp.setText(sp);
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView txtName, txtDescription, txtSp;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.product_name);
            txtDescription = itemView.findViewById(R.id.product_description);
            txtSp = itemView.findViewById(R.id.product_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                int position = getAdapterPosition();
                clickListener.onProductItemClick(products.get(position));
            }
        }
    }

    public interface ProductClickListener {
        void onProductItemClick(Product product);
    }
}
