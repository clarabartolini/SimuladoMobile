package com.aula.estacionamentojbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aula.estacionamentojbs.databinding.ActivityCardsBinding;
import com.aula.estacionamentojbs.databinding.FragmentSplashBinding;


import java.util.ArrayList;

import java.util.List;

public class Cards extends Fragment {
    private FragmentSplashBinding binding;
    List<Estacionamento> carros = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurando o RecyclerView
        binding.car.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurando o adapter
        AdapterCarros adapterCarros = new AdapterCarros(carros);
        binding.car.setAdapter(adapterCarros);

        // Usando a classe Database
        Database database = new Database();
        database.listar(carros, adapterCarros, getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void carregarDados(Estacionamento arg) {
        carros.add(0, arg);
        binding.car.getAdapter().notifyDataSetChanged();
    }
}
