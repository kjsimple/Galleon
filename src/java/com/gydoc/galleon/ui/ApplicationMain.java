package com.gydoc.galleon.ui;

import com.gydoc.galleon.UIUtil;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.i18n.Res;
import com.gydoc.galleon.validator.LoginFormValidator;
import com.vaadin.Application;
import com.vaadin.data.Validator;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.terminal.UserError;
import com.gydoc.galleon.tenant.TenantManager;
import com.gydoc.galleon.service.UserService;
import com.gydoc.galleon.SpringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationMain extends Application implements HttpServletRequestListener {
    
    private Window mainWindow;
    private Window loginWindow;
    private String tenant;
    private static ThreadLocal<ApplicationMain> applications = new ThreadLocal<ApplicationMain>();

    public static Application.SystemMessages getSystemMessages() {
        return new GalleonSystemMessage();
    }

    public static ApplicationMain getGalleonApplication() {
        return applications.get();
    }
    
    public void cleanUp() {
        setTenant(null);
    }
    
    public void init() {
        applications.set(this);
        mainWindow = new Window("Galleon");
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
                            String t = (String) TenantManager.getInstance().getTenant();
                            if (t != null) {
                                String url = String.format("/%1$s?%2$s=%3$s", getProperty("applicationContextPath"), TenantManager.TENANT_KEY, t);
                                mainWindow.getApplication().setLogoutURL(url);
                            }
                            cleanUp();
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
        loginForm.addField("password", pField);

        Button loginButton = new Button(Res.getMessage("loginDialog.loginButton.caption"));
        UIUtil.makeDefaultButton(loginButton);
        loginButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    AbstractField userNameField = (AbstractField) loginForm.getField("username");
                    AbstractField passwordField = (AbstractField) loginForm.getField("password");
                    String userName = userNameField.toString();
                    String password = loginForm.getField("password").getValue().toString();
                    UserService service = (UserService) SpringUtil.getBean("userService");
                    if (service.authenticate(userName, password)) {
                        passwordField.setValue(""); // clear password for security sake
                        getMainWindow().removeWindow(loginWindow);
                        User u = new User();
                        u.setUserName(userName);
                        
                        getMainWindow().getApplication().setUser(u);
                        return ;
                    }
                    userNameField.setComponentError(new UserError(Res.getMessage("loginDialog.validat.invaliduser")));
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
        // to handle multi-tenant
        if (mainWindow == null || mainWindow.getApplication().getUser() == null) {
            // allow user to switch to another tenant before login successfully.
            String tenant = request.getParameter(TenantManager.TENANT_KEY);
            if (tenant != null) {
                setTenant(tenant);
            }
            TenantManager.getInstance().setTenant(getTenant());
        } else {
            // always use tenant from session after user logged in
            TenantManager.getInstance().setTenant(getTenant());
        }
        applications.set(this);
    }

    public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
        applications.remove();
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
    
}
