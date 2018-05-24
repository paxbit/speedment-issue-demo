# Speedment Issue Demo
This repository is supposed to make it easier for others to replicate Speedment issues I encountered.

### Sub-Project json-alias-mismatch-demo

**Affected versions: Speedment 3.1.1 Homer only.**

Execute `gradle speedmentGenerateModel` first to generate the model classes mentioned below. 

##### Issue Description

This demo shows how the Speedment join capability breaks when custom Java aliases are used for 
table, schema or dmbs names. Setting custom aliases is a feature of the SpeedmentTool UI. 

Speedment code generation uses at least two approaches of table identification.
1. The generated entity managers (e.g. GeneratedCookieTypeEntityManager) always seem to use
   the customized "alias" properties of the table, schema or dbms for the creation of the IDENTIFIER
   property. That property is required to provide the first manager to the JoinComponent when opening 
   up a new join.
   
2. OTOH the generated field descriptors of entity classes (e.g. GeneratedCookieTypeEntity.TYPE) always 
     seem to use the "id" properties for their Identifiers to describe what table they belong to.
     See: `com.acme.issuedemo.cookietypes.schema.cookie_types.generated.GeneratedCookieTypeEntity.Identifier.getTableId()`

##### Run tests
Run `gradle test`

I prepared two Speedment model descriptors:
1. json-alias-mismatch-demo/src/main/json/model-with-aliases.json    (**broken**)
1. json-alias-mismatch-demo/src/main/json/model-without-aliases.json (**working**)

The build will output the generated model classes to:
1. json-alias-mismatch-demo/src/main/generated/broken
1. json-alias-mismatch-demo/src/main/generated/working

The test unit `issue.Test` will try to use both generated models.  
The test `testSimpleJoinBuildingDoesNotThrowISEWhenUsingAliases` should fail.
The test `testSimpleJoinBuildingDoesNotThrowISEWhenUsingNoAliases` should work.
     
##### Result     

See class `issue.Test`. The two above mentioned identification conventions seem to cause asserts
made when building a join to fail. The relevant exception message then reads:
`java.lang.IllegalStateException: The field type from join stage 2 is not associated with any of the tables in the join: ingredients, CookieTypeEntity
  at com.speedment.runtime.join.internal.component.join.AbstractJoinBuilder.assertFieldIn(AbstractJoinBuilder.java:154)
  at com.speedment.runtime.join.internal.component.join.AbstractJoinBuilder.assertFieldsAreInJoinTables(AbstractJoinBuilder.java:142)
  at com.speedment.runtime.join.internal.component.join.JoinBuilder2Impl.build(JoinBuilder2Impl.java:81)
  at com.speedment.runtime.join.builder.JoinBuilder2.build(JoinBuilder2.java:48)
  at issue.Test.testJoinFails(Test.java:40)`
