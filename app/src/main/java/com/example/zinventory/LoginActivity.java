package com.example.zinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //TODO update names as per naming conventions
    EditText username, password;
    Button login;
    Animation atg,btgone,btgtwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setupMainActivity();
    }


    private void setupMainActivity(){

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(LoginActivity.this, HomeActivity.class);
                //Log.d("zapp", "button clicked: ");
                startActivity(a);
            }
        });

        atg= AnimationUtils.loadAnimation(this, R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);


    }


    @Override
    protected void onResume() {
        super.onResume();

        username.startAnimation(btgone);
        password.startAnimation(btgone);
        login.startAnimation(btgtwo);

        getData();

    }

    private void getData(){

    }
}
