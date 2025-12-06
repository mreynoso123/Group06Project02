package com.mareyn.group06project02.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.mareyn.group06project02.database.entities.Group;

public class GroupAdapter extends ListAdapter<Group, ChoreViewHolder> {
  public GroupAdapter(@NonNull DiffUtil.ItemCallback<Group> diffCallback) {
    super(diffCallback);
  }

  // Creates recycler widget in Activity, binds to main activity
  @NonNull
  @Override
  public ChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return ChoreViewHolder.create(parent);
  }

  // binds view holder to position in recycler view
  @Override
  public void onBindViewHolder(@NonNull ChoreViewHolder holder, int position) {
    Group current = getItem(position);
    holder.bind(current.getName());
  }

  public static class GroupDiff extends DiffUtil.ItemCallback<Group> {
    @Override
    public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
      return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
      return oldItem.equals(newItem);
    }
  }
}
