package com.aula.estacionamentojbs;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    public Database(){}

    public void salvar(Estacionamento argNota, Context c){
        // ABRIR DB
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (argNota.getPlaca() == null){
            // OBTER O PROXIMO ID PARA NOTA
            db.collection("CountersId").document("nota_id").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    int id = 1;
                    if (task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()){
                            id = doc.getLong("id").intValue() + 1;
                        }
                        argNota.setPlaca();
                        // ATUALIZAR O ID
                        db.collection("CountersId").document("nota_id").update("id", id);
                        // INSERIR NOVA NOTA
                        db.collection("").document(String.valueOf(argNota.getPlaca())).set(argNota);
                        Toast.makeText(c, "Placa salva com sucesso", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            // INSERIR NOVA PLACA
            db.collection("carros").document(String.valueOf(argNota.getPlaca())).set(argNota);
        }
    }
    public void remover(Estacionamento agrnota, Context c) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("carros").document(String.valueOf(agrnota.getPlaca())).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(c, "Removida com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listar(List<Estacionamento> argLista, AdapterCarros agrAdapter, Context c){

        // ABRIR DB
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // ATIVAR REAL TIME
        database.collection("carros").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("Deu RUIM..!!" + error.getMessage());
                }
                assert value != null;
                argLista.clear();

                for (DocumentSnapshot document : value.getDocuments()) {
                    Estacionamento estacionamento = document.toObject(Estacionamento.class);
                    argLista.add(estacionamento);
                }
                agrAdapter.notifyDataSetChanged();
            }
        });
    }
}

