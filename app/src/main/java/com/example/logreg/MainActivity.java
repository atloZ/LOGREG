package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etFelhasznaloNev, etJelszo;
    private Button btBelep, btRegisztral;

    private ABH abh = new ABH(this);

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btRegisztral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regAct =
                        new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(regAct);
                finish();
            }
        });

        btBelep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etFelhasznaloNev.getText().toString().matches("") ||
                    etJelszo.getText().toString().matches("")) {

                    Toast.makeText(
                            MainActivity.this,
                            "Ne hagyj üresen mezőket!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Cursor eredmeny = abh.userOk(
                            etFelhasznaloNev.getText().toString(),
                            abh.passwordHash(etJelszo.getText().toString()));

                    if (eredmeny.getCount() > 0)
                    {
                        while (eredmeny.moveToNext())
                        {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String teljesNev = eredmeny.getString(4);
                            editor.putString("TeljesNev", teljesNev);
                            Toast.makeText(MainActivity.this, teljesNev, Toast.LENGTH_SHORT).show();
                            editor.apply();
                        }
                        Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Hibás felhasználó!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void init(){
        etFelhasznaloNev = findViewById(R.id.etFelhasznaloNev);
        etJelszo = findViewById(R.id.etJelszo);

        btBelep = findViewById(R.id.btnBejelentkezes);
        btRegisztral = findViewById(R.id.btnRegisztracio);

        abh = new ABH(this);

        sharedPreferences = getSharedPreferences("Adatok", Context.MODE_PRIVATE);
    }
}
