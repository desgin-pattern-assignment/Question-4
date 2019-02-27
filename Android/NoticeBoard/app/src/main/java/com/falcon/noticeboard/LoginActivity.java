package com.falcon.noticeboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
    }

    public void signinClicked(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.specify_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }
        if (Controller.isAdmin(username, password)) {
            Intent intent = new Intent(this, AdminMainActivity.class);
            startActivity(intent);
        } else if (Controller.isStudent(username, password)) {
            Intent intent = new Intent(this, StudentMainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.incorrect_credentials), Toast.LENGTH_SHORT).show();
        }
    }

    public void registerClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
