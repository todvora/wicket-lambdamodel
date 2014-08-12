package cz.tomasdvorak.wicket.model;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class LambdaModelTest {

    private Person person;
    private Date time;

    @Before
    public void setUp() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(1987, Calendar.APRIL, 18);
        this.time = cal.getTime();
        cal.add(Calendar.MONTH, +1);
        this.person = new Person("foo", time, new Person("child", cal.getTime(), null));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSerialize() throws IOException, ClassNotFoundException {
        // create lambda model
        final LambdaModel<String> lambdaModel = new LambdaModel<>(person::getName, person::setName);

        // serialize model, like when wicket puts model to session
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(lambdaModel);

        // serialized model (String in bytes form)
        final byte[] serializedLambda = stream.toByteArray();

        // read model from serialized data
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(serializedLambda));
         final LambdaModel<String> deserializedLambda = (LambdaModel<String>) in.readObject();

        // verify, that it still returns correct data
        Assert.assertEquals("foo", deserializedLambda.getObject());
    }

    @Test
    public void testGetterSetterString() throws Exception {
        final IModel<String> model = new LambdaModel<>(()-> person.getName(), (val)-> person.setName(val));
        Assert.assertEquals("foo", model.getObject());
        model.setObject("bar");
        Assert.assertEquals("bar", model.getObject());
    }

    @Test
    public void testGetterSetterDate() throws Exception {
        final IModel<Date> model = new LambdaModel<>(person::getDate, person::setDate);
        Assert.assertEquals(time, model.getObject());
        model.setObject(new Date());
        Assert.assertNotEquals(time, model.getObject());
    }

    @Test
    public void testNestedObjects() throws Exception {
        final IModel<String> model = new LambdaModel<>(()-> person.getChild().getName(), (val)-> person.getChild().setName(val));
        Assert.assertEquals("child", model.getObject());
        model.setObject("anotherChild");
        Assert.assertEquals("anotherChild", model.getObject());
        Assert.assertEquals("anotherChild", person.getChild().getName());
    }

    @Test
    public void testPropertyModel() {
        final PropertyModel<String> propertyModel = new PropertyModel<>(person, "name");
        final LambdaModel<String> lambdaModel = new LambdaModel<>(person::getName, person::setName);
        Assert.assertEquals(propertyModel.getObject(), lambdaModel.getObject());
    }

    @Test(expected = WicketRuntimeException.class)
    public void testNullPointerSetter() {
        final Person nullChild = new Person("foo", new Date(), null);
        final IModel<String> lambdaModel = new LambdaModel<>(() -> nullChild.getChild().getName(), (val) -> nullChild.getChild().setName(val));
        lambdaModel.setObject("bar");
    }

    @Test
    public void testNullPointerGetter() {
        final Person nullChild = new Person("foo", new Date(), null);
        final IModel<String> lambdaModel = new LambdaModel<>(() -> nullChild.getChild().getName(), (val) -> nullChild.getChild().setName(val));
        Assert.assertNull(lambdaModel.getObject());
    }
}

