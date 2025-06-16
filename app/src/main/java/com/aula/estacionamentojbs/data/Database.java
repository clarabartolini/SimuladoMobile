package com.aula.estacionamentojbs.data;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aula.estacionamentojbs.adapter.AdapterCarro;
import com.aula.estacionamentojbs.model.Carro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Database {

    public Database() {
    }
    public void atualizarSaida(Carro carro, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("veiculo").document(carro.getPlaca())
                .update("horaSaida", carro.getHoraSaida());
    }
    public void adicionar(Carro argNota, Context c) {
        if (argNota.getPlaca() == null || argNota.getPlaca().trim().isEmpty()) {
            Toast.makeText(c, "Erro: a placa está vazia!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("veiculo")
                .document(argNota.getPlaca())
                .set(argNota)
                .addOnSuccessListener(unused ->
                        Toast.makeText(c, "Veículo salvo com sucesso!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(c, "Erro ao salvar veículo: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }


    public void excluir(Carro agrnota, Context c) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("veiculo").document(String.valueOf(agrnota.getPlaca())).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(c, "Removida com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listar(List<Carro> argLista, AdapterCarro agrAdapter, Context c) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("veiculo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(c, "Erro ao acessar o banco: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value == null) {
                    Toast.makeText(c, "Nenhum dado retornado do Firestore", Toast.LENGTH_SHORT).show();
                    return;
                }

                argLista.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    Carro estacionamento = document.toObject(Carro.class);
                    if (estacionamento != null) {
                        argLista.add(estacionamento);
                    }
                }
                agrAdapter.notifyDataSetChanged();
            }
        });
    }


}


