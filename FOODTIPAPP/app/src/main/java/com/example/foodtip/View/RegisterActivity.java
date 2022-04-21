package com.example.foodtip.View;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.DataTest.Login_dades;
import com.example.foodtip.View.MainActivity;
import com.example.foodtip.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repeatPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.UserNameREGISTER);
        password = findViewById(R.id.PasswordREGISTER);
        repeatPassword = findViewById(R.id.RepeatPasswordREGISTER);

    }

    /**
     * set register button click listener to register
     * @param view
     */
    public void RegisterButtonOnClickListenerRegister(View view) {
        final int minim_pwd_length = 8;
        final int max_pwd_length = 16;
        String user_name = username.getText().toString();
        String pwd = password.getText().toString();
        String rePwd = repeatPassword.getText().toString();
        if (MainActivity.ld.getUsersMap().containsKey(user_name)) {
            showAlertDialog("Error!,", "Username exist!", R.mipmap.empty_img);
        } else {
            if (pwd.length() > max_pwd_length && pwd.length() < minim_pwd_length) {
                showAlertDialog("Error!", "Password length must be more than 8 characters or less equal than 16 characters", R.mipmap.ic_launcher);
            } else {
                if (pwd.equals(rePwd)) {
                    MainActivity.ld.addUsers(user_name, pwd);
                    Toast.makeText(getApplicationContext(), "Register Sucessful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    showAlertDialog("Error!", "The repeat password is not the same as the previous one", R.mipmap.empty_img);
                }
            }
        }


    }

    /**
     * support function to show AlertDialog
     * @param title title of alertDialog
     * @param message message of alertDialog
     * @param icon icon of alertDialog
     */
    private void showAlertDialog(String title, String message, int icon) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        username.getText().clear();
                        password.getText().clear();
                        repeatPassword.getText().clear();
                    }
                })
                .create();
        alertDialog.show();
    }
}
