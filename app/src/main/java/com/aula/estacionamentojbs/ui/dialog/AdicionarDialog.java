package com.aula.estacionamentojbs.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aula.estacionamentojbs.R;
import com.aula.estacionamentojbs.data.Database;
import com.aula.estacionamentojbs.model.Carro;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdicionarDialog extends DialogFragment {

    private EditText placa, cor, veiculo;
    private Button btnAdicionar, btnCancelar;
    private String proprietario;
    private AdicionarDialogListener listener;

    public interface AdicionarDialogListener {
        void onCarroAdicionado(Carro carro);
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public void setAdicionarDialogListener(AdicionarDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove barra de título e fundo branco
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_adicionar, container, false);

        placa = view.findViewById(R.id.placa);
        cor = view.findViewById(R.id.cor);
        veiculo = view.findViewById(R.id.veiculo);
        btnAdicionar = view.findViewById(R.id.btnAdicionar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        btnCancelar.setOnClickListener(v -> dismiss());
        btnAdicionar.setOnClickListener(v -> adicionarCarro());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void adicionarCarro() {
        String placaStr = placa.getText().toString().trim().toUpperCase(Locale.ROOT);
        String corStr = cor.getText().toString().trim();
        String veiculoStr = veiculo.getText().toString().trim();

        if (placaStr.isEmpty() || corStr.isEmpty() || veiculoStr.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (placaStr.length() < 7) {
            Toast.makeText(getContext(), "Placa inválida. Deve conter pelo menos 7 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!placaStr.matches("^[A-Z0-9-]+$")) {
            Toast.makeText(getContext(), "Placa inválida. Use apenas letras, números e hífen", Toast.LENGTH_SHORT).show();
            return;
        }

        String horaEntradaFormatada = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        Carro novoCarro = new Carro(proprietario, placaStr, veiculoStr, corStr, horaEntradaFormatada, "");
        Database database = new Database();
        database.adicionar(novoCarro, getContext());

        if (listener != null) {
            listener.onCarroAdicionado(novoCarro);
        }

        Toast.makeText(getContext(), "Veículo adicionado com sucesso", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
