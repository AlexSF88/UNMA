package ru.al.view.views.layouts;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * Created by Alex on 24.01.2016.
 */
public class ConfirmationDialog extends MessageBox {
    public ConfirmationDialog(String caption, String message) {
        super(caption, message);
    }

    @Override
    protected void initButtons(){
    }

    public void addBodyComponent(Component component){
        bodyLayout.addComponent(component);
        bodyLayout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }

    public void addFooterComponent(Component component){
        buttonLayout.addComponent(component);
        buttonLayout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }
}
