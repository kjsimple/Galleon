package com.gydoc.galleon.ui;

import com.gydoc.galleon.UIUtil;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.i18n.Res;
import com.gydoc.galleon.validator.LoginFormValidator;
import com.vaadin.Application;
import com.vaadin.data.Validator;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationMain extends Application implements HttpServletRequestListener {
    
    private Window mainWindow;
    private Window loginWindow;
    private static ThreadLocal<ApplicationMain> applications = new ThreadLocal<ApplicationMain>();

    public static Application.SystemMessages getSystemMessages() {
        return new GalleonSystemMessage();
    }

    public static ApplicationMain getGalleonApplication() {
        return applications.get();
    }
    
    public void init() {
        applications.set(this);
        mainWindow = new Window("galleon");
        setMainWindow(mainWindow);
        setTheme("myReindeer");
        mainWindow.getApplication().addListener(new UserChangeListener() {
            public void applicationUserChanged(UserChangeEvent userChangeEvent) {
                if (userChangeEvent.getNewUser() != null) {
                    Window mainFrame = new MainFrame("");
                    mainWindow.removeWindow(loginWindow);
                    loginWindow = null;
                    mainWindow.addWindow(mainFrame);
                    mainFrame.addListener(new Window.CloseListener() {
                        public void windowClose(Window.CloseEvent e) {
                            mainWindow.getApplication().close();
                        }
                    });
                }
            }
        });
        showLoginDialog();
    }
    
    private void showLoginDialog() {
        loginWindow = new Window(Res.getMessage("loginDialog.caption"));
        VerticalLayout layout = (VerticalLayout) loginWindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        loginWindow.setReadOnly(true);
        loginWindow.setModal(true);
        loginWindow.setResizable(false);
        loginWindow.center();

        final Form loginForm = new Form();
        final TextField uField = new TextField(Res.getMessage("loginDialog.userName.label"));
        uField.setRequired(true);
        uField.setRequiredError(Res.getMessage("loginDialog.userName.required"));
        loginForm.addField("username", uField);
        final PasswordField pField = new PasswordField(Res.getMessage("loginDialog.password.label"));
        uField.addValidator(new LoginFormValidator(loginForm));
        loginForm.addField("password", pField);

        Button loginButton = new Button(Res.getMessage("loginDialog.loginButton.caption"));
        UIUtil.makeDefaultButton(loginButton);
        loginButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    loginForm.commit();
                    User u = new User();
                    u.setUserName((String) uField.getValue());
                    u.setPassword((String) pField.getValue());
                    pField.setValue(""); // clear password for security issue
                    getMainWindow().removeWindow(loginWindow);
                    getMainWindow().getApplication().setUser(u);
                } catch (Validator.InvalidValueException e) {
                    // ignore
                }
            }
        });

        loginForm.getLayout().addComponent(createButtonPanel(loginButton));
        loginWindow.addComponent(loginForm);

        mainWindow.addWindow(loginWindow);
        loginForm.focus();
        }

    private Component createButtonPanel(Button loginButton) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(loginButton);
        HorizontalLayout piWrap = new HorizontalLayout();
        piWrap.setStyleName("notDisplay");
        ProgressIndicator pi = new ProgressIndicator();
        pi.setPollingInterval(60 * 1000);
        piWrap.addComponent(pi);
        layout.addComponent(piWrap);
        return layout;
    }

    public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
        applications.set(this);
    }

    public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
        applications.remove();
    }
    
}