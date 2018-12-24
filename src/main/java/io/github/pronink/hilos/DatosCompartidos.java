package io.github.pronink.hilos;

import io.github.pronink.entidades.MensajeArmorStand;

import java.util.ArrayList;

public class DatosCompartidos {
    private ArrayList<Integer> listaMensajesLeidos;
    private ArrayList<MensajeArmorStand> listaMensajesArmorStand;

    public DatosCompartidos() {
        this.listaMensajesLeidos = new ArrayList<>();
        this.listaMensajesArmorStand = new ArrayList<>();
    }

    public synchronized void agregarMensajeLeido(int mensajeLeido) {
        listaMensajesLeidos.add(mensajeLeido);
    }

    public synchronized void agregarMensajeArmorStand(MensajeArmorStand mensajeArmorStand) {
        listaMensajesArmorStand.add(mensajeArmorStand);
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
