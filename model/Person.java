package model;

import java.io.Serializable;

/**
 * Abstract base class representing a Person in the library system.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String id;
    protected String name;
    
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Abstract method to be implemented by subclasses to display person details.
     */
    public abstract void display();
    
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}
