package com.aula.estacionamentojbs.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aula.estacionamentojbs.MainActivity;
import com.aula.estacionamentojbs.R;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView mensagemErro;
    private Button btnLoginNormal;
    private Button btnLoginAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        mensagemErro = findViewById(R.id.mensagemErro);
        btnLoginNormal = findViewById(R.id.btnLoginNormal);
        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);

        btnLoginNormal.setOnClickListener(v -> {
            String user = usernameEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();

            if (user.equals("user") && pass.equals("123")) {
                mensagemErro.setVisibility(View.GONE);
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("nome_usuario", user);
                intent.putExtra("tipo_usuario", "user");
                startActivity(intent);
                finish();
            } else {
                mensagemErro.setVisibility(View.VISIBLE);
                mensagemErro.setText("Usuário ou senha inválidos!");
            }
        });

        btnLoginAdmin.setOnClickListener(v -> {
            String user = usernameEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();

            if (user.equals("admin") && pass.equals("123")) {
                mensagemErro.setVisibility(View.GONE);
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("nome_usuario", user);
                intent.putExtra("tipo_usuario", "admin");
                startActivity(intent);
                finish();
            } else {
                mensagemErro.setVisibility(View.VISIBLE);
                mensagemErro.setText("Usuário ou senha inválidos!");
            }
        });
    }
}
