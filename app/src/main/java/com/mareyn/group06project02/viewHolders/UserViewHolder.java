package com.mareyn.group06project02.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
  private final TextView groupViewItem;

  private UserViewHolder(View choreView) {
    super(choreView);
    groupViewItem = choreView.findViewById(R.id.recyclerItemUserChildTextView);
  }

  public void bind(String text) {
    groupViewItem.setText(text);
  }

  static UserViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.user_recycler_item, parent, false);
    return new UserViewHolder(view);
  }
}
