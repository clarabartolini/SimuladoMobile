package com.aula.estacionamentojbs;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
public class AdapterCarros extends RecyclerView.Adapter<AdapterCarros.ViewHolder>{
    private List<Estacionamento> carros;

    public AdapterCarros(List<Estacionamento> carros) {
        this.carros = carros;
    }

    @NonNull
    @Override
    public AdapterCarros.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarros.ViewHolder holder, int position) {
        holder.proprietario.setText(carros.get(position).getProprietario());
        holder.placa.setText(carros.get(position).getPlaca());
        holder.veiculo.setText(carros.get(position).getVeiculo());
        holder.cor.setText(carros.get(position).getCor());
        holder.entrada.setText(carros.get(position).getHoraEntrada());

        if(!Objects.equals(carros.get(position).getHoraSaida(), "Hora de saída:")){
            holder.fundo.setBackgroundColor(Color.parseColor("#009688"));

        }
        else{
            holder.fundo.setBackgroundColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return carros.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView proprietario, placa, veiculo, cor, entrada, saida;

        ConstraintLayout fundo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proprietario = itemView.findViewById(R.id.proprietario);
            placa = itemView.findViewById(R.id.placa);
            veiculo = itemView.findViewById(R.id.veiculo);
            cor = itemView.findViewById(R.id.cor);
            entrada = itemView.findViewById(R.id.entrada);
            saida = itemView.findViewById(R.id.saida);
            fundo = itemView.findViewById(R.id.fundo);
        }
    }
}
