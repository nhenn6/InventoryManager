package InventoryManager.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Noah Henning
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts;
    public Product(int id, String name, double price, int stock, int min, int max){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set for the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set for the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set for the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the minimum allowed stock of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the minimum allowed stock to set for the product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the maximum allowed stock for the product
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the maximum allowed stock to set for the product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the list of parts associated with the product
     */
    public ObservableList<Part> getAssociatedParts(){
        return this.associatedParts;
    }

    /**
     * @param p the part to add to the list of associated parts
     */
    public boolean addAssociatedPart(Part p){
        return this.associatedParts.add(p);
    }

    /**
     * @return the success of the deletion operation; true for success, false for otherwise
     * @param p the part to delete from the associated parts list
     */
    public boolean deleteAssociatedPart(Part p){
        if (this.associatedParts != null) {
            for (int i = 0; i < this.associatedParts.size(); i++) {
                if (this.associatedParts.get(i).getId() == p.getId()) {
                    this.getAssociatedParts().remove(i);
                    return true;
                }
            }
        }
        return false;

    }


}

