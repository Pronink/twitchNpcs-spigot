package io.github.pronink.hilos;

import io.github.pronink.Informacion;
import io.github.pronink.entidades.MensajeArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class HiloMinecraft implements Runnable {

    private final DatosCompartidos datosCompartidos;

    private int state = 0;

    private Collection<ArmorStand> listaArmorStands = new ArrayList<>();

    public HiloMinecraft(DatosCompartidos datosCompartidos) {
        this.datosCompartidos = datosCompartidos;
    }

    // Se ejecuta 20 veces por segundo (1 tick/segundo)
    @Override
    public void run()
    {
        state = ++state % 1200; // EL CICLO DURA 1 MINUTO
        // Obtengo de la variable compartida los Mensajes que debo spawnear y los spawneo si puedo.
        if (state % 100 == 0) // Cada 5 segundos (100 ticks)
        {
            sincronizarArmorStands();
        }
        // Mantengo fija la referencia de todos los Armorstands con mensajes cercanos a los jugadores y la refresco cada 1 segundo
        if (state % 20 == 0) // Cada 1 segundo (20 ticks)
        {
            listaArmorStands.clear();
            for (Player jugador : Bukkit.getOnlinePlayers()) {
                for (Entity entidad : jugador.getNearbyEntities(30, 30, 30)) {
                    if (entidad instanceof ArmorStand) {
                        listaArmorStands.add((ArmorStand) entidad);
                    }
                }
            }
            // Remover duplicados
            listaArmorStands = new ArrayList<ArmorStand>(new HashSet<ArmorStand>(listaArmorStands));
        }
        // Cada medio segundo refresco el Scoreboard (texto en la derecha de la pantalla) con las coordenadas y el manual
        if (state % 10 == 0) // Cada 0.5 segundos (10 ticks)
        {
            Informacion.pintarInformacion((Collection<Player>)Bukkit.getOnlinePlayers());
        }
        // Rota despacio y crea partículas alrededor de los Armorstands con mensajes
        if (state % 2 == 0)  // Cada 0.1 segundos (2 ticks)
        {
            MensajeArmorStand.animarMensajes(listaArmorStands);
        }
    }

    public void sincronizarArmorStands(){
        // Obtiene la lista de mensajes que la base de datos ha dejado en su variable compartida
        ArrayList<MensajeArmorStand> listaMensajesArmorStands = datosCompartidos.getListaMensajesArmorStand();
        if (listaMensajesArmorStands.size() > 0) {
            for (MensajeArmorStand mensajeArmorStand : listaMensajesArmorStands){
                boolean spawneadoCorrecto = MensajeArmorStand.spawnearMensajeArmorStand(mensajeArmorStand);
                if (!spawneadoCorrecto){ // Si se ha podido spawnear, debo avisar a la base de datos de que no ha sido spawneado
                    System.out.println("Un MensajeArmorStand no ha spawneado !!! id: " + mensajeArmorStand.getId());
                }
            }
        }
    }

}
