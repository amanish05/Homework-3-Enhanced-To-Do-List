package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    private String description;
    private String dueDate;
    //Two new types category and status added
    private String category;
    private Boolean status;

    public ToDoItem(String description, String dueDate, String category, Boolean status) {
        this.description = description;
        this.dueDate = dueDate;

        //Initializing varuable
        this.category = category;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    //Getter for newely added variable category and status
    public String getCategory() {
        return category;
    }

    public Boolean getStatus() {
        return status;
    }
}
