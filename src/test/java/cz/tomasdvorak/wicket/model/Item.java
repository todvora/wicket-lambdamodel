package cz.tomasdvorak.wicket.model;

import java.util.Date;

public class Item {

    private String name;
    private Date date;
    private Item child;

    public Item(String name, Date date, Item child) {
        this.name = name;
        this.date = date;
        this.child = child;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Item getChild() { return child; }
    public void setChild(Item child) { this.child = child; }
}