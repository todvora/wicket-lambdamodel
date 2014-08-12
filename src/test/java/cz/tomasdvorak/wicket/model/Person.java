package cz.tomasdvorak.wicket.model;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {

    private String name;
    private Date date;
    private Person child;

    public Person(final String name, final Date date, final Person child) {
        this.name = name;
        this.date = date;
        this.child = child;
    }

    public String getName() { return name; }
    public void setName(final String name) { this.name = name; }

    public Date getDate() { return date; }
    public void setDate(final Date date) { this.date = date; }

    public Person getChild() { return child; }
    public void setChild(final Person child) { this.child = child; }
}