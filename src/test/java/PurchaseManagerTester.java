import org.group.InsufficientInventoryException;
import org.group.InventoryDataSourceDouble;
import org.group.Item;
import org.group.PurchaseManager;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PurchaseManagerTester {

  //Stub
  private Item coke = new Item(0, "Coke", 100, 2.49, 10);

  @Test
  public void testGetItem() {
    InventoryDataSourceDouble idsd = new InventoryDataSourceDouble();
    idsd.addNewItemToInventory(coke.getName(), coke.getQuantity(), coke.getPrice(), coke.getReorderCutoff());
    PurchaseManager pm = new PurchaseManager(idsd);
    pm.increaseInventory(0, 10);

    assertEquals(110, idsd.getItem(0).getQuantity());
    assertEquals(idsd.getItem(0), pm.getItem(0));
  }

  @Test
  public void testIncreaseInventory() {
    InventoryDataSourceDouble idsd = new InventoryDataSourceDouble();
    PurchaseManager pm = new PurchaseManager(idsd);
    pm.increaseInventory(0, 10);
    assertEquals(110, idsd.getItem(0).getQuantity());
  }

  @Test
  public void testAddNewItemToInventory() {
    InventoryDataSourceDouble idsd = new InventoryDataSourceDouble();
    PurchaseManager pm = new PurchaseManager(idsd);
    int newItemId = pm.addNewItemToInventory("Sprite", 50, 1.99, 5);
    Item newItem = idsd.getItem(newItemId);
    assertEquals("Sprite", newItem.getName());
    assertEquals(50, newItem.getQuantity());
    assertEquals(1.99, newItem.getPrice());
    assertEquals(5, newItem.getReorderCutoff());
  }

  @Test
  public void testPurchaseItem() {
    InventoryDataSourceDouble idsd = new InventoryDataSourceDouble();
    PurchaseManager pm = new PurchaseManager(idsd);
    pm.purchaseItem(0, 20);
    assertEquals(80, idsd.getItem(0).getQuantity());
    pm.purchaseItem(1, 20);
    assertEquals(80, idsd.getItem(1).getQuantity());
  }

  @Test
  public void testInsufficientInventory() {
    InventoryDataSourceDouble idsd = new InventoryDataSourceDouble();
    PurchaseManager pm = new PurchaseManager(idsd);
    int itemId = pm.addNewItemToInventory(coke.getName(), coke.getQuantity(), coke.getPrice(), coke.getReorderCutoff());
    pm.purchaseItem(itemId, 90);
    assertEquals(10, idsd.getItem(itemId).getQuantity());
    assertThrows(InsufficientInventoryException.class, () -> pm.purchaseItem(itemId, 11));
  }


  @Test
  public void testMockPropertyChange() {
    InventoryDataSourceDouble idsd = new InventoryDataSourceDouble();
    PurchaseManager pm = new PurchaseManager(idsd);
    PropertyChangeListener mockListener = Mockito.mock(PropertyChangeListener.class);
    pm.addPropertyChangeListener(mockListener);
    pm.purchaseItem(0, 91);
    ArgumentCaptor<PropertyChangeEvent> argumentCaptor = ArgumentCaptor.forClass(PropertyChangeEvent.class);
    verify(mockListener).propertyChange(argumentCaptor.capture());
    PropertyChangeEvent eventWeSaw = argumentCaptor.getValue();
    assertEquals("reorder", eventWeSaw.getPropertyName());
    assertEquals(new Item(0, "Coke", 100, 2.49, 10), eventWeSaw.getOldValue());
    assertEquals(new Item(0, "Coke", 9, 2.49, 10), eventWeSaw.getNewValue());
  }
}
