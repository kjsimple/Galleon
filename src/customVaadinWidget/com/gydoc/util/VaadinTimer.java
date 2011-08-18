package com.gydoc.util;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.gydoc.util.gwt.client.ui.VTimer;

@ClientWidget(VTimer.class)
public class VaadinTimer extends AbstractComponent {
    
    private int delay;
    private int period;
    
    public VaadinTimer(int delay, int period) {
        super();
        this.delay = delay;
        this.period = period;
    }
    
    /** Paint (serialize) the component for the client. */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        // Superclass writes any common attributes in the paint target.
        super.paintContent(target);
        
        target.addVariable(this, "delay", delay);
        target.addVariable(this, "period", period);
    }
    
}
