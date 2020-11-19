package com.example.movietime.autentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movietime.R;
import com.example.movietime.database.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Username, Email;
    private Button Cancel, Register;
    private TextInputEditText Password1, Password2;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logotipo);

        db = new DBHelper(this);

        Username = (EditText) findViewById(R.id.etUsername);
        Email = (EditText) findViewById(R.id.etEmail);
        Password1 = (TextInputEditText) findViewById(R.id.etPass1);
        Password2 = (TextInputEditText) findViewById(R.id.etPass2);
        Cancel = (Button) findViewById(R.id.btnCancel);
        Register = (Button) findViewById(R.id.btnRegister);

        Register.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRegister:
                String username = Username.getText().toString();
                String email = Email.getText().toString();
                String p1 = Password1.getText().toString();
                String p2 = Password2.getText().toString();

                if (username.equals("")) {
                    Toast.makeText(this, R.string.error_username, Toast.LENGTH_LONG).show();
                } else if (email.equals("")) {
                    Toast.makeText(this, R.string.error_email, Toast.LENGTH_LONG).show();
                } else if (p1.equals("") || p2.equals("")) {
                    Toast.makeText(this, R.string.error_pass, Toast.LENGTH_LONG).show();
                } else if (!p1.equals(p2)) {
                    Toast.makeText(this, R.string.different_passwords, Toast.LENGTH_LONG).show();
                } else {
                    long res = db.CriarUtilizador(username, email, p1);
                    if (res > 0) {
                        Toast.makeText(this, R.string.success_register, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, LoginActivity.class));
                    } else {
                        Toast.makeText(this, R.string.invalid_register, Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.btnCancel:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            default:
        }
    }
}
