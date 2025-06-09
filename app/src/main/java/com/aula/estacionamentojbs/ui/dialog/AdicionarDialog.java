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
import com.aula.estacionamentojbs.model.Carro;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdicionarDialog extends DialogFragment {

    private EditText placa, cor, veiculo;
    private Button btnAdicionar, btnCancelar;

    private String proprietario;

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public interface AdicionarDialogListener {
        void onCarroAdicionado(Carro carro);
    }

    private AdicionarDialogListener listener;

    public void setAdicionarDialogListener(AdicionarDialogListener listener) {
        this.listener = listener;
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

        btnAdicionar.setOnClickListener(v -> {
            String placaStr = placa.getText().toString().trim();
            String corStr = cor.getText().toString().trim();
            String veiculoStr = veiculo.getText().toString().trim();

            if (placaStr.isEmpty() || corStr.isEmpty() || veiculoStr.isEmpty()) {
                Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String horaEntradaFormatada = sdf.format(new Date());

            Carro novoCarro = new Carro(proprietario, placaStr, veiculoStr, corStr, horaEntradaFormatada, "");

            if (listener != null) {
                listener.onCarroAdicionado(novoCarro);
            }

            dismiss();
        });

        return view;
    }
}
