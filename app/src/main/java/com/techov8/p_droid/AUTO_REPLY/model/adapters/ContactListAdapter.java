package com.techov8.p_droid.AUTO_REPLY.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techov8.p_droid.databinding.ContactListRowBinding;
import com.techov8.p_droid.AUTO_REPLY.model.data.ContactHolder;
import com.techov8.p_droid.AUTO_REPLY.model.preferences.PreferencesManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private final ArrayList<ContactHolder> contactHolderArrayList;
    private Set<String> contactArrayCheckpoint = new HashSet<>();
    private final Context mContext;

    public ContactListAdapter(Context context, ArrayList<ContactHolder> contactHolderArrayList) {
        this.contactHolderArrayList = contactHolderArrayList;
        mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ContactListRowBinding binding = ContactListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ContactListRowBinding binding = holder.getBinding();
        binding.contactCheckbox.setChecked(contactHolderArrayList.get(position).isChecked());
        binding.contactCheckbox.setText(contactHolderArrayList.get(position).getContactName());
        binding.contactCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                contactHolderArrayList.get(position).setChecked(isChecked);
                saveSelectedContactList();
        });
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull ViewHolder holder) {
        ContactListRowBinding binding = holder.getBinding();
        binding.contactCheckbox.setOnCheckedChangeListener(null);
        super.onViewRecycled(holder);
    }

    public void saveSelectedContactList() {
        Set<String> selectedContacts = new HashSet<>();
        for (ContactHolder contact : contactHolderArrayList) {
            if (contact.isChecked()) selectedContacts.add(contact.getContactName());
        }
        PreferencesManager.getPreferencesInstance(mContext).setReplyToNames(selectedContacts);
    }

    public void createCheckpoint() {
        contactArrayCheckpoint = new HashSet<>();
        for (ContactHolder contact : contactHolderArrayList) {
            if (contact.isChecked()) contactArrayCheckpoint.add(contact.getContactName());
        }
    }

    public void restoreCheckpoint() {
        for (ContactHolder contact : contactHolderArrayList) {
            contact.setChecked(contactArrayCheckpoint.contains(contact.getContactName()));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactHolderArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ContactListRowBinding binding;
        public ViewHolder(@NonNull @NotNull ContactListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ContactListRowBinding getBinding() {
            return binding;
        }
    }

}
