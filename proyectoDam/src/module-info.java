module proyectoDam {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires java.sql;
    requires controlsfx;
    requires jasperreports;


    exports proyecto;
    exports proyecto.controllers;
    exports proyecto.Logica;
    exports proyecto.modelos.stock;
    exports proyecto.modelos.proveedores;
    exports proyecto.modelos.productos;
    exports proyecto.modelos.traspasos;
    exports proyecto.modelos;
    exports proyecto.util;




    opens proyecto.controllers to javafx.fxml;
    opens proyecto.util to javafx.fxml;
    opens proyecto.controllers.proveedores to javafx.fxml;
    opens proyecto.controllers.productos to javafx.fxml;
    opens proyecto.controllers.stock to javafx.fxml;
    opens proyecto.controllers.traspasos to javafx.fxml;
    opens proyecto.controllers.usuarios to javafx.fxml;
    opens proyecto.controllers.centros to javafx.fxml;


}