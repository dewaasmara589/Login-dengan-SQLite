package com.dewa.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    DBHelper db;
    TextInputLayout textInputNik, textInputPassword, textInputPasswordConfirmation;
    Button btnLogin, btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);

        textInputNik = (TextInputLayout) findViewById(R.id.txtInputNik);
        textInputPassword = (TextInputLayout) findViewById(R.id.txtInputPassword);
        textInputPasswordConfirmation = (TextInputLayout) findViewById(R.id.txtInputPasswordConfirmation);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ambilNik = textInputNik.getEditText().getText().toString();
                String ambilPassword = textInputPassword.getEditText().getText().toString();
                String ambilPasswordConfirmation = textInputPasswordConfirmation.getEditText().getText().toString();

                if (ambilPassword.equals(ambilPasswordConfirmation)) {
                    Boolean daftar = db.insertUser(ambilNik, ambilPassword);

                    if (daftar == true) {
                        Toast.makeText(getApplicationContext(), "Daftar Berhasil", Toast.LENGTH_SHORT).show();

                        Intent intentLogin = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intentLogin);
                    }else {
                        Toast.makeText(getApplicationContext(), "Daftar Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
    }
}