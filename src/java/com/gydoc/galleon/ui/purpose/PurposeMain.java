package com.gydoc.galleon.ui.purpose;

import com.gydoc.galleon.domain.Purpose;
import com.gydoc.galleon.i18n.Res;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

/**
 *
 */
public class PurposeMain extends VerticalLayout {

    private Form form;
    private Purpose purpose;

    public PurposeMain() {
        init();
    }

    private void init() {
        form = new Form();
        purpose = new Purpose();
        BeanItem<Purpose> items = new BeanItem<Purpose>(purpose);
        form.setItemDataSource(items);
        form.setFormFieldFactory(new FormFieldFactory() {
            public Field createField(Item item, Object propertyId, Component uiContext) {
                String msgId = "purpose." + propertyId + ".caption";
                TextArea field = new TextArea(Res.getMessage(msgId) + Res.getMessage("field.caption.suffix"));
                if ("name".equals(propertyId)) {
                    field.setRequired(true);
                    field.setRequiredError(Res.getMessage("field.required.errormsg", new Object[]{Res.getMessage(msgId)}));
                    return field;
                } else if ("description".equals(propertyId)) {
                    field.setRows(4);
                    return field;
                }
                return null;
            }
        });
        form.setVisibleItemProperties(new String[]{"name", "description"});
        form.setImmediate(true);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button addButton = new Button(Res.getMessage("button.add.caption"), form, "commit");
        addButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

            }
        });
        buttons.addComponent(addButton);
        Button searchButton = new Button(Res.getMessage("button.search.caption"));
        searchButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

            }
        });
        buttons.addComponent(searchButton);

        form.getLayout().addComponent(buttons);
        addComponent(form);
    }

}
