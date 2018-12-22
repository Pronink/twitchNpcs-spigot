package io.github.pronink;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;

public class Informacion {
    public static void pintarInformacion(Collection<Player> listaJugadoresOnline) {
        for (Player jugador : listaJugadoresOnline) {
            int x = jugador.getLocation().getBlockX();
            int y = jugador.getLocation().getBlockY();
            int z = jugador.getLocation().getBlockZ();

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("Coordenadas", "dummy", ""); //Nombre-Tipo
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(" ");

            Score tutorial_1 = objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "INTERACCIÓN");
            Score tutorial_2 = objective.getScore("Inserta tu comentario");
            Score tutorial_3 = objective.getScore("desde" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + " twitch " + ChatColor.RESET + "o" + ChatColor.RED + ChatColor.BOLD + " youtube");
            Score tutorial_4 = objective.getScore("y aparecerán dentro");
            Score tutorial_5 = objective.getScore("del mundo de Minecraft");
            Score separador_1 = objective.getScore("  ");
            Score tituloFormato = objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "FORMATO DEL MENSAJE");
            Score ejemplo = objective.getScore(ChatColor.GREEN + "x y z " + ChatColor.GOLD + "Tu mensaje :-)");
            Score separador_2 = objective.getScore(" ");
            Score tituloCoordenadas = objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "COORDENADAS ACTUALES:");
            Score score_x = objective.getScore(ChatColor.GREEN + "x" + ChatColor.DARK_GREEN + " = " + ChatColor.GOLD + x);
            Score score_y = objective.getScore(ChatColor.GREEN + "y" + ChatColor.DARK_GREEN + " = " + ChatColor.GOLD + y);
            Score score_z = objective.getScore(ChatColor.GREEN + "z" + ChatColor.DARK_GREEN + " = " + ChatColor.GOLD + z);

            tutorial_1.setScore(12);
            tutorial_2.setScore(11);
            tutorial_3.setScore(10);
            tutorial_4.setScore(9);
            tutorial_5.setScore(8);
            separador_1.setScore(7);
            tituloFormato.setScore(6);
            ejemplo.setScore(5);
            separador_2.setScore(4);
            tituloCoordenadas.setScore(3);
            score_x.setScore(2);
            score_y.setScore(1);
            score_z.setScore(0);

            jugador.setScoreboard(scoreboard);
        }

    }
}
