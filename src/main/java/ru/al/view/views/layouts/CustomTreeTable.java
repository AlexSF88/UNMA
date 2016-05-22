package ru.al.view.views.layouts;

import com.vaadin.ui.TreeTable;
import ru.al.model.Note;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by Alex on 05.12.2015.
 */
public class CustomTreeTable extends TreeTable {
    public Object addItem(Note newNote){
        newNote.updateStatus();
        return addItem(new Object[]{newNote, newNote.isActive()?"active":"done", newNote.getActiveSubTask(), newNote.getAccomplishedSubTask(), newNote.getAuthor(), newNote.getLastUpdate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT))}, null);
    }
}
