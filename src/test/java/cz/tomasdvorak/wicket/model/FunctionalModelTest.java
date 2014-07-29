package cz.tomasdvorak.wicket.model;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class FunctionalModelTest {


    private FunctionalModelTest.Item item;
    private Date time;

    @Before
    public void setUp() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2014, Calendar.APRIL, 18);
        this.time = cal.getTime();
        this.item = new Item("foo", time);
    }

    @Test
    public void testGetterSetterString() throws Exception {
        final IModel<String> model = new FunctionalModel<>(Item::getName, item::setName, item);
        Assert.assertEquals("foo", model.getObject());
        model.setObject("bar");
        Assert.assertEquals("bar", model.getObject());
    }

    @Test
    public void testGetterSetterDate() throws Exception {
        final IModel<Date> model = new FunctionalModel<>(Item::getDate, item::setDate, item);
        Assert.assertEquals(time, model.getObject());
        model.setObject(new Date());
        Assert.assertNotEquals(time, model.getObject());
    }

    private class Item {

        private String name;
        private Date date;

        public Item(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        private Date getDate() { return date; }
        private void setDate(Date date) { this.date = date; }
    }
}

