package com.dewa.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class HomeActivity extends AppCompatActivity {
    DBHelper db;
    Button btnLogout;
    TextView tvReadJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DBHelper(this);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        tvReadJSON = (TextView) findViewById(R.id.tvReadJSON);

        loadGrades();

        Boolean checkSession = db.checkSession("ada");
        if (checkSession == false) {
            Intent loginIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updtSession = db.upgradeSession("kosong", 1);
                if (updtSession == true){
                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

    public void loadGrades(){
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.get_food);

        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }

        parseJson(builder.toString());
    }

    private void parseJson(String json) {
        StringBuilder builder = new StringBuilder();

        try {
            JSONObject root = new JSONObject(json);

            String noError = root.getString("err_no");

            if (noError.equals("0")){
                Toast.makeText(getApplicationContext(),  "Success Load JSON", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),  "Error : " + noError + "\n" + root.getString("err_msg"), Toast.LENGTH_SHORT).show();
            }

            // Array atau [ pertama dalam JSON
            JSONArray categories = root.getJSONArray("CATEGORY");

            for (int i = 0; i < categories.length(); i++){
                JSONObject category = categories.getJSONObject(i);
                builder.append("Category : ").append(category.getString("CATEGORY_NAME")).append("\n");

                // Array atau [ kedua dalam JSON
                JSONArray subCategories = category.getJSONArray("SUB_CATEGORY");

                builder.append("Sub Category :\n");
                for (int x = 0; x < subCategories.length(); x++){
                    JSONObject subCategory = subCategories.getJSONObject(x);

                    int temp = x + 1;
                    builder.append("\n" + temp + ". ").append(subCategory.getString("CATEGORY_NAME")).append("\n");

                    // Array atau [ ketiga dalam JSON
                    JSONArray itemSubCategories = subCategory.getJSONArray("ITEM");

                    for (int y = 0; y < itemSubCategories.length(); y++) {
                        JSONObject itemSubCategory = itemSubCategories.getJSONObject(y);

                        int temp2 = y + 1;
                        builder.append(temp + "." + temp2 + ". ").append(itemSubCategory.getString("descp")).append(" = ");
                        builder.append("Rp ").append(itemSubCategory.getString("price")).append("\n");
                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        // read all API
        // tvReadJSON.setText(json);
        tvReadJSON.setText(builder.toString());
    }
}