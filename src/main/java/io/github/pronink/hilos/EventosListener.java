package io.github.pronink.hilos;

import io.github.pronink.entidades.MensajeArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EventosListener implements Listener {

    private final DatosCompartidos datosCompartidos;

    public EventosListener(DatosCompartidos datosCompartidos) {
        this.datosCompartidos = datosCompartidos;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand) {
            int mensajeId = MensajeArmorStand.leerMensaje((ArmorStand) event.getEntity());
            if (mensajeId != 0){
                datosCompartidos.agregarMensajeLeido(mensajeId);
            }
        }
    }

}
