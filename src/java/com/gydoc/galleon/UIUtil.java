package com.gydoc.galleon;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;

/**
 *
 */
public class UIUtil {

    private UIUtil() {

    }

    public static void makeDefaultButton(Button button) {
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER, ShortcutAction.ModifierKey.CTRL);
        button.addStyleName("primary");
    }

}
