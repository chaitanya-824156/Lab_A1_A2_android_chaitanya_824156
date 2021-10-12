package com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.MainActivity;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.R;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.ProductAdapter;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.ProviderAdapter;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.SwipeProductCallback;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters.SwipeProviderCallback;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Product;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderListFragment extends Fragment implements ProviderAdapter.ProviderClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;
    private  ProviderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        recyclerView = view.findViewById(R.id.product_holder);
        view.findViewById(R.id.product_search).setVisibility(View.GONE);
        view.findViewById(R.id.productAddAction).setOnClickListener(this);
        view.findViewById(R.id.providerMap).setOnClickListener(this);
        adapter= new ProviderAdapter(getActivity());
        adapter.setOnProviderClick(this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeProviderCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        appDatabase = DbClient.getInstance(getActivity()).getAppDb();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        List<Provider> providers = new ArrayList<>( appDatabase.providerDao().getAllProvidersDB());
        adapter.setProviders(providers);
    }

    @Override
    public void onProviderItemClick(Provider provider) {
        if(getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).passProviderIdToProductsActivity(provider);
        }
    }

    @Override
    public void onDeleteAction(Provider provider) {
        appDatabase.providerDao().deleteProvider(provider);
    }

    @Override
    public void onEditAction(Provider provider) {
        navigateTOEdit(provider.getProvider_id());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.productAddAction) {
            navigateTOEdit(-1);
        }else{
            if(getActivity() instanceof MainActivity){
                ((MainActivity)getActivity()).showProviderOnMap();
            }
        }
    }

    private void navigateTOEdit(int provider) {
        if(getActivity() instanceof  MainActivity){
            ((MainActivity)getActivity()).startAddProviderScreen(provider);
        }
    }
}