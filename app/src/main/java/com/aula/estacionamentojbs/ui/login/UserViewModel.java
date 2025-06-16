package com.aula.estacionamentojbs.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<String> nomeUsuario = new MutableLiveData<>();
    private final MutableLiveData<String> tipoUsuario = new MutableLiveData<>();

    public void setNomeUsuario(String nome) {
        nomeUsuario.setValue(nome);
    }

    public LiveData<String> getNomeUsuario() {
        return nomeUsuario;
    }

    public void setTipoUsuario(String tipo) {
        tipoUsuario.setValue(tipo);
    }

    public LiveData<String> getTipoUsuario() {
        return tipoUsuario;
    }
}
