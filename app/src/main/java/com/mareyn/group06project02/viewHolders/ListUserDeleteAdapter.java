package com.mareyn.group06project02.viewHolders;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.mareyn.group06project02.OnItemClickListener;
import com.mareyn.group06project02.R;
import com.mareyn.group06project02.database.entities.User;

public class ListUserDeleteAdapter extends ListAdapter<User, ListUserDeleteViewHolder> {
  private Context context;
  private OnItemClickListener listener;

  public ListUserDeleteAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback, Context context, OnItemClickListener listener) {
    super(diffCallback);
    this.listener = listener;
    this.context = context;
  }

  // Creates recycler widget in Activity, binds to main activity
  @NonNull
  @Override
  public ListUserDeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return ListUserDeleteViewHolder.create(parent);
  }

  // binds view holder to position in recycler view
  @Override
  public void onBindViewHolder(@NonNull ListUserDeleteViewHolder holder, int position) {
    User current = getItem(position);
    var button = holder.itemView.findViewById(R.id.deleteUserButton);
    button.setOnClickListener(view -> {
      listener.onItemClick(current);
    });
    // holder.itemView.setOnClickListener(v -> listener.onItemClick(current));
    holder.bind(current.getUsername());
  }

  public static class UserDiff extends DiffUtil.ItemCallback<User> {
    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
      return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
      return oldItem.equals(newItem);
    }
  }
}
