package InventoryManager.fxml;

import InventoryManager.classes.Inventory;
import InventoryManager.classes.Part;
import InventoryManager.classes.Product;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * This class controls both the product addition and modification pages of the application.
 * @author Noah Henning
 */
public class ProductController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    //private ObservableList<Part> relatedParts;
    private boolean modifyProduct;
    private Product tempProduct = new Product(99, "temp", 0.00, 0, 0, 99);

    @FXML
    private Button productCancelButton;

    @FXML
    private Text productTitle;

    @FXML
    private TextField productAllPartsSearch;

    @FXML
    private Button productAddPartButton;

    @FXML
    private TextField productName;

    @FXML
    private TextField productInv;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productMax;

    @FXML
    private TextField productMin;

    @FXML
    private Button productRemovePartButton;

    @FXML
    private Button productSaveButton;

    @FXML
    private TableView<Part> productAllPartsTable;

    @FXML
    private TableColumn<Part, Integer> allPartsID;

    @FXML
    private TableColumn<Part, String> allPartsName;

    @FXML
    private TableColumn<Part, Integer> allPartsStock;

    @FXML
    private TableColumn<Part, Double> allPartsCost;

    @FXML
    private TableView<Part> productPartsTable;

    @FXML
    private TableColumn<Part, Integer> productPartsId;

    @FXML
    private TableColumn<Part, String> productPartsName;

    @FXML
    private TableColumn<Part, Integer> productPartsStock;

    @FXML
    private TableColumn<Part, Double> productPartsCost;

    /** This method handles adding a part to a product. */
    @FXML
    void addPart(ActionEvent event) {
        if(productAllPartsTable.getSelectionModel().getSelectedItem() == null){
            showErrorAlert("Selection Error", "There was an error adding the Part to your Product.", "A part must be selected in order to add it to your product");
            return;
        }
        tempProduct.addAssociatedPart(productAllPartsTable.getSelectionModel().getSelectedItem());
        productPartsTable.setItems(tempProduct.getAssociatedParts());


    }

    /** This method handles canceling out of the product screen and returning to the main page. */
    @FXML
    void cancelProduct(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePane.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /** This method handles removing a part from a product.
     * RUNTIME ERROR - The remove function was referencing the wrong table resulting in not being able to delete parts from the associated parts table. This was solved by re-aligning the reference with the correct table.  */
    @FXML
    void removePart(ActionEvent event) {
        if(productPartsTable.getSelectionModel().getSelectedItem() == null){
            showErrorAlert("Selection Error", "There was an error removing the Part from your Product.", "A part must be selected in order to remove it from your product");
            return;
        }

        productPartsTable.getItems().remove(productPartsTable.getSelectionModel().getSelectedItem());


    }

    /** This method handles saving products and validating all inputs. */
    @FXML
    void saveProduct(ActionEvent event) throws IOException {
        //Input validation utilizing regex
        if(productName.getText().length() == 0){
            showErrorAlert("Input Error", "There appears to be an issue with Product Name.", "A product name must be given.");
            return;
        }
        else if(!Pattern.matches("\\d+", productPrice.getText()) && !Pattern.matches("\\d+\\.?\\d*", productPrice.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Price/Cost quantity.", "Price/Cost must be a number or decimal with a leading 0.");
            return;
        }
        else if(!Pattern.matches("\\d+", productInv.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Inventory quantity.", "Quantity must be a whole number.");
            return;
        }
        else if(!Pattern.matches("\\d+", productMax.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Min quantity.", "Quantity must be a whole number.");
            return;
        }
        else if(!Pattern.matches("\\d+", productMin.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Max quantity.", "Quantity must be a whole number.");
            return;
        }
        else if( Integer.parseInt(productMax.getText()) < Integer.parseInt(productMin.getText())){
            showErrorAlert("Input Error", "There appears to be an issue with Min and Max quantities.", "Max must be greater than or equal to Min.");
            return;
        }
        else if(Integer.parseInt(productInv.getText())> Integer.parseInt(productMax.getText()) || Integer.parseInt(productInv.getText())< Integer.parseInt(productMin.getText()) ){
            showErrorAlert("Input Error", "There appears to be an issue with Inventory Quantities.", "Inventory Quantity must be between Min Quantity and Max Quantity.");
            return;
        }
        else if(tempProduct.getAssociatedParts().size() < 1){
            showErrorAlert("Input Error", "There appears to be an issue with the Product's Parts.", "Products must have at least one related Part.");
            return;
        }
        Product p = new Product(
                Inventory.getCurrentProductID(),
                productName.getText(),
                Double.parseDouble(productPrice.getText()),
                Integer.parseInt(productInv.getText()),
                Integer.parseInt(productMin.getText()),
                Integer.parseInt(productMax.getText()));

        if(tempProduct.getAssociatedParts().size() > 0){
            for (int i = 0; i < tempProduct.getAssociatedParts().size(); i++) {
                System.out.println(tempProduct.getAssociatedParts().get(i).getName());
                p.addAssociatedPart(tempProduct.getAssociatedParts().get(i));
            }
        }

        if(!modifyProduct){
            Inventory.addProduct(p);
        }
        else{
            p.setId(tempProduct.getId());
            Inventory.updateProduct(Inventory.getAllProducts().indexOf(tempProduct), p);
        }
        root = FXMLLoader.load(getClass().getResource("HomePane.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /** This method switches the scene from the default product addition to product modification.
     * RUNTIME ERROR - This method was not correctly setting the title of the pane to "Modify Product" and was instead changing the text of a text field instead. This was fixed by re-aligning the reference with the correct fx:id
     * @param p the product to be modified
     */
    @FXML
    void setModify(Product p){
        modifyProduct =  true;
        productTitle.setText("Modify Product");
        tempProduct = p;
        productPartsTable.setItems(tempProduct.getAssociatedParts());
        productInv.setText(Integer.toString(p.getStock()));
        productMin.setText(Integer.toString(p.getMin()));
        productMax.setText(Integer.toString(p.getMax()));
        productName.setText(p.getName());
        productPrice.setText(Double.toString(p.getPrice()));

    }

    /** This method overrides the initialization function and allows data to populate in the table views correctly. */
    @Override
    public void initialize(URL url, ResourceBundle bundle){

        allPartsID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allPartsCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        allPartsStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productAllPartsTable.setItems(Inventory.getAllParts());

        productPartsId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productPartsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productPartsCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productPartsStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPartsTable.setItems(tempProduct.getAssociatedParts());

    }

    /**
     * This method handles sending error messages to the user.
     * @param title the title of the error message to display.
     * @param header the header of the error message to display.
     * @param content the main contents of the error message to display.
     */
    private void showErrorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /** This method handles searching for parts as the user types in the search box.  */
    @FXML
    void searchProduct(KeyEvent event) {
        ObservableList<Part> returnedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        String query = productAllPartsSearch.getText();

        if(query.length() == 0){
            productAllPartsTable.setPlaceholder(new Label("Table has no data."));
            productAllPartsTable.setItems(Inventory.getAllParts());
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
        if(returnedParts.size() == 0) productAllPartsTable.setPlaceholder(new Label("No results for search query."));
        productAllPartsTable.setItems(returnedParts);

    }

}
