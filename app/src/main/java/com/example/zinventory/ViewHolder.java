package com.example.zinventory;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



public class ViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;
    TextView description;
    TextView quantity;


    ViewHolder(View itemView) {
        super(itemView);
        cv = itemView.findViewById(R.id.cardView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        quantity = itemView.findViewById(R.id.quantity);


    }

}
