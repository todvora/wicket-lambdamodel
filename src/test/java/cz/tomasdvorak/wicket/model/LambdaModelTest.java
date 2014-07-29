package cz.tomasdvorak.wicket.model;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class LambdaModelTest {

    private Item item;
    private Date time;

    @Before
    public void setUp() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2014, Calendar.APRIL, 18);
        this.time = cal.getTime();
        cal.add(Calendar.MONTH, +1);
        this.item = new Item("foo", time, new Item("child", cal.getTime(), null));
    }

    @Test
    public void testGetterSetterString() throws Exception {
        final IModel<String> model = new LambdaModel<>(()->item.getName(), (val)->item.setName(val));
        Assert.assertEquals("foo", model.getObject());
        model.setObject("bar");
        Assert.assertEquals("bar", model.getObject());
    }

    @Test
    public void testGetterSetterDate() throws Exception {
        final IModel<Date> model = new LambdaModel<>(item::getDate, item::setDate);
        Assert.assertEquals(time, model.getObject());
        model.setObject(new Date());
        Assert.assertNotEquals(time, model.getObject());
    }

    @Test
    public void testNestedObjects() throws Exception {
        final IModel<String> model = new LambdaModel<>(()->item.getChild().getName(), (val)->item.getChild().setName(val));
        Assert.assertEquals("child", model.getObject());
        model.setObject("anotherChild");
        Assert.assertEquals("anotherChild", model.getObject());
        Assert.assertEquals("anotherChild", item.getChild().getName());
    }


}

