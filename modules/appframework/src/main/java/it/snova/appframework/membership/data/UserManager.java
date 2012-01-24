package it.snova.appframework.membership.data;

import it.snova.appframework.security.PasswordEntity;
import it.snova.appframework.security.PasswordGenerator;
import it.snova.apptables.data.UsersTable;
import it.snova.apptables.framework.Context;
import it.snova.hbaselib.framework.HClient;
import it.snova.hbaselib.schema.ScanProcessorSingleResult;
import it.snova.hbaselib.schema.SequenceBuilder;
import it.snova.hbaselib.schema.TableBuilder;

import java.io.IOException;

public class UserManager
{
  static PasswordGenerator pgen = PasswordGenerator.factory();

  HClient client;
  
  public UserManager(HClient client)
  {
    this.client = client;
  }
  
  public UserBean getUser(final String name) throws Exception
  {
    TableBuilder<UsersTable> tb = client.getSchemaBuilder(UsersTable.class).getTableBuilder();
    ScanProcessorSingleResult<UsersTable> scan =
      (ScanProcessorSingleResult<UsersTable>) tb.scan().process(
        new ScanProcessorSingleResult<UsersTable>(UsersTable.class) {
          @Override
          public boolean processSingleResult(UsersTable result) throws IOException
          {
            if (result.getName().equals(name)) {
              return true;
            }
            return false;
          }
        });
    if (scan.result != null) {
      return createFromData(scan.result);
    }
    return null;
  }
  
  public UserBean loginUser(final String name, final String pwd) throws Exception
  {
    return validateUser(getUser(name), pwd);
  }
  
  public UserBean resetPassword(UserBean u)
  {
    PasswordEntity p = pgen.generatePassword();
    u.setPwd(p.getPwd());
    u.setSalt(p.getSalt());
    return u;
  }
  
  public UserBean createNewUser() throws Exception
  {
    TableBuilder<UsersTable> tb = client.getSchemaBuilder(UsersTable.class).getTableBuilder();
    
    SequenceBuilder sb = tb.getSequence();

    String userId = Long.toHexString(sb.getNextId());
    UserBean u = new UserBean();
    u.setId(userId);
    PasswordEntity p = pgen.generatePassword();
    u.setPwd(p.getPwd());
    u.setSalt(p.getSalt());
    return u;
  }
  
  public void createUser(UserBean u) throws Exception
  {
    createUser(null, createFromUser(u));
  }

  public void createUser(Context c, UserBean u) throws Exception
  {
    createUser(c, createFromUser(u));
  }

  private void createUser(Context c, UsersTable u) throws Exception
  {
    TableBuilder<UsersTable> tb = client.getSchemaBuilder(UsersTable.class).getTableBuilder();
    if (c != null) {
      tb.add(c.with(u));
    } else {
      tb.add(u);
    }
  }
  
  private UserBean validateUser(UserBean u, String pwd)
  {
    if (u != null) {
      PasswordEntity p = new PasswordEntity(u.getSalt(), pwd);
      if (pgen.verifyPassword(p, u.getPwd())) {
        return u;
      }
    }
    return null;
  }

  private UserBean createFromData(UsersTable u)
  {
    UserBean user = new UserBean();
    user.setId(u.getId());
    user.setName(u.getName());
    user.setEmail(u.getEmail());
    user.setPwd(u.getPwd());
    user.setSalt(u.getSalt());
    return user;
  }
  
  private UsersTable createFromUser(UserBean u)
  {
    UsersTable user = new UsersTable();
    user.setId(u.getId());
    user.setName(u.getName());
    user.setEmail(u.getEmail());
    user.setPwd(u.getPwd());
    user.setSalt(u.getSalt());
    return user;
  }

}
