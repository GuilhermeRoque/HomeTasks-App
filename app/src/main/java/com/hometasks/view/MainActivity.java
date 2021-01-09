package com.hometasks.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hometasks.R;
import com.hometasks.controller.JsonResponse;
import com.hometasks.controller.Service;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private String username;
    private String password;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.login);
        senha = findViewById(R.id.password);
        Button button_login = findViewById(R.id.button);
        Button button_registro = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(GONE);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    progressBar.setVisibility(VISIBLE);

                    username = email.getText().toString();
                    password = senha.getText().toString();

                    @SuppressLint("StaticFieldLeak")
                    AsyncTask<Void, Void, JsonResponse> task = new AsyncTask<Void, Void, JsonResponse>() {
                        @Override
                        protected JsonResponse doInBackground(Void... voids) {
                            return Service.authUser(username, password);
                        }
                        @Override
                        protected void onPostExecute(JsonResponse result){
                            super.onPostExecute(result);
                            progressBar.setVisibility(GONE);
                            if (result == null) Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();
                            else {
                                if (result.isStatus()) {
                                    Toast.makeText(getApplicationContext(), "Bem vindo!", Toast.LENGTH_LONG).show();
                                    goToMenuActivity();

                                }else{
                                    Toast.makeText(getApplicationContext(), result.getJsonValue("error"), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                    };

                    task.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("Deseja realmente sair do aplicativo?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void goToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void goToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}