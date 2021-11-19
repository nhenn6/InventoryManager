package InventoryManager.classes;

/**
 *
 * @author Noah Henning
 */
public class InHouse extends Part{

    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.setMachineId(machineId);
    }

    /**
     * @return the machine ID for the machine on which this part is produced
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machine ID to set for this part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
