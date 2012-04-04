package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.DBIterator;
import it.snova.appframework.membership.data.DBIterator.UserIterator;
import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.Group;
import it.snova.dbschema.table.User;
import it.snova.web.gui.faces.bean.model.UserListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.primefaces.model.DualListModel;

public class GroupUsersDialogBean implements Serializable
{
  private static final Logger logger = Logger.getLogger(GroupUsersDialogBean.class.getName());

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private UserSessionBean userSession;
  
  private GroupBean selectedGroup;

  private DualListModel<UserBean> model = null;
  private List<UserBean> source = new ArrayList<UserBean>();
  private List<UserBean> target = new ArrayList<UserBean>();

  public GroupUsersDialogBean(GroupBean selectedGroup)
  {
    this.selectedGroup = selectedGroup;
  }
  
  public void init()
  {
    source.clear();
    target.clear();
    
    if (selectedGroup != null) {
      UserManager mgr = getUserSession().getUserManager();
      Domain d = mgr.getDomain(selectedGroup.getDomain());

      try {
        Group g = mgr.getGroup(selectedGroup.getId());
        final Collection<User> users = g.getMembers();
        
        mgr.iterate(d, new DBIterator<User>()
          .setIterator(
            new UserIterator() {
              
              @Override
              public boolean process(User u)
              {
                if (!users.contains(u)) {
                  source.add(UserListModel.create(u));
                }
                return false;
              }
            }));
        
        for (User u: users) {
          target.add(UserListModel.create(u));
        }
        
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
    model = new DualListModel<UserBean>(new ArrayList<UserBean>(source), new ArrayList<UserBean>(target));
    source.clear();
    target.clear();
  }

  public DualListModel<UserBean> getModel()
  {
    return model;
  }

  public void setModel(DualListModel<UserBean> model)
  {
    this.model = model;
    
    save();
  }

  public void save()
  {
    logger.info("Save members ");
    
    UserManager mgr = getUserSession().getUserManager();
    
    Group g = mgr.getGroup(this.selectedGroup.getId());
    
    List<UserBean> users = model.getTarget();
    
    long[] ids = new long[users.size()];
    for (int idx = 0; idx < users.size(); idx++) {
      ids[idx] = users.get(idx).getId();
    }
    mgr.setGroupMembers(g, ids);
  }

  public UserSessionBean getUserSession()
  {
    return userSession;
  }

  public void setUserSession(UserSessionBean userSession)
  {
    this.userSession = userSession;
  }
  
}
