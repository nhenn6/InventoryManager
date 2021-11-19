package InventoryManager.classes;

/**
 *
 * @author Noah Henning
 */
public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * @return the name of the company that produces this part
     */
    public String getCompanyName() { return companyName; }

    /**
     * @param companyName the company name to set for this part
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
