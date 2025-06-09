package com.aula.estacionamentojbs.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.aula.estacionamentojbs.R;
import com.aula.estacionamentojbs.ui.login.Login;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Aguarda 2 segundos e vai para o login
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, Login.class));
            finish();
        }, 2000);
    }
}
