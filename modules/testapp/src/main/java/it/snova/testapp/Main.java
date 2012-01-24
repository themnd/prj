package it.snova.testapp;

import it.snova.appframework.membership.data.UserBean;
import it.snova.appframework.membership.data.UserManager;
import it.snova.appinstall.AppInstaller;
import it.snova.hbaselib.framework.HClient;

public class Main
{
  public static void main(String [] args)
  {
    Main main = new Main();
    main.doHBaseTests();
    System.exit(0);
  }

  UserManager userManager  = null;
  
  private void doHBaseTests()
  {
    try {
      HClient client = new HClient().init();
      userManager = new UserManager(client);

//      AppInstaller.install(client);
      
//      for (int i = 0; i < 100; i++) {
//        
//        UserBean u = userManager.createNewUser();
//        u.setName("u" + i);
//        u.setEmail("u@u");
//        
//        userManager.createUser(u);
//      }
      
      boolean b = testLogin(client, "admin", "admin");
      
      System.out.println("login: " + b);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private boolean testLogin(HClient client, final String name, final String pwd) throws Exception
  {
    UserBean user = userManager.loginUser(name, pwd);
    return user != null;
  }
  
}
