package InventoryManager.fxml;

import InventoryManager.classes.Part;
import InventoryManager.classes.Product;
import InventoryManager.classes.Inventory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class controls the main FXML page of the application.
 * @author Noah Henning
 */
public class HomePaneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<Part> partsDisplayField;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partStockColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TableView<Product> productsDisplayField;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productStockColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TextField partsSearchField;

    @FXML
    private TextField productsSearchField;

    @FXML
    private Button partsAddButton;

    @FXML
    private Button partsModifyButton;

    @FXML
    private Button partsDeleteButton;

    @FXML
    private Button productsAddButton;

    @FXML
    private Button productsModifyButton;

    @FXML
    private Button productsDeleteButton;

    /** This method overrides the initialize function and sets up the table views allowing them to populate with data. */
    @Override
    public void initialize(URL location, ResourceBundle bundle){

        partsDisplayField.setItems(Inventory.getAllParts());
        productsDisplayField.setItems(Inventory.getAllProducts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));


    }

    /** This method handles switching to the scene where parts can be added. */
    @FXML
    void addPart(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("PartGUI.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method handles switching to the scene where parts can be modified. */
    @FXML
    void modifyPart(ActionEvent actionEvent) throws IOException {
        if(partsDisplayField.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PartGUI.fxml"));
            root = loader.load();
            PartController pc = loader.getController();
            pc.setModify(partsDisplayField.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("There was an issue loading the modify pane.");
            alert.setContentText("Please select a Part from the table in order to open the modify pane.");

            alert.showAndWait();
        }
    }

    /** This method handles switching to the scene where products can be added. */
    @FXML
    void addProduct(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProductGUI.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method handles switching to the scene where products can be modified. */
    @FXML
    void modifyProduct(ActionEvent actionEvent) throws IOException {
        if(productsDisplayField.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductGUI.fxml"));
            root = loader.load();
            ProductController pc = loader.getController();
            pc.setModify(productsDisplayField.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("There was an issue loading the modify pane.");
            alert.setContentText("Please select a Product from the table in order to open the modify pane.");

            alert.showAndWait();
        }
    }

    /** This method handles deleting parts from the inventory. It will not allow a part to be deleted if it is related to a product. */
    @FXML
    void deletePart(ActionEvent actionEvent) {
        if(partsDisplayField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("There was an issue deleting a part.");
            alert.setContentText("A part must be selected in order to delete it.");

            alert.showAndWait();
            return;
        }

        for (int i = 0; i < Inventory.getAllProducts().size() ; i++) {
            for (int j = 0; j < Inventory.getAllProducts().get(i).getAssociatedParts().size(); j++) {
                ObservableList<Part> associatedParts = Inventory.getAllProducts().get(i).getAssociatedParts();
                if(associatedParts.get(j).getId() == partsDisplayField.getSelectionModel().getSelectedItem().getId()){
                    String name = Inventory.getAllProducts().get(i).getName();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Dependency Error");
                    alert.setHeaderText("There was an issue deleting a part.");
                    alert.setContentText("Product " + name + " requires this part. Please remove the part from this product before deleting.");

                    alert.showAndWait();
                    return;
                }

            }
//            if(Inventory.getAllProducts().get(i).getAssociatedParts().contains(partsDisplayField.getSelectionModel().getSelectedItem())){
//                String name = Inventory.getAllProducts().get(i).getName();
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Dependency Error");
//                alert.setHeaderText("There was an issue deleting a part.");
//                alert.setContentText("Product " + name + " requires this part. Please remove the part from this product before deleting.");
//
//                alert.showAndWait();
//                return;
//            }

        }
        if(!Inventory.deletePart(partsDisplayField.getSelectionModel().getSelectedItem())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setHeaderText("There was an issue deleting a part.");
            alert.setContentText("Selected Part does not exist.");
        }
        partsDisplayField.setItems(Inventory.getAllParts());


    }

    /** This method handles the deletion of products */
    @FXML
    void deleteProduct(ActionEvent actionEvent) {
        if(productsDisplayField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("There was an issue deleting a product.");
            alert.setContentText("A product must be selected in order to delete it.");

            alert.showAndWait();
            return;
        }
        if(!Inventory.deleteProduct(productsDisplayField.getSelectionModel().getSelectedItem())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setHeaderText("There was an issue deleting a part.");
            alert.setContentText("Selected Part does not exist.");
        }
        partsDisplayField.setItems(Inventory.getAllParts());

    }

    /** This method handles the closing of the entire program. */
    @FXML
    void closeProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

    /** This method handles searching parts in the table as the user types in the search field. */
    @FXML
    void searchParts(KeyEvent event) {
        ObservableList<Part> returnedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        String query = partsSearchField.getText();

        if(query.length() == 0){
            partsDisplayField.setPlaceholder(new Label("Table has no data."));
            partsDisplayField.setItems(Inventory.getAllParts());
            return;
        }

        for (int i = 0; i < allParts.size(); i++) {
            if(Integer.toString(allParts.get(i).getId()).toLowerCase().contains(query.toLowerCase())){
                returnedParts.add(allParts.get(i));
            }
            else if(allParts.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                returnedParts.add(allParts.get(i));
            }
        }
        if(returnedParts.size() == 0) partsDisplayField.setPlaceholder(new Label("No results for search query."));
        partsDisplayField.setItems(returnedParts);

    }

    /** This method handles searching products in the table as the user types in the search field. */
    @FXML
    void searchProducts(KeyEvent event) {
        ObservableList<Product> returnedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        String query = productsSearchField.getText();

        if(query.length() == 0){
            productsDisplayField.setItems(Inventory.getAllProducts());
            productsDisplayField.setPlaceholder(new Label("Table has no data."));
            return;
        }

        for (int i = 0; i < allProducts.size(); i++) {
            if(Integer.toString(allProducts.get(i).getId()).toLowerCase().contains(query.toLowerCase())){
                returnedProducts.add(allProducts.get(i));
            }
            else if(allProducts.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                returnedProducts.add(allProducts.get(i));
            }
        }
        productsDisplayField.setItems(returnedProducts);
        if(returnedProducts.size() == 0) productsDisplayField.setPlaceholder(new Label("No results for search query."));

    }


}
