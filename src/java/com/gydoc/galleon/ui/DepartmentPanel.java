package com.gydoc.galleon.ui;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.Department;
import com.gydoc.galleon.i18n.Res;
import com.gydoc.galleon.service.DepartmentService;
import com.vaadin.ui.*;

/**
 *
 */
public class DepartmentPanel extends VerticalLayout {

    private TextField depName;
    private TextField depCode;
    private TextField depDesc;

    public DepartmentPanel() {
        init();
    }

    private void init() {
        Form userForm = new Form();

        depName = new TextField(Res.getMessage("department.name.caption"));
        userForm.addField("depname", depName);
        depCode = new TextField(Res.getMessage("department.code.caption"));
        userForm.addField("depcode", depCode);
        depDesc = new TextField(Res.getMessage("department.desc.caption"));
        userForm.addField("depdesc", depDesc);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button addButton = new Button(Res.getMessage("button.add.caption"));
        addButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                DepartmentService service = (DepartmentService) SpringUtil.getBean(DepartmentService.S_ID);
                Department dep = new Department();
                dep.setName(depName.getValue().toString());
                dep.setCode(depCode.getValue().toString());
                dep.setDescription(depDesc.getValue().toString());
                service.addDepartment(dep);
            }
        });
        buttons.addComponent(addButton);
        Button searchButton = new Button(Res.getMessage("button.search.caption"));
        searchButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

            }
        });
        buttons.addComponent(searchButton);

        userForm.getLayout().addComponent(buttons);
        addComponent(userForm);
    }

}
