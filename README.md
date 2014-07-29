wicket-functional
=================

Wicket components and utilities that uses functional features from Java 8

Lambda Model
=================
Lambda Model is replacement for unsafe PropertyModel. Good old Property Model works just fain. It has only one problem. It is not type safe. 
And that is something we really don't want in Java code.For example suppose, we have object person of type Person, with property name of type String. 
Then property model for person name looks like this. 

```
IModel<String> nameModel = new PropertyModel<String>(person, "name");
```
Everything works well, it is easy to write and elegant. But one day you decide, that name should not be String, rather object of type Name, with properties firstName and lastName.
Refactoring will help you to rewrite code. But compiler has no chance to recognize, that old PropertyModel is now accessing different type and will return wrong data.

Or you decide to rename property (and getter and setter) to fullName. And error is introduced. Compiler will not check, that old property name does not exist anymore. This and other problems of PropertyModel are also descibed in article [A CLEAN APPROACH TO WICKET MODELS](http://blog.eluder.org/2012/02/a-clean-approach-to-wicket-models/) by [@trautonen](https://github.com/trautonen/).

That's not happening with LambdaModel. LambdaModel is typesafe! Same functionality can be created like this:
```
IModel<String> nameModel = new LambdaModel<String>(person::getName, person::setName);
```
Everything is typesafe, IDE knows about usage when you refactor, compiler will fail if you rename getter and forgot to update model. There is no such thing as "string encoded" property anymore. 
