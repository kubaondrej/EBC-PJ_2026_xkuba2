package cz.mendelu.projektjava_xkuba2.model;

import java.util.UUID;

public class StockItem {
    private String id;
    int amount;

    public StockItem(String id, int amount) {
        this.id=id;
        this.amount = amount;
    }
    public StockItem() {}

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }


    public void setAmmount(int ammount) {
        this.amount = ammount;
    }

    @Override
    public String toString()
    {
        return "Lék s id:" + ' ' + id + ' ' + 'v' + ' ' + "množství" + amount;
    }
}
