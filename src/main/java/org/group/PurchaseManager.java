package org.group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PurchaseManager implements PropertyChangeListener {

  private InventoryDataSource inventoryDataSource;
  private PropertyChangeSupport pcs;

  public PurchaseManager(InventoryDataSource IDS) {
    inventoryDataSource = IDS;
    pcs = new PropertyChangeSupport(this);
  }

  public void purchaseItem(int id, int quantity) throws InsufficientInventoryException {
    Item originalItem = inventoryDataSource.getItem(id);

    if (originalItem.getQuantity() >= quantity) {
      inventoryDataSource.changeInventory(id, -quantity);
      Item updatedItem = inventoryDataSource.getItem(id);

      if (updatedItem.getQuantity() <= updatedItem.getReorderCutoff()) {
        pcs.firePropertyChange("reorder", originalItem, updatedItem);
      }
    } else {
      throw new InsufficientInventoryException("Insufficient Inventory");
    }
  }

  public Item getItem(int id) {
    return inventoryDataSource.getItem(id);
  }

  public void increaseInventory(int id, int quantityToAdd) {
    inventoryDataSource.changeInventory(id, quantityToAdd);
  }

  public int addNewItemToInventory(String name, int quantity, double price, int reorderCutoff) {
    return inventoryDataSource.addNewItemToInventory(name, quantity, price, reorderCutoff);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    inventoryDataSource.changeInventory((int) evt.getPropagationId(),
            (int) evt.getOldValue() - (int) evt.getNewValue());
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }
}
