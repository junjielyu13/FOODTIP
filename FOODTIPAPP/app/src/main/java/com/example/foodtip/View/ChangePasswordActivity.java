package com.example.foodtip.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText newPassword;
    EditText confirmNewPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_new_pwd);
        newPassword = findViewById(R.id.newPasswordEditText);
        confirmNewPassword = findViewById(R.id.confirmPasswordEditText);
    }

    public void resetPasswordButtonOnClick(View view) {
        String newPasswordText = newPassword.getText().toString().trim();
        String confirmNewPasswordText = confirmNewPassword.getText().toString().trim();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(newPasswordText.isEmpty()){
            this.newPassword.setError("New password is required!");
            this.newPassword.requestFocus();
            return;
        }
        if(confirmNewPasswordText.isEmpty()){
            this.confirmNewPassword.setError("Repeat the password!");
            this.confirmNewPassword.requestFocus();
            return;
        }

        if(newPasswordText.equals(FoodTip.getInstance().getUser().getPassword())){
            this.newPassword.setError("New password should be different!");
            this.newPassword.setText("");
            this.confirmNewPassword.setText("");
            this.newPassword.requestFocus();
            return;
        }
        if(!newPasswordText.equals(confirmNewPasswordText)){
            this.confirmNewPassword.setError("Incorrect password!");
            confirmNewPassword.requestFocus();
            this.confirmNewPassword.setText("");
            return;
        }
        FoodTip.getInstance().getUser().setPassword(newPasswordText);
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPasswordText).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ChangePasswordActivity.this, "Password changed successful", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ChangePasswordActivity.this, "Process fail", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });


    }


}
