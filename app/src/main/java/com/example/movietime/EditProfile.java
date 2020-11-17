package com.example.movietime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditProfile extends AppCompatActivity {

    private TextView User;
    private TextInputEditText Password1, Password2;
    private EditText et_email;
    private String user, email, password;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getSupportActionBar().setSubtitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logotipo);

        db = new DBHelper(this);

        SharedPreferences prefs = EditProfile.this.getSharedPreferences("User", Context.MODE_PRIVATE);
        String nomeArmazenado = prefs.getString("Username", null);
        String EmailArmazenado = prefs.getString("Email", null);
        String PasswordArmazenado = prefs.getString("Password", null);

        if ((nomeArmazenado != null) && (EmailArmazenado != null) && (PasswordArmazenado != null)) {
            user = prefs.getString("Username", "");
            email = prefs.getString("Email", "");
            password = prefs.getString("Password", "");
        }

        et_email = (EditText) findViewById(R.id.etEmail);
        User = (TextView) findViewById(R.id.etUsername);
        Password1 = (TextInputEditText) findViewById(R.id.etPassword1);
        Password2 = (TextInputEditText) findViewById(R.id.etPassword2);

        et_email.setText(email);
        User.setText(user);
        Password1.setText(password);
        Password2.setText(password);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_Save:
                String username = User.getText().toString();
                String email = et_email.getText().toString();
                String senha1 = Password1.getText().toString();
                String senha2 = Password2.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(this, R.string.error_email, Toast.LENGTH_LONG).show();
                } else if (senha1.equals("") || senha2.equals("")) {
                    Toast.makeText(this, R.string.error_pass, Toast.LENGTH_LONG).show();
                } else if (!senha1.equals(senha2)) {
                    Toast.makeText(this, R.string.different_passwords, Toast.LENGTH_LONG).show();
                } else {
                    long res = db.Update_Dados_Pessoais(username, senha1, email);

                    if (res > 0) {
                        String result = db.Validar_Dados_Pessoais(username);

                        if (result.equals("OK")) {
                            SharedPreferences sharedPreferences = EditProfile.this.getSharedPreferences("User", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Username", username);
                            editor.putString("Email", email);
                            editor.putString("Password", senha1);
                            editor.commit();

                            Toast.makeText(this, getString(R.string.update_data_saved) + username, Toast.LENGTH_LONG).show();

                            startActivity(new Intent(EditProfile.this, MainActivity.class));

                        } else {
                            Toast.makeText(this, R.string.error_update_data, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;

            case R.id.action_Back:
                startActivity(new Intent(EditProfile.this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
