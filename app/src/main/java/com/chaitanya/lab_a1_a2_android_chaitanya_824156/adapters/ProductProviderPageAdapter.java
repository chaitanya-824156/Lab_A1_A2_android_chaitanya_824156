package com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments.ProductListFragment;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.fragments.ProviderListFragment;

public class ProductProviderPageAdapter extends FragmentPagerAdapter {
    private ProductListFragment productsFragment = new ProductListFragment();
    private ProviderListFragment providersFragment = new ProviderListFragment();

    public ProductProviderPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return productsFragment;
        } else {
            return providersFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Products";
        } else {
            return "Providers";
        }
    }
}