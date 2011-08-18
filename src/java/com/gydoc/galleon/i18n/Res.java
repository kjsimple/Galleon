package com.gydoc.galleon.i18n;

import com.gydoc.galleon.SpringUtil;
import java.util.Locale;

import com.gydoc.galleon.ui.ApplicationMain;
import org.springframework.context.MessageSource;

/**
 *
 */
public class Res {
    
    private static MessageSource ms = (MessageSource) SpringUtil.getBean("messageSource");
    private static Object[] EMPTY_ARGS = new Object[0];

    private Res() {
        
    }
    
    public static String getMessage(String key) {
        return ms.getMessage(key, EMPTY_ARGS, getLocale());
    }
    
    public static String getMessage(String key, Object[] args) {
        return ms.getMessage(key, args, getLocale());
    }
    
    public static String getMessage(String key, Locale locale) {
        return ms.getMessage(key, EMPTY_ARGS, locale);
    }
    
    public static String getMessage(String key, Object[] args, Locale locale) {
        return ms.getMessage(key, args, locale);
    }
    
    private static Locale getLocale() {
        Locale locale = ApplicationMain.getGalleonApplication().getLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

}
