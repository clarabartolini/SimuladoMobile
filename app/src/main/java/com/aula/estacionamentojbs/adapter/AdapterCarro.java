package com.aula.estacionamentojbs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.estacionamentojbs.model.Carro;
import com.aula.estacionamentojbs.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterCarro extends RecyclerView.Adapter<AdapterCarro.ViewHolder> {

    private List<Carro> carros;
    private String tipoUsuario;
    private OnCarroActionListener listener;

    // Interface para callbacks
    public interface OnCarroActionListener {
        void onExcluirRegistro(Carro carro);
        void onRegistrarSaida(Carro carro, int position);
    }

    public AdapterCarro(List<Carro> carros, String tipoUsuario, OnCarroActionListener listener) {
        this.carros = carros;
        this.tipoUsuario = tipoUsuario;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterCarro.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carro, parent, false);
        return new ViewHolder(view);
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarro.ViewHolder holder, int position) {
        Carro carro = carros.get(position);

        holder.proprietario.setText("Proprietário: " + carro.getProprietario());
        holder.placa.setText("Placa: " + carro.getPlaca());
        holder.veiculo.setText("Veículo: " + carro.getVeiculo());
        holder.cor.setText("Cor: " + carro.getCor());
        holder.entrada.setText("Horário de entrada: " + carro.getHoraEntrada());
        holder.saida.setText("Horário de saída: " + carro.getHoraSaida());

        if ("admin".equalsIgnoreCase(tipoUsuario)) {
            holder.btnExcluirRegistro.setVisibility(View.VISIBLE);
            holder.btnRegistrarSaida.setVisibility(View.GONE);
        } else {
            holder.btnExcluirRegistro.setVisibility(View.GONE);
            holder.btnRegistrarSaida.setVisibility(View.VISIBLE);
        }

        holder.btnExcluirRegistro.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExcluirRegistro(carros.get(position));
            }
        });

        holder.btnRegistrarSaida.setOnClickListener(v -> {
            String horaAtual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            carro.setHoraSaida(horaAtual);

            holder.saida.setText("Horário de saída: " + horaAtual);

            if (listener != null) {
                listener.onRegistrarSaida(carro, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView proprietario, placa, veiculo, cor, entrada, saida;
        Button btnExcluirRegistro, btnRegistrarSaida;
        ConstraintLayout fundo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proprietario = itemView.findViewById(R.id.proprietario);
            placa = itemView.findViewById(R.id.placa);
            veiculo = itemView.findViewById(R.id.veiculo);
            cor = itemView.findViewById(R.id.cor);
            entrada = itemView.findViewById(R.id.entrada);
            saida = itemView.findViewById(R.id.saida);
            btnRegistrarSaida = itemView.findViewById(R.id.btnRegistrarSaida);
            btnExcluirRegistro = itemView.findViewById(R.id.btnExcluirRegistro);
            fundo = itemView.findViewById(R.id.fundo);
        }
    }
}
