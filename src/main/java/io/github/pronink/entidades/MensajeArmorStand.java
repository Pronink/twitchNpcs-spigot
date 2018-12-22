package io.github.pronink.entidades;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Random;

public class MensajeArmorStand {

    //region Propiedades
    private int id;
    private String usuarioId;
    private String usuarioNombreFantasia;
    private int plataformaId;
    private boolean esModerador;
    private boolean esSuscriptor;
    private String texto;
    private int x;
    private int y;
    private int z;
    private boolean haAparecido;
    private boolean haSidoLeido;
    //endregion

    //region Getters y Setters
    public MensajeArmorStand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombreFantasia() {
        return usuarioNombreFantasia;
    }

    public void setUsuarioNombreFantasia(String usuarioNombreFantasia) {
        this.usuarioNombreFantasia = usuarioNombreFantasia;
    }

    public int getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(int plataformaId) {
        this.plataformaId = plataformaId;
    }

    public boolean isEsModerador() {
        return esModerador;
    }

    public void setEsModerador(boolean esModerador) {
        this.esModerador = esModerador;
    }

    public boolean isEsSuscriptor() {
        return esSuscriptor;
    }

    public void setEsSuscriptor(boolean esSuscriptor) {
        this.esSuscriptor = esSuscriptor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isHaAparecido() {
        return haAparecido;
    }

    public void setHaAparecido(boolean haAparecido) {
        this.haAparecido = haAparecido;
    }

    public boolean isHaSidoLeido() {
        return haSidoLeido;
    }

    public void setHaSidoLeido(boolean haSidoLeido) {
        this.haSidoLeido = haSidoLeido;
    }
    //endregion

    //region Enum Plataforma
    private static final class Plataforma {
        public static final int YOUTUBE = 1;
        public static final int TWITCH = 2;
    }
    //endregion

    //region Propiedades estáticas
    private static final Material cementoRojo = Material.RED_CONCRETE;
    private static final Material cementoMorado = Material.PURPLE_CONCRETE;
    private static final Particle.DustOptions redstoneRoja = new Particle.DustOptions(Color.RED, 1);
    private static final Particle.DustOptions redstoneMorada = new Particle.DustOptions(Color.FUCHSIA, 1);
    private static final Random random = new Random();
    //endregion

    //region Métodos estáticos
    public static int leerMensaje(ArmorStand armorStand) {
        int mensajeId = 0;
        Material casco = armorStand.getHelmet().getType();
        if (casco == cementoRojo || casco == cementoMorado) {
        String nombreArmorStand = armorStand.getCustomName();
        if (nombreArmorStand != null && !nombreArmorStand.equals("")) {
            // En el nombre del armorstand se encuentra id_nombre_mensajeDelJugador
            String[] trozosMensaje = nombreArmorStand.split("_", 3);
            mensajeId = Integer.parseInt(trozosMensaje[0]);
            String plataformaNombre;
            String jugadorNombre;
            String mensaje = ChatColor.RESET + trozosMensaje[2];
            if (casco == cementoRojo) {
                plataformaNombre = ChatColor.DARK_RED + "[YOUTUBE]";
                jugadorNombre = ChatColor.RED + "[" + trozosMensaje[1] + "]";
            } else {
                plataformaNombre = ChatColor.DARK_PURPLE + "[TWITCH]";
                jugadorNombre = ChatColor.LIGHT_PURPLE + "[" + trozosMensaje[1] + "]";
            }
            Bukkit.broadcastMessage(plataformaNombre + " " + jugadorNombre + " : " + mensaje);
        }
        // Efectos de partículas y de rotura
            Particle.DustOptions redstoneRoja = new Particle.DustOptions(Color.RED, 1);
            Particle.DustOptions redstoneMorada = new Particle.DustOptions(Color.FUCHSIA, 1);
            if (casco == cementoRojo) {
                armorStand.getWorld().spawnParticle(Particle.REDSTONE, armorStand.getLocation().add(0, 1, 0), 50, 0.15d, 0.15d, 0.15d, 10, redstoneRoja);
            } else {
                armorStand.getWorld().spawnParticle(Particle.REDSTONE, armorStand.getLocation().add(0, 1, 0), 50, 0.15d, 0.15d, 0.15d, 10, redstoneMorada);
            }
        }
        armorStand.remove();
        return mensajeId;
    }

    public static void animarMensajes(Collection<ArmorStand> listaArmorStands) {
        for (ArmorStand armorStand : listaArmorStands) {
            Material casco = armorStand.getHelmet().getType();
            if (casco == cementoRojo || casco == cementoMorado) {
                Location localizacion = armorStand.getLocation();
                localizacion.setYaw(localizacion.getYaw() + 10);
                armorStand.teleport(localizacion);
                World mundo = armorStand.getWorld();
                if (casco == cementoRojo && random.nextBoolean()) { // 75% de probabilidad
                    mundo.spawnParticle(Particle.REDSTONE, localizacion.add(0, 1, 0), 1, 0.15d, 0.15d, 0.15d, 1, redstoneRoja);
                } else if (casco == cementoMorado && random.nextBoolean()) { // 75% de probabilidad
                    mundo.spawnParticle(Particle.REDSTONE, localizacion.add(0, 1, 0), 1, 0.15d, 0.15d, 0.15d, 1, redstoneMorada);
                }
            }
        }
    }

    public static boolean spawnearMensajeArmorStand(MensajeArmorStand mensajeArmorStand) {
        ArmorStand armorStand = null;
        try {
            World mundo = Bukkit.getWorld("world");
            Location localizacion = new Location(mundo, mensajeArmorStand.getX(), mensajeArmorStand.getY(), mensajeArmorStand.getZ());
            localizacion = localizacion.add(0.5d, 0.5d, 0.5d);
            ItemStack casco;
            if (mensajeArmorStand.getPlataformaId() == Plataforma.YOUTUBE) {
                casco = new ItemStack(Material.RED_CONCRETE);
            } else if (mensajeArmorStand.getPlataformaId() == Plataforma.TWITCH) {
                casco = new ItemStack(Material.PURPLE_CONCRETE);
            } else {
                return false;
            }
            armorStand = (ArmorStand) mundo.spawnEntity(localizacion, EntityType.ARMOR_STAND);
            armorStand.setHelmet(casco);
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            // En el nombre del armorstand se encontrará id_nombre_mensajeDelJugador
            armorStand.setCustomName(mensajeArmorStand.getId() + "_" + mensajeArmorStand.getUsuarioNombreFantasia() + "_" + mensajeArmorStand.getTexto());
            armorStand.setCustomNameVisible(false);
            // TODO Hacer que no le puedas quitar el sombrero al armorstand
            return true;
        } catch (Exception excepcion) { // TODO Que si la excepcion es del tipo de "Que esta fuera del mundo" no pinte mensaje de error
            if (armorStand != null) {
                armorStand.remove();
            }
            Bukkit.broadcastMessage("No se pudo colocar en: " + armorStand.getLocation().getX() + armorStand.getLocation().getY() + armorStand.getLocation().getZ());
            return false;
        }
    }
    //endregion

}
