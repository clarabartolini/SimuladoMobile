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

    public void adicionar(Carro argNota, Context c) {
        // ABRIR DB
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // INSERIR NOVA PLACA
        db.collection("veiculo").document(String.valueOf(argNota.getPlaca())).set(argNota);
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

    public void listar(List<Carro> argLista, AdapterCarro agrAdapter, Context c){

        // ABRIR DB
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // ATIVAR REAL TIME
        database.collection("veiculo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("Deu RUIM..!!" + error.getMessage());
                }
                assert value != null;
                argLista.clear();

                for (DocumentSnapshot document : value.getDocuments()) {
                    Carro estacionamento = document.toObject(Carro.class);
                    argLista.add(estacionamento);
                }
                agrAdapter.notifyDataSetChanged();
            }
        });
    }
}

