package com.aula.estacionamentojbs.model;

public class Carro {
    private String proprietario;
    private String placa;
    private String veiculo;
    private String cor;
    private String horaEntrada;
    private String horaSaida;

    public Carro(String proprietario, String placa, String veiculo, String cor, String horaEntrada, String horaSaida) {
        this.proprietario = proprietario;
        this.placa = placa;
        this.veiculo = veiculo;
        this.cor = cor;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;

    }


    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca() {
        this.placa = placa;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    @Override
    public String toString() {
        return "Estacionamento{" +
                "proprietario='" + proprietario + '\'' +
                ", placa='" + placa + '\'' +
                ", veiculo='" + veiculo + '\'' +
                ", cor='" + cor + '\'' +
                ", horaEntrada='" + horaEntrada + '\'' +
                ", horaSaida='" + horaSaida + '\'' +
                '}';
    }
}
