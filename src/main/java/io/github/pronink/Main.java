package io.github.pronink;

import io.github.pronink.hilos.DatosCompartidos;
import io.github.pronink.hilos.EventosListener;
import io.github.pronink.hilos.HiloBaseDatos;
import io.github.pronink.hilos.HiloMinecraft;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private HiloBaseDatos hiloBaseDatos;

    @Override
    public void onEnable() {
        DatosCompartidos datosCompartidos = new DatosCompartidos();
        hiloBaseDatos = new HiloBaseDatos(datosCompartidos);
        hiloBaseDatos.start();
        Bukkit.getServer().getPluginManager().registerEvents(new EventosListener(datosCompartidos), this);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new HiloMinecraft(datosCompartidos), 1, 1);
    }


    @Override
    public void onDisable() {
        hiloBaseDatos.cerrarPool();
    }
}
