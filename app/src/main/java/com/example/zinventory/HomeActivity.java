package com.example.zinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {

    Button btnadd,btndetails;
    ImageView ivsplash;
    Animation atg,btgone,btgtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btndetails = findViewById(R.id.btndetails);
        ivsplash = findViewById(R.id.ivsplash);
        btnadd = findViewById(R.id.btnadd);
        atg= AnimationUtils.loadAnimation(this, R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);
        ivsplash.startAnimation(atg);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent b = new Intent(HomeActivity.this, AddItemActivity.class);
                startActivity(b);

                /*CustomDialog cd = new CustomDialog(HomeActivity.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
                */


            }
        });

        btndetails.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent c = new Intent(HomeActivity.this,GetStockDetailsActivity.class);
                startActivity(c);
            }
        });


    }

}
