package com.aula.estacionamentojbs.ui.perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aula.estacionamentojbs.R;
import com.aula.estacionamentojbs.ui.login.UserViewModel;

public class Perfil extends Fragment {

    private TextView nomeUsuario;
    private TextView tipoUsuario;
    private UserViewModel userViewModel;

    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        nomeUsuario = view.findViewById(R.id.nomeUsuario);
        tipoUsuario = view.findViewById(R.id.tipoUsuario);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.getNomeUsuario().observe(getViewLifecycleOwner(), nome -> {
            nomeUsuario.setText("Usuário: " + nome);
        });

        userViewModel.getTipoUsuario().observe(getViewLifecycleOwner(), tipo -> {
            tipoUsuario.setText("Tipo: " + tipo);
        });

        return view;
    }
}
