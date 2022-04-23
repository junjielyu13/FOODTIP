package com.example.foodtip.View;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repeatPassword;
    private final short MIN_PWD_LEN = 8, MAX_PWD_LEN = 16;
    private FoodTip foodTip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        foodTip = FoodTip.getInstance();
        username = findViewById(R.id.UserNameREGISTER);
        password = findViewById(R.id.PasswordREGISTER);
        repeatPassword = findViewById(R.id.RepeatPasswordREGISTER);
    }

    /**
     * set register button click listener to register
     * @param view
     */
    public void RegisterButtonOnClickListenerRegister(View view) {
        String user_name = username.getText().toString();
        String pwd = password.getText().toString();
        String rePwd = repeatPassword.getText().toString();

        if(cheackPwd(pwd,rePwd)){
            foodTip.CreatNewUser(this,user_name,pwd,user_name);
        }
    }
    private boolean cheackPwd(@NonNull String password, String repPwd){
        if(!password.equals(repPwd)){
            showAlertDialog("Error","The repeat password is not the same as the previous one",R.drawable.ic_error);
            return false;
        }
        if(password.length() < MIN_PWD_LEN && password.length() > MAX_PWD_LEN ){
            showAlertDialog("Error","Password length must be more than 8 characters or less equal than 16 characters",R.drawable.ic_error);
            return false;
        }
        return true;
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
