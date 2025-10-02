package org.group;

import java.util.ArrayList;

public class InventoryDataSourceDouble implements InventoryDataSource {
  ArrayList<Item> inventory = new ArrayList<>();


  //Added
  public InventoryDataSourceDouble() {
    inventory.add(new Item(0, "Coke", 100, 2.49, 10));
    inventory.add(new Item(1, "Pepsi", 100, 2.49, 10));
  }

  public Item getItem(int id) {
    return inventory.get(id);
  }

  public void changeInventory(int id, int magnitudeOfChange) {
    Item currItem = getItem(id);
    //The old code
   // currItem.setQuantity(currItem.getQuantity() + magnitudeOfChange);
   // inventory.set(id, currItem);

    //The new code
    Item updatedItem = new Item(currItem.getId(), currItem.getName(),
            currItem.getQuantity() + magnitudeOfChange, currItem.getPrice(),
            currItem.getReorderCutoff());
    inventory.set(id, updatedItem);
  }

  public int addNewItemToInventory(String name, int quantity, double price, int reorderCutoff) {
    int newID = inventory.size();
    inventory.add(new Item(newID, name, quantity, price, reorderCutoff));
    return newID;
  }
}
