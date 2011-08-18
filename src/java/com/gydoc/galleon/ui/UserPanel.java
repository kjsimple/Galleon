package com.gydoc.galleon.ui;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.i18n.Res;
import com.gydoc.galleon.service.UserService;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 *
 */
public class UserPanel extends VerticalLayout {
    
    private Table usersTable;
    private Form userForm;
    private User user;

    public UserPanel() {
        init();
    }
    
    private void init() {
        userForm = new Form();

        user = new User();
        BeanItem<User> items = new BeanItem<User>(user);
        userForm.setItemDataSource(items);
        userForm.setFormFieldFactory(new FormFieldFactory() {
            public Field createField(Item item, Object pid, Component uiContext) {
                String msgId = "user." + pid + ".caption";
                System.out.println("msgId = " + msgId);
                if ("userLogin".equals(pid) || "userName".equals(pid)) {
                    return new TextField(Res.getMessage(msgId));
                } else if ("password".equals(pid)) {
                    PasswordField password = new PasswordField(Res.getMessage(msgId));
                    return password;
                }
                return null;
            }
        });
        userForm.setVisibleItemProperties(new String[] {"userLogin", "userName", "password"});

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button addButton = new Button(Res.getMessage("button.add.caption"));
        addButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UserService us = (UserService) SpringUtil.getBean("userService");
                us.addUser(user);
            }
        });
        buttons.addComponent(addButton);
        Button searchButton = new Button(Res.getMessage("button.search.caption"));
        searchButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Container c = usersTable.getContainerDataSource();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", UserPanel.this.getApplication().getLocale());
                c.removeAllItems();
                UserService us = (UserService) SpringUtil.getBean("userService");
                Collection<User> users = us.findRecentUsers(10);
                for (User u : users) {
                    Item item = c.addItem(u.getId());
                    item.getItemProperty("userID").setValue(u.getUserLogin());
                    item.getItemProperty("userName").setValue(u.getUserName());
                    item.getItemProperty("creationDate").setValue(sdf.format(u.getCreationDate().getTime()));
                }
            }
        });
        buttons.addComponent(searchButton);
        
        userForm.getLayout().addComponent(buttons);
        addComponent(userForm);

        usersTable = new Table(Res.getMessage("user.table.title"));
        usersTable.addStyleName("galleon-data-table");
        usersTable.setPageLength(10);
        usersTable.setSelectable(true);
        usersTable.setWidth("100%");
        
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("userID", String.class, null);
        container.addContainerProperty("userName", String.class, null);
        container.addContainerProperty("creationDate", String.class, null);
        usersTable.setColumnHeader("userID", Res.getMessage("user.id.th.lable"));
        usersTable.setColumnHeader("userName", Res.getMessage("user.name.th.label"));
        usersTable.setColumnHeader("creationDate", Res.getMessage("user.creationDate.th.label"));
        
        usersTable.setContainerDataSource(container);
        addComponent(usersTable);
    }
    
}
