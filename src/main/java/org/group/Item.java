package org.group;

import java.util.Objects;

public class Item {
  private int id, quantity, reorderCutoff;
  private String name;
  private double price;

  public Item(int id, String name, int quantity, double price, int reorderCutoff) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.reorderCutoff = reorderCutoff;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getReorderCutoff() {
    return reorderCutoff;
  }

  public void setReorderCutoff(int reorderCutoff) {
    this.reorderCutoff = reorderCutoff;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true; // Check for identity
    if (obj == null || getClass() != obj.getClass()) return false; // Check for type compatibility
    Item item = (Item) obj; // Cast to Item
    return id == item.id &&
            quantity == item.quantity &&
            Double.compare(item.price, price) == 0 &&
            Objects.equals(name, item.name) &&
            Objects.equals(reorderCutoff, item.reorderCutoff);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, quantity, price, reorderCutoff);
  }
}

