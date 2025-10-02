package org.group;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class firer {
  private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }

}
