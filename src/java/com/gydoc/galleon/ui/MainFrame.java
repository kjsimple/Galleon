package com.gydoc.galleon.ui;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.ExpenseReport;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.i18n.Res;
import com.gydoc.galleon.service.ExpenseReportService;
import com.gydoc.galleon.ui.purpose.PurposeMain;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

/**
 *
 */
public class MainFrame extends Window implements Property.ValueChangeListener {

    VerticalLayout rightPane = new VerticalLayout();

    public MainFrame(String caption) {
        super(caption);
        init();
    }

    private void init() {
        setModal(true);
        setHeight("98%");
        setWidth("98%");
        rightPane.setMargin(true);
        setContent(createContent());
    }

    private ComponentContainer createContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        
        Panel actionsPanel = new Panel();
        actionsPanel.setStyleName(Reindeer.PANEL_LIGHT);
        actionsPanel.setHeight("100%");
        actionsPanel.setWidth(null);
        actionsPanel.getContent().setSizeUndefined();
        Tree actions = new Tree();
        actions.setContainerDataSource(createActions());
        actions.setItemCaptionPropertyId("name");
        actions.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
        actionsPanel.addComponent(actions);
        layout.addComponent(actionsPanel);

        actions.addListener(this);
        actions.setImmediate(true);

        layout.addComponent(rightPane);
        layout.setExpandRatio(rightPane, 1);
        return layout;
    }

    private Container createActions() {
        HierarchicalContainer container = new HierarchicalContainer();
        container.addContainerProperty("name", String.class, null);
        
        Item doc = container.addItem("DOC");
        doc.getItemProperty("name").setValue(Res.getMessage("funcmenu.documents.caption"));
        Item myDoc = container.addItem("MYDOC");
        myDoc.getItemProperty("name").setValue(Res.getMessage("funcmenu.documents.er.caption"));
        
        Item admin = container.addItem("ADMIN");
        admin.getItemProperty("name").setValue(Res.getMessage("funcmenu.admin.caption"));
        Item adminUser = container.addItem("USER");
        adminUser.getItemProperty("name").setValue(Res.getMessage("funcmenu.adminuser.caption"));
        container.setChildrenAllowed("USER", false);
        container.setParent("USER", "ADMIN");

        Item adminDepartment = container.addItem("DEP");
        adminDepartment.getItemProperty("name").setValue(Res.getMessage("funcmenu.admindepartment.caption"));
        container.setChildrenAllowed("DEP", false);
        container.setParent("DEP", "ADMIN");

        Item adminPurpose = container.addItem("PURPOSE");
        adminPurpose.getItemProperty("name").setValue(Res.getMessage("funcmenu.adminpurpose.caption"));
        container.setChildrenAllowed("PURPOSE", false);
        container.setParent("PURPOSE", "ADMIN");
        
        container.setChildrenAllowed("MYDOC", false);
        container.setParent("MYDOC", "DOC");
        return container;
    }

    public void valueChange(Property.ValueChangeEvent event) {
        String actionName = (String) event.getProperty().getValue();
        if (actionName == null) {
            return ;
        }

        rightPane.removeAllComponents();
        if ("MYDOC".equals(actionName)) {
            rightPane.addComponent(expensePanel());
        } else if ("USER".equals(actionName)) {
            rightPane.addComponent(new UserPanel());
        } else if ("DEP".equals(actionName)) {
            rightPane.addComponent(new DepartmentPanel());
        } else if ("PURPOSE".equals(actionName)) {
            rightPane.addComponent(new PurposeMain());
        }
    }

    private Component expensePanel() {
        VerticalLayout layout = new VerticalLayout();
        Form expenseForm = new Form();
        final TextField titleField = new TextField(Res.getMessage("expense.title.caption"));
        final TextField amountField = new TextField(Res.getMessage("expense.amount.caption"));
        amountField.setPropertyDataSource(new ObjectProperty<Integer>(null));
        expenseForm.addField("title", titleField);
        expenseForm.addField("amount", amountField);
        HorizontalLayout buttons = new HorizontalLayout();
        Button addButton = new Button(Res.getMessage("button.add.caption"));
        addButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                ExpenseReport er = new ExpenseReport();
                er.setTitle((String) titleField.getValue());
                er.setAmount((Integer) amountField.getPropertyDataSource().getValue());
                User user = (User) ApplicationMain.getGalleonApplication().getUser();
                er.setOwner(user);
                ExpenseReportService service = (ExpenseReportService) SpringUtil.getBean(ExpenseReportService.S_ID);
                service.addExpense(er);
            }
        });
        buttons.addComponent(addButton);
        expenseForm.getLayout().addComponent(buttons);
        layout.addComponent(expenseForm);

        Table ers = new Table(Res.getMessage("er.table.title"));
        ers.addStyleName("galleon-data-table");
        ers.setPageLength(10);
        ers.setSelectable(true);
        ers.setContainerDataSource(createErTable());
        ers.setWidth("100%");
        layout.addComponent(ers);

        return layout;
    }

    private Container createErTable() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("name2", String.class, null);
        container.addContainerProperty("name3", String.class, null);
        for (int i = 0; i < 12; i++) {
            Item item = container.addItem(i);
            item.getItemProperty("name").setValue("a" + i);
            item.getItemProperty("name2").setValue("a0" + i);
            item.getItemProperty("name3").setValue("a00" + i);
        }
        return container;
    }

}
