package cz.mendelu.projektjava_xkuba2.model;

import java.util.UUID;

public class StockItem {
    private String id;
    int amount;

    public StockItem(String id, int ammount) {
        this.id=id;
        this.amount = ammount;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
    //nejspíš nebude potřeba
//    public void setMedication(Medication medication) {
//        this.medication = medication;
//    }

    public void setAmmount(int ammount) {
        this.amount = ammount;
    }

    @Override
    public String toString()
    {
        return "Lék s id:" + ' ' + id + ' ' + 'v' + ' ' + "množství" + amount;
    }
}
