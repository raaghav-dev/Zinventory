package com.example.zinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<Data> list;
    Context context;

    public RecyclerViewAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = list.get(position);
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView

        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());
        holder.quantity.setText(data.getQuantity());

        //animate(holder);

    }

    @Override
    public int getItemCount() {

        //returns the number of elements the RecyclerView will display

        return list.size();
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    // Insert a new item to the RecyclerView on a predefined position
//
//    public void insert(int position, Data data) {
//        list.add(position, data);
//        notifyItemInserted(position);
//    }
//
//    // Remove a RecyclerView item containing a specified Data object
//
//    public void remove(Data data) {
//        int position = list.indexOf(data);
//        list.remove(position);
//        notifyItemRemoved(position);
//    }

}
