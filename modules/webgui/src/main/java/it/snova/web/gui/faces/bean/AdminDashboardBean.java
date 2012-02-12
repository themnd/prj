package it.snova.web.gui.faces.bean;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@ManagedBean(name = "adminDashboard")
public class AdminDashboardBean
{
  private DashboardModel model;

  public AdminDashboardBean()
  {
    model = new DefaultDashboardModel();
    DashboardColumn column1 = new DefaultDashboardColumn();
    DashboardColumn column2 = new DefaultDashboardColumn();
    column1.addWidget("Users");
    column1.addWidget("Groups");
    model.addColumn(column1);
    model.addColumn(column2);
  }
  
  public DashboardModel getModel()
  {
    return model;
  }

}
