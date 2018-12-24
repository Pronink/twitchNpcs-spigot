package io.github.pronink.entidades;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.EulerAngle;

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
    private static final Material cementoRojoMaterial = Material.RED_CONCRETE;
    private static final Material cementoMoradoMaterial = Material.MAGENTA_CONCRETE;
    private static final MaterialData cementoRojoParticula = new MaterialData(Material.RED_CONCRETE, (byte)14);
    private static final MaterialData cementoMoradoParticula = new MaterialData(Material.MAGENTA_CONCRETE, (byte)2);
    private static final Particle.DustOptions redstoneRojaParticula = new Particle.DustOptions(Color.RED, 1);
    private static final Particle.DustOptions redstoneMoradaParticula = new Particle.DustOptions(Color.FUCHSIA, 1);
    private static final ItemStack cementoRojoItem = new ItemStack(Material.RED_CONCRETE);
    private static final ItemStack cementoMoradoItem = new ItemStack(Material.MAGENTA_CONCRETE);
    private static final Random random = new Random();
    //endregion

    //region Métodos estáticos
    public static int leerMensaje(ArmorStand armorStand) {
        int mensajeId = 0;
        Material casco = armorStand.getHelmet().getType();
        if (casco == cementoRojoMaterial || casco == cementoMoradoMaterial) {
            String nombreArmorStand = armorStand.getCustomName();
            if (nombreArmorStand != null && !nombreArmorStand.equals("")) {
                // En el nombre del armorstand se encuentra id_nombre_mensajeDelJugador
                String[] trozosMensaje = nombreArmorStand.split("_", 3);
                mensajeId = Integer.parseInt(trozosMensaje[0]);
                String plataformaNombre;
                String jugadorNombre;
                String mensaje = ChatColor.RESET + trozosMensaje[2];
                if (casco == cementoRojoMaterial) {
                    plataformaNombre = ChatColor.DARK_RED + "[YOUTUBE]";
                    jugadorNombre = ChatColor.RED + "[" + trozosMensaje[1] + "]";
                } else {
                    plataformaNombre = ChatColor.DARK_PURPLE + "[TWITCH]";
                    jugadorNombre = ChatColor.LIGHT_PURPLE + "[" + trozosMensaje[1] + "]";
                }
                Bukkit.broadcastMessage(plataformaNombre + " " + jugadorNombre + " : " + mensaje);
            }
            Location localizacion = armorStand.getLocation().add(0, 1, 0);
            armorStand.getWorld().playSound(localizacion, obtenerSonidoAleatorioVillager(), 5, 1);
            // Efectos de partículas y de rotura
            if (casco == cementoRojoMaterial) {
                armorStand.getWorld().spawnParticle(Particle.REDSTONE, localizacion, 50, 0.15d, 0.15d, 0.15d, 10, redstoneRojaParticula);
                armorStand.getWorld().spawnParticle(Particle.LEGACY_BLOCK_CRACK, localizacion, 20, 0.15d, 0.15d, 0.15d, cementoRojoParticula);
            } else {
                armorStand.getWorld().spawnParticle(Particle.REDSTONE, localizacion, 50, 0.15d, 0.15d, 0.15d, 10, redstoneMoradaParticula);
                armorStand.getWorld().spawnParticle(Particle.LEGACY_BLOCK_CRACK, localizacion, 20, 0.15d, 0.15d, 0.15d, cementoMoradoParticula);
            }
        }
        armorStand.remove();
        return mensajeId;
    }

    public static void animarMensajes(Collection<ArmorStand> listaArmorStands) {
        for (ArmorStand armorStand : listaArmorStands) {
            Material casco = armorStand.getHelmet().getType();
            if (casco == cementoRojoMaterial || casco == cementoMoradoMaterial) {
                Location localizacion = armorStand.getLocation();
                localizacion.setYaw(localizacion.getYaw() + random.nextInt(1) + 10); // Entre 10 y 11
                armorStand.teleport(localizacion);
                World mundo = armorStand.getWorld();
                localizacion.add(0, 1, 0);
                /*if (random.nextBoolean()){
                    mundo.spawnParticle(Particle.END_ROD, localizacion, 1, 0d, 0d, 0d, 0.025d);
                }*/
                if (casco == cementoRojoMaterial) {
                    mundo.spawnParticle(Particle.REDSTONE, localizacion, 5, 0.15d, 0.15d, 0.15d, 1, redstoneRojaParticula);
                } else {
                    mundo.spawnParticle(Particle.REDSTONE, localizacion, 5, 0.15d, 0.15d, 0.15d, 1, redstoneMoradaParticula);
                }
            }
        }
    }

    public static boolean spawnearMensajeArmorStand(MensajeArmorStand mensajeArmorStand) {
        ArmorStand armorStand = null;
        World mundo = Bukkit.getWorld("world");
        Location localizacion = new Location(mundo, mensajeArmorStand.getX(), mensajeArmorStand.getY(), mensajeArmorStand.getZ());
        try {
            // Centro la localización en el centro del bloque
            localizacion = localizacion.add(0.5d, 1d, 0.5d);
            // Mientras no sea aire no paro de subir la localización
            int i = 0;
            while (!esAire(localizacion)) {
                Bukkit.broadcastMessage("Subiendo.." + i++ + " " + !esAire(localizacion));
                localizacion = localizacion.add(0d, 1d, 0d);
            }
            // Mientras no haya tierra debajo no paro de bajar la localización
            int j = 0;
            while (esAire(localizacion.add(0d, -1d, 0d))) {
                Bukkit.broadcastMessage("Bajando.." + j++ + " " + !esAire(localizacion));
            }
            ItemStack casco;
            if (mensajeArmorStand.getPlataformaId() == Plataforma.YOUTUBE) {
                casco = cementoRojoItem;
            } else if (mensajeArmorStand.getPlataformaId() == Plataforma.TWITCH) {
                casco = cementoMoradoItem;
            } else {
                return false;
            }
            armorStand = (ArmorStand) mundo.spawnEntity(localizacion.add(0d, 1d, 0d), EntityType.ARMOR_STAND);
            armorStand.setHelmet(casco);
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            // En el nombre del armorstand se encontrará id_nombre_mensajeDelJugador
            armorStand.setCustomName(mensajeArmorStand.getId() + "_" + mensajeArmorStand.getUsuarioNombreFantasia() + "_" + mensajeArmorStand.getTexto());
            armorStand.setCustomNameVisible(false);
            //armorStand.setGlowing(true);
            armorStand.setBasePlate(false);
            armorStand.setLeftLegPose(new EulerAngle(3.14f, 0, 0));
            armorStand.setRightLegPose(new EulerAngle(3.14f, 0, 0));

            armorStand.getWorld().playSound(localizacion.add(0d, 0.5d, 0d), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 10, 2);
            armorStand.getWorld().spawnParticle(Particle.END_ROD, localizacion.add(0d, 0.5d, 0d), 50, 0.15d, 0.15d, 0.15d, 0.1d);
            localizacion.add(0d, -0.5d, 0d);

            // TODO Hacer que no le puedas quitar el sombrero al armorstand
            return true;
        } catch (Exception excepcion) { // TODO Que si la excepcion es del tipo de "Que esta fuera del mundo" no pinte mensaje de error aunque parece que este error ya NUNCA se da
            if (armorStand != null) {
                armorStand.remove();
            }
            Bukkit.broadcastMessage("No se pudo colocar en: " + localizacion.getX() + localizacion.getY() + localizacion.getZ() + " con id: " + mensajeArmorStand.getId());
            return false;
        }
    }

    private static boolean esAire(Location localizacion) {
        Material material = localizacion.getBlock().getType();
        return (material == Material.AIR || material == Material.CAVE_AIR);
    }

    private static Sound obtenerSonidoAleatorioVillager() {
        switch (random.nextInt(4)) {
            case 0:
                return Sound.ENTITY_VILLAGER_AMBIENT;
            case 1:
                return Sound.ENTITY_VILLAGER_YES;
            case 2:
                return Sound.ENTITY_VILLAGER_TRADE;
            case 3:
                return Sound.ENTITY_VILLAGER_NO;
        }
        return Sound.ENTITY_VILLAGER_AMBIENT;
    }
    //endregion

}
