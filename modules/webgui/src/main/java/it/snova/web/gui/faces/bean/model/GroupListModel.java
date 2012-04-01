package it.snova.web.gui.faces.bean.model;

import it.snova.appframework.membership.data.DBIterator;
import it.snova.appframework.membership.data.DBIterator.GroupIterator;
import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.defaults.Defaults;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.Group;
import it.snova.web.gui.faces.bean.GroupBean;
import it.snova.web.gui.faces.bean.UserSessionBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "groupListModel")
@ViewScoped
public class GroupListModel extends LazyDataModel<GroupBean>
{
  private static final Logger logger = Logger.getLogger(GroupListModel.class.getName());

  private static final long serialVersionUID = 1L;
  
  private UserSessionBean userSession;

  private List<GroupBean> groups = new ArrayList<GroupBean>();
  private String selectedDomain = Defaults.ALL_DOMAINS;

  public GroupListModel()
  {
  }

  @Override
  public List<GroupBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, final Map<String,String> filters)
  {
//    System.out.println("first: " + first);
//    System.out.println("pageSize: " + pageSize);
//    System.out.println("sortField: " + sortField);
//    System.out.println("sortOrder: " + sortOrder.toString());
//    System.out.println("filter: " + filters.toString());

    groups.clear();
    
    try {
      UserManager userMgr = getUserSession().getUserManager();
      
      GroupIterator iter = new GroupIterator() {
        
        @Override
        public boolean process(Group g)
        {
          boolean add = true;
          if (add && filters.containsKey("name")) {
            add = g.getName() != null && g.getName().toLowerCase().contains(filters.get("name").toLowerCase());
          }
          if (add) {
            groups.add(create(g));
          }
          return false;
        }
      };
      Domain d = null;
      if (filters.containsKey("domain")) {
        String domain = filters.get("domain");
        d = userMgr.getDomain(domain);
      }
      
      userMgr.iterateGroups(d, new DBIterator<Group>()
          .setStart(first)
          .setLimit(pageSize)
          .setIterator(iter));

      int count = userMgr.getGroupCount(d);
      logger.fine("Group count: " + count);
      setRowCount(count);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return groups;
  }
  
  private GroupBean create(Group g)
  {
    GroupBean bean = new GroupBean(g.getId());
    bean.setDomain(g.getDomain().getName());
    bean.setName(g.getName());
    return bean;
  }
  
  public String getSelectedDomain()
  {
    return selectedDomain;
  }
  
  public void setSelectedDomain(String selectedDomain)
  {
    this.selectedDomain = selectedDomain;
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
