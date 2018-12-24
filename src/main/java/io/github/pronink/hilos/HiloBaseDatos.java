package io.github.pronink.hilos;

import com.google.gson.Gson;
import io.github.pronink.entidades.MensajeArmorStand;
import org.bukkit.Bukkit;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiloBaseDatos extends Thread {

    private boolean hiloEjecutandose;
    private MariaDbPoolDataSource pool;
    private DatosCompartidos datosCompartidos;

    public HiloBaseDatos(DatosCompartidos datosCompartidos) {
        this.datosCompartidos = datosCompartidos;
        this.hiloEjecutandose = true;
        try {
            pool = new MariaDbPoolDataSource("jdbc:mariadb://localhost/mundo_susurrante?user=usuarioaplicacion&password=12344321noesunabuenapassword&maxPoolSize=10");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (hiloEjecutandose) {
            sincronizarBaseDeDatos();
            dormir(2000);
        }
    }

    private ResultSet ejecutarSql(String query) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            Statement stmt = connection.createStatement();
            connection.close();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            connection.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    private void dormir(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sincronizarBaseDeDatos(){
        // Marca como leidos todos los mensajes que hayan sido encontrados en el objeto compartido ListaMensajesLeidos
        try {
            String jsonArrayMensajesLeidos = new Gson().toJson(datosCompartidos.getListaMensajesLeidos());
            ejecutarSql("CALL marcar_mensajes_leidos('" + jsonArrayMensajesLeidos + "');");
            if (!jsonArrayMensajesLeidos.equals("[]")) {
                Bukkit.broadcastMessage("Leidos: " + jsonArrayMensajesLeidos);
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }

        // Agrega al objeto compartido MensajesArmorStands todos los mensajes nuevos no spawneados traidos de la base de datos
        try {
            ResultSet rs = ejecutarSql("CALL obtener_mensajes_no_spawneados()");
            while (rs.next()) {
                MensajeArmorStand mensajeArmorStand = new MensajeArmorStand();
                mensajeArmorStand.setId(rs.getInt("id"));
                mensajeArmorStand.setPlataformaId(rs.getInt("plataforma_id"));
                mensajeArmorStand.setEsModerador(rs.getBoolean("es_moderador"));
                mensajeArmorStand.setEsSuscriptor(rs.getBoolean("es_suscriptor"));
                mensajeArmorStand.setHaAparecido(rs.getBoolean("ha_aparecido"));
                mensajeArmorStand.setHaSidoLeido(rs.getBoolean("ha_sido_leido"));
                mensajeArmorStand.setTexto(rs.getString("texto"));
                mensajeArmorStand.setUsuarioId(rs.getString("usuario_id"));
                mensajeArmorStand.setUsuarioNombreFantasia(rs.getString("nombre_fantasia"));
                mensajeArmorStand.setX(rs.getInt("coordenada_x"));
                mensajeArmorStand.setY(rs.getInt("coordenada_y"));
                mensajeArmorStand.setZ(rs.getInt("coordenada_z"));
                datosCompartidos.agregarMensajeArmorStand(mensajeArmorStand);
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    public void destruirHilo() {
        pool.close();
        hiloEjecutandose = false;
    }
}
