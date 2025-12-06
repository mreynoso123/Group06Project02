package com.mareyn.group06project02.viewHolders;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.mareyn.group06project02.OnItemClickListener;
import com.mareyn.group06project02.database.entities.User;

public class UserAdapter extends ListAdapter<User, UserViewHolder> {
  private Context context;
  private OnItemClickListener listener;

  public UserAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback, Context context, OnItemClickListener listener) {
    super(diffCallback);
    this.listener = listener;
    this.context = context;
  }

  // Creates recycler widget in Activity, binds to main activity
  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return UserViewHolder.create(parent);
  }

  // binds view holder to position in recycler view
  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    User current = getItem(position);
    holder.itemView.setOnClickListener(v -> listener.onItemClick(current));
    // holder.itemView.setOnClickListener(v -> {
    //   Intent intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(context.getApplicationContext(), user.getUsername(), user.getUserId());
    //   startActivity(intent);
    // });
    // groupViewItem.setOnClickListener(view -> {
    //   var user = new User(1, "child1", "password", "", false);
    //   Intent intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(this., user.getUsername(), user.getUserId());
    //   startActivity(intent);
    // });
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
