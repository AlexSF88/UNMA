package ru.al.view.components;

import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/**
 * Created by Alex on 12.11.2015.
 */
public class EditableLabel extends HorizontalLayout {
    private Label label;
    private TextField textField;
    private boolean isEditable;
    public EditableLabel() {
        init(null);
    }
    public EditableLabel(String value) {
        init(value);
    }

    public void setEditable(boolean isEditable){
        if(isEditable) {
            label.setVisible(false);
            textField.setValue(label.getValue());
            textField.setVisible(true);
            this.isEditable = true;
        } else {
            textField.setVisible(false);
            label.setValue(textField.getValue());
            label.setVisible(true);
            this.isEditable = false;
        }
    }

    public void setSize(float width, float height, Unit unit){
        setWidth(width,unit);
        setHeight(height, unit);
    }

    public void setValue(String value) {
        label.setValue(value);
        textField.setValue(value);
    }

    public String getValue() {
        return label.getValue();
    }

    public void init(String value){
        this.label = new Label();
        this.label.setValue(value);
        this.textField = new TextField();
        addComponent(label);
        addComponent(textField);
        this.addLayoutClickListener(layoutClickEvent -> {
            if (layoutClickEvent.isDoubleClick()) {
                setEditable(!isEditable);
                System.out.println(!isEditable);
            }
        });
        textField.setImmediate(true);
        textField.addShortcutListener(new ShortcutListener("ctrl") {
            @Override
            public void handleAction(Object o, Object o1) {

            }
        });
        label.setSizeFull();
        textField.setSizeFull();
        textField.setVisible(false);
    }

    @Override
    public String toString() {
        return label.getValue();
    }
}
