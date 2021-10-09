package com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.MainActivity;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.R;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.ProductAdapter;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.ProviderAdapter;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

import java.util.List;

public class ProviderListFragment extends Fragment implements ProviderAdapter.ProviderClickListener {

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        recyclerView = view.findViewById(R.id.product_holder);
        view.findViewById(R.id.product_search).setVisibility(View.GONE);
        appDatabase = DbClient.getInstance(getActivity()).getAppDb();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        List<Provider> providers = appDatabase.providerDao().getAllProviders();
        ProviderAdapter adapter = new ProviderAdapter(providers);
        adapter.setOnProviderClick(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onProviderItemClick(Provider provider) {
        if(getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).passProviderIdToProductsActivity(provider);
        }
    }
}