package com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.MainActivity;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.ProviderProductsActivity;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.R;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.ProductAdapter;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.SwipeProductCallback;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProductDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment implements ProductAdapter.ProductClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private int providerId = -1;
    private ProductDao productDao;
    private ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        recyclerView = view.findViewById(R.id.product_holder);
        AppCompatEditText searchEdit = view.findViewById(R.id.search);
        view.findViewById(R.id.productAddAction).setOnClickListener(this);
        view.findViewById(R.id.providerMap).setVisibility(View.GONE);
        adapter = new ProductAdapter(getActivity());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeProductCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString().trim();
                findProducts(search);
            }
        });
        AppDatabase appDatabase = DbClient.getInstance(getActivity()).getAppDb();
        productDao = appDatabase.productDao();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            providerId = getArguments().getInt("providerId");
        }
        findProducts("");
    }

    public void findProducts(String search) {
        String searchLike = "%" + search + "%";
        List<Product> products = new ArrayList<>();
        if (providerId <= 0) {
            products = productDao.getAllProducts(searchLike);
        } else {
            products = productDao.getAllByProviderIds(new int[]{providerId}, searchLike);
        }
        adapter.setProducts(products);
    }

    @Override
    public void onProductItemClick(Product product) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).passProductIdToDetailActivity(product);
        } else if (getActivity() instanceof ProviderProductsActivity) {
            ((ProviderProductsActivity) getActivity()).passProductIdToDetailActivity(product);
        }
    }

    @Override
    public void onDeleteAction(Product product) {
        productDao.deleteProduct(product);
    }

    @Override
    public void onEditProductAction(Product product) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).startAddProductScreen(product.getProduct_id());
        } else if (getActivity() instanceof ProviderProductsActivity) {
            ((ProviderProductsActivity) getActivity()).startAddProductScreen(product.getProduct_id());
        }
    }

    @Override
    public void onClick(View v) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).startAddProductScreen(-1);
        } else if (getActivity() instanceof ProviderProductsActivity) {
            ((ProviderProductsActivity) getActivity()).startAddProductScreen(-1);
        }
    }
}