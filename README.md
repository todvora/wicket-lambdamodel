wicket-functional
=================

Wicket components and utilities that uses functional features from Java 8

Lambda Model
=================
Lambda Model is replacement for unsafe PropertyModel. Property Model works ok, it's easy to write, easy to understand. But it has several problems. Mainly it is not type safe. And that is something you really don't want in Java code. For example suppose, we have object person of type Person, with property name of type String. 
Then property model looks like this. 

```
IModel<String> nameModel = new PropertyModel<String>(person, "name");
```
Everything works well, it is short and elegant. But one day you decide, that name should not be String, rather object of type Name, with properties firstName and lastName.
Your IDE will help you to refactor code. But compiler and IDE has no chance to recognize, that old PropertyModel is now accessing different type and will return wrong data (probably toString of object).

Or you decide to rename property (and getter and setter) to fullName and error is introduced. Compiler will not check, that old property name does not exist anymore. This and other problems of PropertyModel are also descibed in article [A CLEAN APPROACH TO WICKET MODELS](http://blog.eluder.org/2012/02/a-clean-approach-to-wicket-models/) by [@trautonen](https://github.com/trautonen/).

Lot's of people are dealing with this problem and looking for a way how to ensure type safety while keeping symplicity of PropertyModel. For example project [SafeModel](https://github.com/duesenklipper/wicket-safemodel) uses proxy and reflection to remove string-typed properties.

LambdaModel takes different approach. It tries to be as simple as possible and avoid all the reflection magic. With Java 8, we can use [references to methods](http://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) or short and easy to read [lambda expressions](http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html). Model, that access property name of object person can be written in this form:
```
IModel<String> nameModel = new LambdaModel<String>(person::getName, person::setName);
```
Model is typesafe, IDE knows about usage when you refactor, compiler will fail if you rename getter and forgot to update model. There is no such thing as "string encoded" property anymore and no reflection behind scene. You can provide your getter and setter as a lambda expressions. That gives you power to access nested objects:

```
final IModel<String> model = new LambdaModel<>(()->person.getChild().getName(), (val)->person.getChild().setName(val));
```

Implementation is based on [Supplier](http://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html) and [Consumer](http://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) functional interfaces from Java 8. 

Main benefits are: no magic, type safety, easy refactoring. Current known limitation is possibility to provide getter, that access different property than setter. There is nothing to prevent it. 
