package ru.al.view.views.layouts;

import com.vaadin.ui.*;

/**
 * Created by Alex on 23.01.2016.
 */
public class MessageBox extends Window {

    protected VerticalLayout windowLayout;
    protected HorizontalLayout headerLayout;
    protected HorizontalLayout bodyLayout;
    protected HorizontalLayout footerLayout;
    protected HorizontalLayout buttonLayout;
    protected Label headerLabel;

    public MessageBox(String caption, String message) {
        super(caption);
        init(message);
    }

    private void init(String message){
        windowLayout = new VerticalLayout();
        windowLayout.setSizeFull();
        headerLayout = new HorizontalLayout();
        headerLayout.setSizeFull();
        headerLabel = new Label(message);
        headerLabel.setSizeUndefined();
        headerLayout.addComponent(headerLabel);
        headerLayout.setComponentAlignment(headerLabel, Alignment.MIDDLE_CENTER);
//        bodyLayout = new HorizontalLayout();
//        bodyLayout.setSizeFull();
//        bodyLayout.setSpacing(true);
        windowLayout.addComponent(headerLayout);
//        windowLayout.addComponent(bodyLayout);

        initFooter();

        setWidth(20, Unit.PERCENTAGE);
        setHeight(20, Unit.PERCENTAGE);
        center();
        setContent(windowLayout);
    }

    protected void initBody(){
    }

    protected void initFooter(){
        footerLayout = new HorizontalLayout();
        footerLayout.setWidth(100, Unit.PERCENTAGE);
        buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeUndefined();
//        buttonLayout.setMargin(true);
        buttonLayout.setSpacing(true);
        footerLayout.addComponent(buttonLayout);
        footerLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
        initButtons();
        windowLayout.addComponent(footerLayout);
    }

    protected void initButtons(){
        buttonLayout.addComponent(new Button("Подтвердить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        }));
    }
}
