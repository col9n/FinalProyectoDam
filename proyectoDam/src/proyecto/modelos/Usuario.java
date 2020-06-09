package proyecto.modelos;

public class Usuario {
    private int id_usuario;
    private String nombre_usuario;
    private String apellido1;
    private String apellido2;
    private String user;
    private int id_centro;


    public Usuario(int id_usuario, String nombre_usuario, String apellido1, String apellido2, String user, int id_centro) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.user = user;
        this.id_centro = id_centro;
    }

    public Usuario(int id_usuario, String nombre, String ape1, String ape2) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre;
        this.apellido1 = ape1;
        this.apellido2 = ape2;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId_centro() {
        return id_centro;
    }

    public void setId_centro(int id_centro) {
        this.id_centro = id_centro;
    }


    @Override
    public String toString() {
        return "Usuario " +user+" centro "+id_centro;
    }
}
