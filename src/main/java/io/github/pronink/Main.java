package io.github.pronink;

import io.github.pronink.hilos.DatosCompartidos;
import io.github.pronink.hilos.EventosListener;
import io.github.pronink.hilos.HiloBaseDatos;
import io.github.pronink.hilos.HiloMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private HiloBaseDatos hiloBaseDatos;
    private EventosListener eventosListener;
    private HiloMinecraft hiloMinecraft;

    @Override
    public void onEnable() {
        DatosCompartidos datosCompartidos = new DatosCompartidos();
        hiloBaseDatos = new HiloBaseDatos(datosCompartidos);
        hiloBaseDatos.start();
        eventosListener = new EventosListener(datosCompartidos);
        hiloMinecraft = new HiloMinecraft(datosCompartidos);
        Bukkit.getServer().getPluginManager().registerEvents(eventosListener, this);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, hiloMinecraft, 1, 1);
    }


    @Override
    public void onDisable() {
        hiloBaseDatos.sincronizarBaseDeDatos();
        hiloMinecraft.sincronizarArmorStands();

        hiloBaseDatos.destruirHilo();
        // No hace falta destruir el hilo de Minecraft porque es un hilo especial que maneja la API de spigot
    }
}
