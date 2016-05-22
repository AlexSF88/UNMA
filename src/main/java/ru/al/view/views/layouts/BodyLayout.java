package ru.al.view.views.layouts;

import com.vaadin.ui.HorizontalSplitPanel;


/**
 * Created by Alex on 07.11.2015.
 */
public class BodyLayout extends HorizontalSplitPanel {

    private float maxPos = 200;
    private float minPos = 0;
    private Unit unit = Unit.PIXELS;

    public BodyLayout() {
        addSplitterClickListener(splitterClickEvent -> {
            switchCollapsed(minPos, maxPos);
        });
//        setSizeFull();
        setSplitPosition(maxPos, Unit.PIXELS);
    }

    public BodyLayout(float maxPos, float minPos, Unit unit) {
        this.maxPos = maxPos;
        this.minPos = minPos;
        this.unit = unit;
    }

    public void switchCollapsed(float minPos, float maxPos) {
        if (getSplitPosition()==minPos){
            setSplitPosition(maxPos, unit);
        } else {
            this.maxPos = getSplitPosition();
            setSplitPosition(minPos, unit);
        }
    }

    public float getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(int maxPos) {
        this.maxPos = maxPos;
    }

    public float getMinPos() {
        return minPos;
    }

    public void setMinPos(int minPos) {
        this.minPos = minPos;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setParameters (float minPos, float maxPos, Unit unit){
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.unit = unit;
    }
}
