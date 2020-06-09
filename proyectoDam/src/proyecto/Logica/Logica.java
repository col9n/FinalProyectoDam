package proyecto.Logica;

import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import proyecto.modelos.Usuario;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Logica {
    private static Logica INSTANCE = null;
    private Usuario usuario;
    private Timeline timeline=new Timeline();
    Database database=new Database();


    private Logica() {


    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }


    public static void setINSTANCE(Logica INSTANCE) {
        Logica.INSTANCE = INSTANCE;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void pararFecha() {
        timeline.stop();
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }




}
