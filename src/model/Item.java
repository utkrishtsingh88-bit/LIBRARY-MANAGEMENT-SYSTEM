package model;

import java.io.Serializable;

/**
 * Abstract base class for library items.
 */
public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String id;
    protected String title;
    
    public Item(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public abstract void displayDetails();
}
