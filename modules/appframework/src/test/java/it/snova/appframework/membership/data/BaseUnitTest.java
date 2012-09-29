package it.snova.appframework.membership.data;

import it.snova.appframework.context.ConnectorOptions;
import it.snova.appframework.context.Context;
import junit.framework.TestCase;

public abstract class BaseUnitTest extends TestCase
{
  Context context;

  @Override
  protected void setUp() throws Exception
  {
    super.setUp();

    ConnectorOptions options = new ConnectorOptions()
    .dbname("app")
    .username("root")
    .password("root");

    context = new Context().setOptions(options).init();
  }

}
