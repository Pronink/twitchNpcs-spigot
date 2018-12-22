package io.github.pronink.hilos;

import io.github.pronink.entidades.MensajeArmorStand;
import java.util.ArrayList;

public class DatosCompartidos {
    private ArrayList<Integer> listaMensajesSpawneados;
    private ArrayList<Integer> listaMensajesLeidos;
    private ArrayList<MensajeArmorStand> listaMensajesArmorStand;

    public DatosCompartidos() {
        this.listaMensajesSpawneados = new ArrayList<>();
        this.listaMensajesLeidos = new ArrayList<>();
        this.listaMensajesArmorStand = new ArrayList<>();
    }
    public synchronized void agregarMensajeSpawneados(int mensajeSpawneado) {
        listaMensajesSpawneados.add(mensajeSpawneado);
    }

    public synchronized void agregarMensajeLeido(int mensajeLeido) {
        listaMensajesLeidos.add(mensajeLeido);
    }

    public synchronized void agregarMensajeArmorStand(MensajeArmorStand mensajeArmorStand) {
        listaMensajesArmorStand.add(mensajeArmorStand);
    }

    public synchronized ArrayList<Integer> getListaMensajesSpawneados() {
        ArrayList<Integer> copia = new ArrayList<>(listaMensajesSpawneados);
        listaMensajesSpawneados.clear();
        return copia;
    }

    public synchronized ArrayList<Integer> getListaMensajesLeidos() {
        ArrayList<Integer> copia = new ArrayList<>(listaMensajesLeidos);
        listaMensajesLeidos.clear();
        return copia;
    }

    public synchronized ArrayList<MensajeArmorStand> getListaMensajesArmorStand() {
        ArrayList<MensajeArmorStand> copia = new ArrayList<>(listaMensajesArmorStand);
        listaMensajesArmorStand.clear();
        return copia;
    }

}
