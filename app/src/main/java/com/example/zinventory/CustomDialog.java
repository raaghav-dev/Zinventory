package com.example.zinventory;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialog extends Dialog {

    private String itemArray[];
    private ArrayList<Item> itemsList;
    private ArrayAdapter<String> itemArrayAdapter;
    AutoCompleteTextView item_name;
    Activity CustomDialog;
    EditText store_name, quantity;


    public CustomDialog(Activity a) {
        super(a);
        this.CustomDialog = a;
        CustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        quantity = findViewById(R.id.quantity);
        store_name = findViewById(R.id.store_name);
        item_name = findViewById(R.id.item_name);


    }

}





