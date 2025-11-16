package com.mareyn.group06project02.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.R;

public class ChoreViewHolder extends RecyclerView.ViewHolder {
  private final TextView choreViewItem;

  private ChoreViewHolder(View choreView) {
    super(choreView);
    choreViewItem = choreView.findViewById(R.id.recyclerItemTextView);
  }

  public void bind(String text) {
    choreViewItem.setText(text);
  }

  static ChoreViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.chore_recycler_item, parent, false);
    return new ChoreViewHolder(view);
  }
}
