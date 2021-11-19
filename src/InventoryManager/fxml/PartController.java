package InventoryManager.fxml;

import InventoryManager.classes.InHouse;
import InventoryManager.classes.Inventory;
import InventoryManager.classes.Outsourced;
import InventoryManager.classes.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.regex.*;

import java.io.IOException;
/**
 * This class controls both the part addition and modification pages of the application.
 * @author Noah Henning
 */
public class PartController{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private int modifyPartID;

    @FXML
    private boolean editPart = false;

    @FXML
    private boolean inHouse;

    @FXML
    private Text partTitle;

    @FXML
    private TextField partID;

    @FXML
    private RadioButton partInHouse;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton partOutsourced;

    @FXML
    private TextField partMinInv;

    @FXML
    private TextField partMaxInv;

    @FXML
    private TextField partPrice;

    @FXML
    private TextField partCurrentInv;

    @FXML
    private TextField partName;

    @FXML
    private Button partSave;

    @FXML
    private Button partCancel;

    @FXML
    private Text partCreationLoacationLabel;

    @FXML
    private TextField partCreationLocationText;

    /** This method handles canceling and returning to the main window. */
    @FXML
    void partCancel(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePane.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /** This method handles saving parts and validating input. */
    @FXML
    void partSave(ActionEvent event) throws IOException {
        //Input validation utilizing regex
        if(!partInHouse.isSelected() && !partOutsourced.isSelected()){
            showErrorAlert("Selection Error", "There appears to be an issue with the selection of the type of part.", "Please select In House or Outsourced.");
            return;
        }
        else if(partName.getText().length() == 0){
            showErrorAlert("Input Error", "There appears to be an issue with Part Name.", "A part name must be given.");
            return;
        }
        else if(!Pattern.matches("\\d+", partCurrentInv.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Inventory quantity.", "Quantity must be a whole number.");
            return;
        }
        else if(!Pattern.matches("\\d+", partPrice.getText()) && !Pattern.matches("\\d+\\.?\\d*", partPrice.getText())){
            showErrorAlert("Input Error", "There appears to be an issue withPrice / Cost quantity.", "Quantity must be a number or decimal with a leading 0.");
            return;
        }
        else if(!Pattern.matches("\\d+", partMaxInv.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Min quantity.", "Quantity must be a whole number.");
            return;
        }
        else if(!Pattern.matches("\\d+", partMinInv.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Max quantity.", "Quantity must be a whole number.");
            return;
        }
        else if( Integer.parseInt(partMaxInv.getText()) < Integer.parseInt(partMinInv.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Min and Max quantities.", "Max must be greater than or equal to Min.");
            return;
        }
        else if(Integer.parseInt(partCurrentInv.getText())> Integer.parseInt(partMaxInv.getText()) || Integer.parseInt(partCurrentInv.getText())< Integer.parseInt(partMinInv.getText()) ){
            showErrorAlert("Input Error", "There appears to be an issue with Inventory Quantities.", "Inventory Quantity must be between Min Quantity and Max Quantity.");
            return;
        }
        else if(inHouse && !Pattern.matches("\\d+", partCreationLocationText.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Machine ID.", "ID must be a whole number.");
            return;
        }
        else if(!inHouse && partCreationLocationText.getText().length() == 0){
            showErrorAlert("Input Error", "There appears to be an issue with the Part Sourcing Company Name.", "A company name must be supplied for an outsourced part.");
            return;
        }


        //Save Function for new part
        if(!editPart) {
            if (inHouse) {
                Inventory.addPart(new InHouse(
                        Inventory.getCurrentPartID(),
                        partName.getText(),
                        Double.parseDouble(partPrice.getText()),
                        Integer.parseInt(partCurrentInv.getText()),
                        Integer.parseInt(partMinInv.getText()),
                        Integer.parseInt(partMaxInv.getText()),
                        Integer.parseInt(partCreationLocationText.getText())));
            }

            else Inventory.addPart(new Outsourced(
                    Inventory.getCurrentPartID(),
                    partName.getText(),
                    Double.parseDouble(partPrice.getText()),
                    Integer.parseInt(partCurrentInv.getText()),
                    Integer.parseInt(partMinInv.getText()),
                    Integer.parseInt(partMaxInv.getText()),
                    partCreationLocationText.getText()));
        }
        //Save Function for modified part
        else{
            if (inHouse) {
                Inventory.updatePart(modifyPartID, new InHouse(
                        Inventory.getCurrentPartID(),
                        partName.getText(),
                        Double.parseDouble(partPrice.getText()),
                        Integer.parseInt(partCurrentInv.getText()),
                        Integer.parseInt(partMinInv.getText()),
                        Integer.parseInt(partMaxInv.getText()),
                        Integer.parseInt(partCreationLocationText.getText())));
            }
            else Inventory.updatePart(modifyPartID, new Outsourced(
                    Inventory.getCurrentPartID(),
                    partName.getText(),
                    Double.parseDouble(partPrice.getText()),
                    Integer.parseInt(partCurrentInv.getText()),
                    Integer.parseInt(partMinInv.getText()),
                    Integer.parseInt(partMaxInv.getText()),
                    partCreationLocationText.getText()));

        }
        root = FXMLLoader.load(getClass().getResource("HomePane.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }

    /** This method handles what should happen when the inHouse radio button is selected. */
    @FXML
    void inHouseSelected(ActionEvent event) {
        inHouse = true;
        partCreationLoacationLabel.setText("Machine ID");
        partCreationLocationText.setPromptText("Machine ID Number");
        partCreationLocationText.setFocusTraversable(false);
    }

    /** This method handles what should happen when the outsourced radio button is selected. */
    @FXML
    void outsourcedSelected(ActionEvent event) {
        inHouse = false;
        partCreationLoacationLabel.setText("Company Name");
        partCreationLocationText.setPromptText("Name of Company");
        partCreationLocationText.setFocusTraversable(false);
    }

    /**
     * This method handles showing errors to the user
     * @param title the title to display on the error popup
     * @param header the header to display on the error popup
     * @param content the content to display on the error popup
     */
    private void showErrorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /** This method handles changing the window from adding a new part to modifying an existing one.
     * @param p the part to be modified
     */
    @FXML
    void setModify(Part p){
        partTitle.setText("Modify Part");
        editPart = true;
        modifyPartID = p.getId();
        if(p instanceof Outsourced){
            outsourcedSelected(null);
            partCreationLocationText.setText(((Outsourced) p).getCompanyName());
            partOutsourced.setSelected(true);
        }
        else {
            inHouseSelected(null);
            partCreationLocationText.setText(Integer.toString(((InHouse)p).getMachineId()));
            partInHouse.setSelected(true);
        }
        partName.setText(p.getName());
        partCurrentInv.setText(Integer.toString(p.getStock()));
        partMaxInv.setText(Integer.toString(p.getMax()));
        partMinInv.setText(Integer.toString(p.getMin()));
        partPrice.setText(Double.toString(p.getPrice()));
        modifyPartID = p.getId();
    }


}
