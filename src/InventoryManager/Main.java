package InventoryManager;
//Java Docs are located at ~/HenningInventoryManager/JavaDocs
import InventoryManager.classes.InHouse;
import InventoryManager.classes.Inventory;
import InventoryManager.classes.Outsourced;
import InventoryManager.classes.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class creates an app for inventory management of parts and products.
 * FUTURE ENHANCEMENT - Implement connection to a central database to enable collaboration.
 * FUTURE ENHANCEMENT - Implement inline validation for the input text fields.
 * @author Noah Henning
 *
 */

public class Main extends Application {

    /** This method sets the main stage of the application. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/HomePane.fxml"));
        primaryStage.setTitle("Henning Inventory Manager");
        primaryStage.setScene(new Scene(root, 900, 400));
        primaryStage.show();
    }

    /** This is the main method. This is where artificial data is injected into the application and the application is launched. */
    public static void main(String[] args) {
        Inventory.addPart(new InHouse(0, "screw", 5.0, 9, 0, 10, 98));
        Inventory.addPart(new InHouse(1, "nut", 14.0, 37, 0, 85, 98));
        Inventory.addPart(new Outsourced(2, "transistor", 50.0, 90, 0, 100, "Apple"));
        Inventory.addPart(new Outsourced(3, "bad screw", 1.0, 7, 5, 30, "google"));
        Inventory.setCurrentPartID(4);

        Inventory.addProduct(new Product(0, "Screw n nut", 30.0, 9, 0, 100));
        Inventory.addProduct(new Product(1, "bad Screw n nut", 15.0, 7, 0, 100));
        Inventory.addProduct(new Product(2, "nutsistor", 300.0, 93, 0, 750));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(2));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(3));
        Inventory.lookupProduct(0).addAssociatedPart(Inventory.lookupPart(0));
        Inventory.lookupProduct(0).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.setCurrentProductID(3);

        launch(args);
    }
}
