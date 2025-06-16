package com.aula.estacionamentojbs;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.aula.estacionamentojbs.databinding.ActivityMainBinding;
import com.aula.estacionamentojbs.ui.login.UserViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String nomeUsuario;
    private String tipoUsuario;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nomeUsuario = getIntent().getStringExtra("nome_usuario");
        tipoUsuario = getIntent().getStringExtra("tipo_usuario");

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setNomeUsuario(nomeUsuario != null ? nomeUsuario : "Não definido");
        userViewModel.setTipoUsuario(tipoUsuario != null ? tipoUsuario : "Não definido");

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_perfil)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
