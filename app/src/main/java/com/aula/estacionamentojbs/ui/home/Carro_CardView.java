package com.aula.estacionamentojbs.ui.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aula.estacionamentojbs.R;
import com.aula.estacionamentojbs.adapter.AdapterCarro;
import com.aula.estacionamentojbs.data.Database;
import com.aula.estacionamentojbs.databinding.FragmentHomeBinding;
import com.aula.estacionamentojbs.model.Carro;
import com.aula.estacionamentojbs.ui.dialog.AdicionarDialog;
import com.aula.estacionamentojbs.ui.login.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Carro_CardView extends Fragment {

    private FragmentHomeBinding binding;
    private final List<Carro> carros = new ArrayList<>();
    private String tipoUsuario = "user";

    private String proprietario = "";
    private AdapterCarro adapterCarros;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        binding.car.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterCarros = new AdapterCarro(carros, tipoUsuario, new AdapterCarro.OnCarroActionListener() {
            @Override
            public void onExcluirRegistro(Carro carro) {
                abrirDialogExcluir(carro);
            }

            @Override
            public void onRegistrarSaida(Carro carro, int position) {
                String horaAtual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                carro.setHoraSaida(horaAtual);

                // Atualiza no banco se necessário:
                Database database = new Database();
                database.atualizarSaida(carro, getContext());

                adapterCarros.notifyItemChanged(position);
            }
        });

        binding.car.setAdapter(adapterCarros);

        userViewModel.getTipoUsuario().observe(getViewLifecycleOwner(), tipo -> {
            tipoUsuario = tipo != null ? tipo.toLowerCase() : "user";

            adapterCarros.setTipoUsuario(tipoUsuario);
            adapterCarros.notifyDataSetChanged();

            if ("user".equals(tipoUsuario)) {
                binding.fabAddCar.setVisibility(View.VISIBLE);
            } else {
                binding.fabAddCar.setVisibility(View.GONE);
            }
        });

        userViewModel.getNomeUsuario().observe(getViewLifecycleOwner(), nome -> {
            if (nome != null) {
                proprietario = nome;
            }
        });

        binding.fabAddCar.setOnClickListener(v -> abrirDialogAdicionar());

        // Dados de exemplo
        carros.clear();
//        carros.add(new Carro("João", "ABC-1234", "Fiat Uno", "Vermelho", "08:00", ""));
//        carros.add(new Carro("Maria", "XYZ-5678", "Ford Ka", "Preto", "09:15", ""));
//        carros.add(new Carro("Sara", "GIJ-2345", "Gol Bolinha", "Azul Escuro", "15:12", ""));
        Database database = new Database();
        database.listar(carros, adapterCarros, getContext());
        adapterCarros.notifyDataSetChanged();
    }

    private void abrirDialogAdicionar() {
        AdicionarDialog dialog = new AdicionarDialog();
        dialog.setProprietario(proprietario);
        dialog.setAdicionarDialogListener(carro -> {
            carros.add(0, carro);
            adapterCarros.notifyItemInserted(0);
            binding.car.scrollToPosition(0);
        });
        dialog.show(getParentFragmentManager(), "AdicionarDialog");
    }

    private void abrirDialogExcluir(Carro carro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_excluir, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView mensagemExcluir = dialogView.findViewById(R.id.mensagemExcluir);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluir);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

        mensagemExcluir.setText("Tem certeza que deseja excluir o registro do veículo " + carro.getPlaca() + "?");

        btnExcluir.setOnClickListener(v -> {
            Database database = new Database();
            database.excluir(carro, getContext());
            carros.remove(carro);
            adapterCarros.notifyDataSetChanged();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
