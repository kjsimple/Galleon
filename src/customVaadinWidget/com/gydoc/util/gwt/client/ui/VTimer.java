package com.gydoc.util.gwt.client.ui;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VTimer extends GwtTimer implements Paintable {
    
    public VTimer() {
        super();
    }
    
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        // This call should be made first. Ensure correct implementation,
        // and let the containing layout manage caption, etc.
        if (client.updateComponent(this, uidl, true)) {
            return;
        }
        
        int delay = uidl.getIntVariable("delay");
        int period = uidl.getIntVariable("period");
    }
    
}
