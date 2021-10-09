package com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.R;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;

import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder> {
    private List<Provider> providers;
    public ProviderClickListener onProviderClick;

    public ProviderAdapter(List<Provider> providers) {
        this.providers = providers;
    }

    public void setOnProviderClick(ProviderClickListener onProviderClick) {
        this.onProviderClick = onProviderClick;
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_item, parent, false);
        return new ProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {
        Provider provider = providers.get(position);
        String name = provider.getProvider_name();
        String phone = provider.getProvider_phone();
        String email = provider.getProvider_email();
        holder.txtName.setText(name);
        holder.txtPhone.setText(phone);
        holder.txtMail.setText(email);
    }

    @Override
    public int getItemCount() {
        return providers == null ? 0 : providers.size();
    }

    public class ProviderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView txtName, txtPhone, txtMail;

        public ProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.provider_name);
            txtPhone = itemView.findViewById(R.id.phone_number);
            txtMail = itemView.findViewById(R.id.mail_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onProviderClick != null) {
                int providerPos = getAdapterPosition();
                onProviderClick.onProviderItemClick(providers.get(providerPos));
            }
        }
    }

    public interface ProviderClickListener {
        void onProviderItemClick(Provider provider);
    }
}
