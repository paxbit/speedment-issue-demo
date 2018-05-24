package issue;

import com.acme.issuedemo.IssueDemoApplication;
import com.acme.issuedemo.IssueDemoApplicationBuilder;
import com.acme.issuedemo.cookietypes.schema.cookie_types.CookieTypeEntity;
import com.acme.issuedemo.cookietypes.schema.cookie_types.CookieTypeEntityManager;
import com.acme.issuedemo.cookietypes.schema.ingredients.IngredientsEntity;
import com.speedment.runtime.join.JoinComponent;
import org.junit.Before;

/**
 * todo comment.
 */
public class Test {

  private IssueDemoApplication demoApplication;
  private JoinComponent joinComponent;

  @Before
  public void setUp()
    throws Exception
  {
    demoApplication = new IssueDemoApplicationBuilder()
      .withConnectionUrl(
        "This test does not require an actual database instance (hence no JDBC URL here) because " +
        "the exception triggers before Speedment connects to the DBMS for the first time. " +
        "If you need the actual schema matching the provided " +
        "json-alias-mismatch-demo/src/main/json/speedment.json " +
        "you may create it using " +
        "json-alias-mismatch-demo/config/database/init.sql " +
        "in a Postgres instance of your choice. Then come back and set its JDBC URL here."
      )
      .withSkipCheckDatabaseConnectivity()
      .withSkipLogoPrintout()
      .build();

    joinComponent = demoApplication.getOrThrow(JoinComponent.class);
  }

  @org.junit.Test
  public void testSimpleJoinBuildingDoesNotThrowISEWhenUsingAliases()
  {
    joinComponent.from(CookieTypeEntityManager.IDENTIFIER)
      .innerJoinOn(IngredientsEntity.OWNER).equal(CookieTypeEntity.TYPE)
      .build();

  }
}
