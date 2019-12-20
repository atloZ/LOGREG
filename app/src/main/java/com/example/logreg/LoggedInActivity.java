package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    private TextView tvTeljesnev;
    private Button btKijelentkezes;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        init();

        tvTeljesnev.setText(sharedPreferences.getString("TeljesNev", ""));

        btKijelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent defa =
                        new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(defa);
                finish();
            }
        });
    }

    private void init(){
        tvTeljesnev = findViewById(R.id.tv);
        btKijelentkezes = findViewById(R.id.btnKijelentkezes);
        sharedPreferences = getSharedPreferences("Adatok", Context.MODE_PRIVATE);
    }
}
