package com.example.movietime.autentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movietime.R;
import com.example.movietime.SingletonUser;
import com.example.movietime.database.DBHelper;
import com.example.movietime.main.activity.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Username, Password;
    private TextView RegisterLink;
    private Button Login;
    Session session;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logotipo);

        db = new DBHelper(this);
        session = new Session(this);

        SingletonUser.singleton(this.getApplicationContext());

        Username = (EditText) findViewById(R.id.etUsername);
        Password = (EditText) findViewById(R.id.etPassword);

        Login = (Button) findViewById(R.id.btnLogin);

        RegisterLink = (TextView) findViewById(R.id.tvRegister);

        Login.setOnClickListener(this);
        RegisterLink.setOnClickListener(this);

        if (session.loggedin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

                String username = Username.getText().toString();
                String password = Password.getText().toString();

                if (username.equals("")) {
                    Toast.makeText(this, R.string.error_username, Toast.LENGTH_LONG).show();
                } else if (password.equals("")) {
                    Toast.makeText(this, R.string.error_pass, Toast.LENGTH_LONG).show();
                } else {
                    String res = db.ValidarLogin(username, password);
                    Cursor c = db.mostrarRegisto(username, password);

                    if (res.equals("OK")) {

                        session.setLoggedin(true);

                        Toast.makeText(LoginActivity.this, "Welcome " + username, Toast.LENGTH_LONG).show();

                        SingletonUser.singleton().storeValueString("Username", username);
                        SingletonUser.singleton().storeValueString("Email", c.getString(1));
                        SingletonUser.singleton().storeValueString("Password", password);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(this, R.string.error_login, Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.tvRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to leave?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
