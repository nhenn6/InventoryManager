package InventoryManager.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Noah Henning
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int currentPartID = 0;
    private static int currentProductID = 0;


    /**
     * @return the currently available Part ID that has been auto-generated.
     */
    public static int getCurrentPartID() {
        return currentPartID;

    }

    /**
     * @param n the int to change the generated Part ID to. Used for testing only.
     */
    public static void setCurrentPartID(int n) {
        currentPartID = n;

    }


    /**
     * @return the currently available Product ID that has been auto-generated.
     */
    public static int getCurrentProductID() {
        return currentProductID;
    }

    /**
     * @param n the int to change the generated Product ID to. Used for testing only.
     */
    public static void setCurrentProductID(int n) {
        currentProductID = n;

    }


    /**
     * @param p the part to add to the ObservableList allParts.
     */
    public static void addPart(Part p){
        allParts.add(allParts.size(), p);
        currentPartID++;
    }

    /**
     * @param p the part to remove from the ObservableList allParts.
     */
    public static boolean deletePart(Part p){
        if(allParts.indexOf(p) != -1){
            allParts.remove(allParts.indexOf(p));
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @param p the product to add to the ObservableList allProducts.
     */
    public static void addProduct(Product p){
        allProducts.add(allProducts.size(), p);
        currentProductID++;
    }

    /**
     * @param p the product to remove from the observable list allProducts.
     */
    public static boolean deleteProduct(Product p){
        if(allProducts.indexOf(p) != -1) {
            allProducts.remove(allProducts.indexOf(p));
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param partId the ID of the part to lookup
     * @return the Part object that has the ID that was passed in to the method
     */
    public static Part lookupPart(int partId){
        for (int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partId) return allParts.get(i);
        }
        return null;
    }

    /**
     * @param productId the ID of the product to lookup
     * @return the Product object that has the ID that was passsed in to the method
     */
    public static Product lookupProduct(int productId){
        for (int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productId) return allProducts.get(i);
        }
        return null;
    }

    /**
     * @param partName the part name to lookup
     * @return the list of Parts matching the part name that was passed in
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getName() == partName) matchingParts.add(allParts.get(i));
        }

        return matchingParts;
    }

    /**
     * @param productName the product name to look up
     * @return the list of Products matching the product name that was passed in
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getName() == productName) matchingProducts.add(allProducts.get(i));
        }

        return matchingProducts;
    }

    /**
     * @return the list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @param index the index at which the part to be updated is stored
     * @param p the new part that is replacing the old part at the index passed in
     */
    public static void updatePart(int index, Part p){
        allParts.set(index, p);
    }

    /**
     * @param index the index at which the product to be updated is stored
     * @param p the new product that is replacing the old product at the index passed in
     */
    public static void updateProduct(int index, Product p){
        allProducts.set(index, p);
    }

}
