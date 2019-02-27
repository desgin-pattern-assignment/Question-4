package com.falcon.noticeboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private EditText usernameEditText, passwordEditText;
    private CheckBox adminCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        adminCheckBox = findViewById(R.id.adminCheckBox);
    }

    public void registerClicked(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isAdmin = adminCheckBox.isChecked();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.specify_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }
        int reply = Controller.register(username, password, isAdmin);
        switch (reply) {
            case 0:
                Toast.makeText(this, getString(R.string.successfully_registered), Toast.LENGTH_SHORT).show();
                if (isAdmin) {
                    startActivity(new Intent(this, AdminMainActivity.class));
                } else {
                    startActivity(new Intent(this, StudentMainActivity.class));
                }
                break;
            case 1:
                Toast.makeText(this, getString(R.string.already_has_admin), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, getString(R.string.username_exists), Toast.LENGTH_SHORT).show();
                break;
            case 3:
            case 4:
                Toast.makeText(this, getString(R.string.unable_to_register), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
