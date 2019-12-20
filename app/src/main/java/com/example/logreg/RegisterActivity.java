package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etFelhasznaloNev, etJelszo, etTNev;
    private Button btnReg, btnVissza;

    private ABH abh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString() != "" &&
                    etFelhasznaloNev.getText().toString() != "" &&
                    !teljesnevE(etFelhasznaloNev.getText().toString()) &&
                    etJelszo.getText().toString() != "" &&
                    etTNev.getText().toString() != "" ){
                    if (abh.emailIsValid(etEmail.getText().toString())){
                        if (!abh.adatFelvetel(etEmail.getText().toString(),
                                etFelhasznaloNev.getText().toString(),
                                etJelszo.getText().toString(),
                                etTNev.getText().toString())){

                            Toast.makeText(RegisterActivity.this,
                                    "Sikertelen regisztráció!",
                                    Toast.LENGTH_SHORT);
                        }

                        Toast.makeText(RegisterActivity.this,
                                "Sikeres regisztráció!",
                                Toast.LENGTH_SHORT);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,
                                "Hibás email format",
                                Toast.LENGTH_SHORT);
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this,
                            "Hiányos adatokat észlelt a program",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main =
                        new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void init(){
        etEmail = findViewById(R.id.etEmail);
        etFelhasznaloNev = findViewById(R.id.etFelhasznaloNevReg);
        etJelszo = findViewById(R.id.etJelszoReg);
        etTNev = findViewById(R.id.etNevReg);

        btnReg = findViewById(R.id.btnRegisztracioReg);
        btnVissza = findViewById(R.id.btnVissza);

        abh = new ABH(this);
    }

    private boolean teljesnevE(String teljesnev){

        String[] tn = teljesnev.split(" ");

        return
            tn.length > 1 &&
            tn[0].equals(tn[0].substring(1)) &&
            tn[1].equals(tn[1].substring(1));
    }
}
