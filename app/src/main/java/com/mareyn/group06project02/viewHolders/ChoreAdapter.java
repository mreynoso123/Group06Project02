package com.mareyn.group06project02.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.mareyn.group06project02.database.entities.Chore;

public class ChoreAdapter extends ListAdapter<Chore, ChoreViewHolder> {
  public ChoreAdapter(@NonNull DiffUtil.ItemCallback<Chore> diffCallback) {
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
    Chore current = getItem(position);
    holder.bind(current.toString());
  }

  static class ChoreDiff extends DiffUtil.ItemCallback<Chore> {
    @Override
    public boolean areItemsTheSame(@NonNull Chore oldItem, @NonNull Chore newItem) {
      return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Chore oldItem, @NonNull Chore newItem) {
      return oldItem.equals(newItem);
    }
  }
}
