package issue;

import com.acme.demonoaliases.DemoNoAliasesApplication;
import com.acme.demonoaliases.DemoNoAliasesApplicationBuilder;
import com.acme.demonoaliases.cookietypes.schema.cookie_types.CookieTypes;
import com.acme.demonoaliases.cookietypes.schema.cookie_types.CookieTypesManager;
import com.acme.demonoaliases.cookietypes.schema.ingredients.Ingredients;
import com.acme.demowithaliases.DemoWithAliasesApplication;
import com.acme.demowithaliases.DemoWithAliasesApplicationBuilder;
import com.acme.demowithaliases.cookietypes.schema.cookie_types.CookieTypeEntity;
import com.acme.demowithaliases.cookietypes.schema.cookie_types.CookieTypeEntityManager;
import com.acme.demowithaliases.cookietypes.schema.ingredients.IngredientsEntity;
import com.speedment.runtime.join.JoinComponent;
import org.junit.Before;

/**
 * todo comment.
 */
public class Test {

  public static final String CONNECTION_URL =
    "This test does not require an actual database instance (hence no JDBC URL here) because " +
    "the exception triggers before Speedment connects to the DBMS for the first time. " +
    "If you need the actual schema matching the provided " +
    "json-alias-mismatch-demo/src/main/json/speedment.json " +
    "you may create it using " +
    "json-alias-mismatch-demo/config/database/init.sql " +
    "in a Postgres instance of your choice. Then come back and set its JDBC URL here.";
  private DemoNoAliasesApplication joinsWorkingApplication;
  private DemoWithAliasesApplication joinsBrokenApplication;

  private JoinComponent workingJoinComponent;
  private JoinComponent brokenJoinComponent;

  @Before
  public void setUp()
  {
    joinsBrokenApplication = new DemoWithAliasesApplicationBuilder()
      .withConnectionUrl(CONNECTION_URL)
      .withSkipCheckDatabaseConnectivity()
      .withSkipLogoPrintout()
      .build();

    joinsWorkingApplication = new DemoNoAliasesApplicationBuilder()
      .withConnectionUrl(CONNECTION_URL)
      .withSkipCheckDatabaseConnectivity()
      .withSkipLogoPrintout()
      .build();

    brokenJoinComponent = joinsBrokenApplication.getOrThrow(JoinComponent.class);
    workingJoinComponent = joinsWorkingApplication.getOrThrow(JoinComponent.class);
  }

  @org.junit.Test
  public void testSimpleJoinBuildingDoesNotThrowISEWhenUsingAliases()
  {
    brokenJoinComponent.from(CookieTypeEntityManager.IDENTIFIER)
      .innerJoinOn(IngredientsEntity.OWNER).equal(CookieTypeEntity.TYPE)
      .build();
  }

  @org.junit.Test
  public void testSimpleJoinBuildingDoesNotThrowISEWhenUsingNoAliases()
  {
    workingJoinComponent.from(CookieTypesManager.IDENTIFIER)
      .innerJoinOn(Ingredients.OWNER).equal(CookieTypes.TYPE)
      .build();

  }
}
