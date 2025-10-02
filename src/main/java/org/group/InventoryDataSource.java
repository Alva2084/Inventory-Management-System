package org.group;

public interface InventoryDataSource {
  Item getItem(int id);
  void changeInventory(int id, int magnitudeOfChange);
  int addNewItemToInventory(String name, int quantity, double price, int reorderCutoff);
}
