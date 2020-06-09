package proyecto.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import proyecto.Logica.Logica;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    private Stage stage = new Stage();
    private Timeline timeline = Logica.getInstance().getTimeline();




    /*------------- Zona del tab proveedor --------------*/
    @FXML
    private BorderPane borderPaneProved;

    @FXML
    private Button provedButAdd;

    @FXML
    private Button provedButVer;

    @FXML
    private Button provedButMod;

    @FXML
    private Button provedButEli;

    /*------------- Zona del tab producto --------------*/
    @FXML
    private BorderPane borderPaneProduc;

    @FXML
    private Button producButAdd;

    @FXML
    private Button producButVer;

    @FXML
    private Button producButMod;

    @FXML
    private Button producButEli;

    /*------------- Zona del tab stock --------------*/
    @FXML
    private BorderPane borderPaneStock;

    @FXML
    private Button stockButMod;

    @FXML
    private Button stockButVer;


    /*------------- Zona del tab traspasos --------------*/
    @FXML
    private BorderPane borderPaneTraspasos;

    @FXML
    private Button traspasosButAdd;

    @FXML
    private Button traspasosButVer;

    @FXML
    private Button traspasosButMod;

    @FXML
    private Button traspasosButEli;

    /*------------- Zona del tab centro --------------*/
    @FXML
    private BorderPane borderPaneCentros;

    @FXML
    private Button centroButAdd;

    @FXML
    private Button centroButVer;


    /*------------- Zona del tab usuarios --------------*/
    @FXML
    private BorderPane borderPaneUsuarios;

    @FXML
    private Button usuariosButAdd;

    @FXML
    private Button usuariosButVer;


    /* Zona de border pane pie de pagina */

    /* Zona de border pane pie de pagina izquierda */
    @FXML
    private Label labelBotIzq=new Label();

    /* Zona de border pane pie de pagina izquierda */
    @FXML
    private Label labelBotDer=new Label();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelBotIzq.setText(Logica.getInstance().getUsuario().toString());
        setFechaYHora();
        loadCenter("/proyecto/views/recursos/cargaPane",borderPaneProved);
        loadCenter("/proyecto/views/recursos/cargaPane",borderPaneProduc);
        loadCenter("/proyecto/views/recursos/cargaPane",borderPaneStock);
        loadCenter("/proyecto/views/recursos/cargaPane",borderPaneTraspasos);
        loadCenter("/proyecto/views/recursos/cargaPane",borderPaneCentros);
        loadCenter("/proyecto/views/recursos/cargaPane",borderPaneUsuarios);

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadCenter(String nombreFxml,BorderPane pane) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(nombreFxml + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.setCenter(root);
    }

    private void setFechaYHora() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            labelBotDer.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /*------------- Zona del tab proveedor --------------*/
    @FXML
    private void provedVer(ActionEvent event) { loadCenter("/proyecto/views/proveedores/VerProveedor",borderPaneProved);
    }

    @FXML
    private void provedAdd(ActionEvent event) { loadCenter("/proyecto/views/proveedores/AddProveedor",borderPaneProved);
    }

    @FXML
    private void provedMod(ActionEvent event) { loadCenter("/proyecto/views/proveedores/ModProveedor",borderPaneProved);
    }

    @FXML
    private void provedEli(ActionEvent event) { loadCenter("/proyecto/views/proveedores/EliProveedor",borderPaneProved);
    }

    /*------------- Zona del tab producto --------------*/
    @FXML
    public void producAdd(ActionEvent actionEvent) { loadCenter("/proyecto/views/productos/AddProducto",borderPaneProduc);
    }

    @FXML
    public void producVer(ActionEvent actionEvent) { loadCenter("/proyecto/views/productos/VerProducto",borderPaneProduc);
    }

    @FXML
    public void producMod(ActionEvent actionEvent) { loadCenter("/proyecto/views/productos/ModProducto",borderPaneProduc);
    }

    @FXML
    public void producEli(ActionEvent actionEvent) { loadCenter("/proyecto/views/productos/EliProducto",borderPaneProduc);
    }



    /*------------- Zona del tab stock --------------*/

    public void stockdVer(ActionEvent actionEvent) {loadCenter("/proyecto/views/stock/VerStock",borderPaneStock);
    }

    public void stockMod(ActionEvent actionEvent) {loadCenter("/proyecto/views/stock/ModStock",borderPaneStock);
    }

    /*------------- Zona del tab traspasos --------------*/

    public void traspasosdAdd(ActionEvent actionEvent) {loadCenter("/proyecto/views/traspasos/AddTraspasos",borderPaneTraspasos);
    }

    public void traspasosdVer(ActionEvent actionEvent) {loadCenter("/proyecto/views/traspasos/VerTraspasos",borderPaneTraspasos);
    }


    public void traspasosdEli(ActionEvent actionEvent) {loadCenter("/proyecto/views/traspasos/EliTraspasos",borderPaneTraspasos);
    }

    /*------------- Zona del tab centros --------------*/

    public void centrosdAdd(ActionEvent actionEvent) {loadCenter("/proyecto/views/centros/AddCentro",borderPaneCentros);
    }

    public void centrosVer(ActionEvent actionEvent) {loadCenter("/proyecto/views/centros/VerCentro",borderPaneCentros);
    }




    /*------------- Zona del tab usuarios --------------*/

    public void usuariosdAdd(ActionEvent actionEvent) {loadCenter("/proyecto/views/usuarios/AddUser",borderPaneUsuarios);
    }

    public void usuariosdVer(ActionEvent actionEvent) {loadCenter("/proyecto/views/usuarios/VerUser",borderPaneUsuarios);
    }


    public void configuracion(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proyecto/views/configuracion.fxml"));
            Parent root = fxmlLoader.load();
            ConfiguracionController configuracionController = (ConfiguracionController) fxmlLoader.getController();
            configuracionController.setStage(stage);
            stage.setResizable(false);
            stage.setTitle("Configuracíon ");
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
    }







}
