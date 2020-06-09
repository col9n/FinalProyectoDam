package proyecto.util;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.File;
import java.util.List;

public class Util {
    public static void alertaShow(String titulo, String texto, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public static Alert alertaGet(String titulo, String texto, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        return alert;
    }

    public static void activacionBoton(TextField textField, TextField textField1, Button button){
       ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(textField, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        textField.setPromptText("No puede estar vacio");
        validationSupport.registerValidator(textField1, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        textField1.setPromptText("No puede estar vacio");
        button.disableProperty().bind(validationSupport.invalidProperty());

    }

    public static void activacionBotonComboBoxProduct(TextField textField, ComboBox textField1, Button button){
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(textField, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        textField.setPromptText("No puede estar vacio");
        validationSupport.registerValidator(textField1, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        textField1.setPromptText("No puede estar vacio");
        button.disableProperty().bind(validationSupport.invalidProperty());

    }

    public static void  activacionBoton(TextField userEmpleado,PasswordField pass,PasswordField passRep,TextField nombreEmpleado,TextField primerApellido,TextField segundoApellido,ComboBox centroEmpleado,Button guardarProducto){
        ValidationSupport validationSupport = new ValidationSupport();

        validationSupport.registerValidator(userEmpleado, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        userEmpleado.setPromptText("No puede estar vacio");

        validationSupport.registerValidator(nombreEmpleado, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        nombreEmpleado.setPromptText("No puede estar vacio");

        validationSupport.registerValidator(primerApellido, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        primerApellido.setPromptText("No puede estar vacio");

        validationSupport.registerValidator(segundoApellido, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        segundoApellido.setPromptText("No puede estar vacio");

        validationSupport.registerValidator(centroEmpleado, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        centroEmpleado.setPromptText("No puede estar vacio");

        validationSupport.registerValidator(pass, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        pass.setPromptText("No puede estar vacio");

        validationSupport.registerValidator(passRep, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        passRep.setPromptText("No puede estar vacio");

        guardarProducto.disableProperty().bind(validationSupport.invalidProperty());
    }

    public static boolean  passEquals(String pass,String pass1){
        if(!pass.equals(pass1))
        {
            alertaShow("Contraseña diferentes","Revise las contraseñas", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }


    public static void activacionBotonComboBoxProduct(TextField textField, Button button){
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(textField, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        textField.setPromptText("No puede estar vacio");
        button.disableProperty().bind(validationSupport.invalidProperty());

    }

    public static void activacionBotonConfiguracion(TextField textField,TextField textField1,TextField textField2, Button button){
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(textField, Validator.createEmptyValidator("Este campo no puede estar vacío"));

        validationSupport.registerValidator(textField1, Validator.createEmptyValidator("Este campo no puede estar vacío"));

        validationSupport.registerValidator(textField2, Validator.createEmptyValidator("Este campo no puede estar vacío"));

        button.disableProperty().bind(validationSupport.invalidProperty());

    }

    public static void activacionBotonComboBoxTraspasoAdd(TextField textField, ComboBox combobox,ComboBox combobox1, Button button){
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(textField, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        textField.setPromptText("No puede estar vacio");
        validationSupport.registerValidator(combobox, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        combobox.setPromptText("No puede estar vacio");
        validationSupport.registerValidator(combobox1, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        combobox1.setPromptText("No puede estar vacio");
        button.disableProperty().bind(validationSupport.invalidProperty());
    }

    public static void activacionBotonComboBoxTraspasoEli(TableView tableView, Button button){
        if(tableView.getItems().size()<=0)
            button.setDisable(true);
        else
            button.setDisable(false);
    }


    public static void enableButtonListaContine(List lista, Button button){
        button.setDisable(false);
        if(lista.size()==0)
            button.setDisable(true);
    }

    public static String stringToMayus(String palabra){
        return palabra.toUpperCase();
    }

    public static File getFile() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(null);
    }
}
