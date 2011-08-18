package com.gydoc.galleon.ui;

import com.gydoc.galleon.i18n.Res;
import com.vaadin.Application;

/**
 *
 */
public class GalleonSystemMessage extends Application.CustomizedSystemMessages {

    @Override
    public String getCommunicationErrorCaption() {
        return Res.getMessage("galleon.sys.msg.commErrorCaption");
    }
    
}
