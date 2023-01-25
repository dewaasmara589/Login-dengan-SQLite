package com.dewa.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    TextInputLayout textInputNik, textInputPassword;
    Button btnLogin, btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        textInputNik = (TextInputLayout) findViewById(R.id.txtInputNik);
        textInputPassword = (TextInputLayout) findViewById(R.id.txtInputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ambilNik = textInputNik.getEditText().getText().toString();
                String ambilPassword = textInputPassword.getEditText().getText().toString();
                Boolean masuk = db.checkLogin(ambilNik, ambilPassword);

                if (masuk == true){
                    Boolean updateSession = db.upgradeSession("ada", 1);

                    if (updateSession == true){
                        Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();

                        Intent intentHome = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intentHome);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDaftar = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentDaftar);
            }
        });
    }
}