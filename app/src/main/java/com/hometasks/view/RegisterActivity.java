package com.hometasks.view;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hometasks.R;
import com.hometasks.controller.JsonResponse;
import com.hometasks.controller.Service;
import com.hometasks.pojo.User;


public class RegisterActivity extends AppCompatActivity {
    private EditText mUser;
    private EditText mPassword;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        final User newUser = new User();

        mUser = findViewById(R.id.editUser);
        mPassword = findViewById(R.id.editPassword);
        mName = findViewById(R.id.editName);
        mPhone = findViewById(R.id.editTelephone);
        mEmail = findViewById(R.id.editEmail);
        mDate = findViewById(R.id.editDate);
        Button mRegistro = findViewById(R.id.buttonRegister);

        mRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser.setIdUser(mUser.getText().toString());
                newUser.setPassword(mPassword.getText().toString());
                newUser.setName(mName.getText().toString());
                newUser.setTelephone(mPhone.getText().toString());
                newUser.setEmail(mEmail.getText().toString());
                newUser.setDate(mDate.getText().toString());
                newUser.setPoints(0);
                newUser.setProfile("");

                @SuppressLint("StaticFieldLeak")
                AsyncTask<Void, Void, JsonResponse> task = new AsyncTask<Void, Void, JsonResponse>() {
                    @Override
                    protected JsonResponse doInBackground(Void... voids) {
                        return Service.postObject(newUser);
                    }

                    @Override
                    protected void onPostExecute(JsonResponse resp) {
                        super.onPostExecute(resp);
                        if (resp.isStatus()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.successful_register), Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), resp.getJsonValue("error"), Toast.LENGTH_LONG).show();
                        }
                    }
                };

                task.execute();

            }
        });

    }

}
