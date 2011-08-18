package com.gydoc.galleon.validator;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.i18n.Res;
import com.gydoc.galleon.service.UserService;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.Form;

/**
 *
 */
public class LoginFormValidator extends AbstractValidator {

    private Form form;

    public LoginFormValidator(Form form) {
        this(form, Res.getMessage("loginDialog.validat.invaliduser"));
    }

    public LoginFormValidator(Form form, String message) {
        super(message);
        this.form = form;
    }

    public boolean isValid(Object o) {
        String userName =  form.getField("username").getValue().toString();
        String pass = form.getField("password").getValue().toString();
        UserService us = (UserService) SpringUtil.getBean("userService");
        return us.authenticate(userName, pass);
    }

}
