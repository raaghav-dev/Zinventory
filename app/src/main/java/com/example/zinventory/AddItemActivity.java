package com.example.zinventory;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    TextView noitems;
    public FloatingActionButton fab1, fab2, fab3, fab;
    public boolean isFabOpen = false;
    String name, stname, qty;
    RecyclerView recyclerView;
    static RecyclerViewAdapter adapter;
    List<Data> dataList;
    private String itemArray[];
    private ArrayList<Item> itemsList;
    private ArrayAdapter<String> itemArrayAdapter;
    ImageView uploadImage;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        fab2 = findViewById(R.id.fab2);

//      -----------------------------------Upload Image---------------------------------------------

        uploadImage = findViewById(R.id.uploadImage);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choose();
            }
        });

//        ------------------------------------------------------------------------------------------



        dataList = new ArrayList<>();
        itemsList = new ArrayList<>();

        getSupportActionBar().setTitle(getResources().getString(R.string.itemTitle));
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddItemActivity.this, "Stay tuned for upcoming icons", Toast.LENGTH_SHORT).show();
            }
        });

        noitems = findViewById(R.id.noitems);
        noitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("zapp", "button clicked: ");
                if (isFabOpen) {
                    closeFABMenu();
                    isFabOpen = false;
                }
            }
        });


        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab3 = findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen) {
                    closeFABMenu();
                    isFabOpen = false;
                } else {
                    showFABMenu();
                    isFabOpen = true;
                }
            }

        });

        getAllItems();

        itemArray = new String[itemsList.size()];
        for (int i = 0; i < itemsList.size(); i++)
            itemArray[i] = itemsList.get(i).getName();

        itemArrayAdapter = new ArrayAdapter<>(this, R.layout.layout_item_name, R.id.txtItemName, itemArray);


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(AddItemActivity.this);
                final View CustomDialog = inflater.inflate(R.layout.custom_dialog, null);

                final AutoCompleteTextView ItemName = CustomDialog.findViewById(R.id.item_name);
                final EditText StoreName = CustomDialog.findViewById(R.id.store_name);
                final EditText Quantity = CustomDialog.findViewById(R.id.quantity);

                AlertDialog dialog = new AlertDialog.Builder(AddItemActivity.this)
                        .setView(CustomDialog)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int Submit) {
                                name = ItemName.getText().toString();
                                stname = StoreName.getText().toString();
                                qty = Quantity.getText().toString();



                                ItemName.setAdapter(itemArrayAdapter);
                                ItemName.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        Rect r = new Rect();
                                        ItemName.getWindowVisibleDisplayFrame(r);
                                        int screenHeight = ItemName.getRootView().getHeight();
                                        int keypadHeight = screenHeight - r.bottom;
                                        if (keypadHeight > screenHeight * 0.15) {
                                            if (itemsList.size() > 0) {
                                                ItemName.dismissDropDown();
                                            }
                                        }
                                    }
                                });

                                dataList.add(new Data(name, stname, qty));
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.getWindow();
                dialog.show();

                noitems.setVisibility(View.GONE);

            }
        });


        recyclerView = findViewById(R.id.RecyclerView);
        adapter = new RecyclerViewAdapter(dataList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), dataList.get(position).getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {

//                dataList.remove(position);
//                adapter.notifyDataSetChanged();

//                --------------Delete/Cancel------------------


                AlertDialog dialog = new AlertDialog.Builder(AddItemActivity.this).setMessage("Delete Selected Item").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int delete) {
                        Toast.makeText(getApplicationContext(), dataList.get(position).getTitle() + " was deleted", Toast.LENGTH_SHORT).show();

                        dataList.remove(position);
                        adapter.notifyDataSetChanged();

                    }
                }).setNegativeButton("Cancel",null).create();
                dialog.getWindow();
                dialog.show();

//                ---------------------------------------------------

            }
        }));


    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(AddItemActivity.this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            saveToPreferences(AddItemActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }
        }
    }

    private void Choose(){
        final CharSequence[] options_array = {"Camera","Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
        builder.setItems(options_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(options_array[item].equals("Camera")){
                    if(ContextCompat.checkSelfPermission(AddItemActivity.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        if(getFromPref(AddItemActivity.this, ALLOW_KEY)){
                            showSettingsAlert();
                        } else if(ContextCompat.checkSelfPermission(AddItemActivity.this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                            if(ActivityCompat.shouldShowRequestPermissionRationale(AddItemActivity.this,Manifest.permission.CAMERA)){
                                showAlert();
                            } else {
                                ActivityCompat.requestPermissions(AddItemActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        }
                    }else{
                        openCamera();
                    }
                }

                else if(options_array[item].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
                }
            }
        });
        builder.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(AddItemActivity.this);

                    }
                });
        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private static Boolean getFromPref(Context context,String key){
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed){
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File f = new File(android.os.Environment.getExternalStorageDirectory(), "Invoice.jpg");
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, 1);
    }

    private void showFABMenu() {

        fab1.animate().translationY(-180).translationX(-200);
        fab2.animate().translationY(-260).translationX(0);
        fab3.animate().translationY(-180).translationX(200);
    }

    private void closeFABMenu() {

        fab1.animate().translationY(0).translationX(0);
        fab2.animate().translationY(0).translationX(0);
        fab3.animate().translationY(0).translationX(0);
    }

    private void getAllItems() {

        ItemsAPI itemsAPI = ServiceGenerator.createService(ItemsAPI.class);
        itemsAPI.getAllItems().enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                if (response.isSuccessful()) {
                    itemsList.clear();
                    itemsList.addAll(response.body().getItemList());
                    Log.d("zapp", "API Called Successfully");
                } else Log.d("zapp", "API Called unSuccessfully");

            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.d("zapp", "error: " +t);
            }


        });
    }

    private void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(AddItemActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

}

