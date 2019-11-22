package io.github.pronink.hilos;

import io.github.pronink.entidades.MensajeArmorStand;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
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
            ArmorStand armorStand = (ArmorStand) event.getEntity();
            if (event.getDamager() instanceof Player && // Si es un jugador quien ha golpeado el armor stand
                    (armorStand.getHelmet().getType() == Material.MAGENTA_CONCRETE ||
                    armorStand.getHelmet().getType() == Material.RED_CONCRETE)) {
                int mensajeId = MensajeArmorStand.leerMensaje((ArmorStand) event.getEntity());
                if (mensajeId != 0) {
                    datosCompartidos.agregarMensajeLeido(mensajeId);
                }
            }

        }
    }

}
