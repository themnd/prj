package it.snova.web.gui.faces.bean;

import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@ManagedBean(name = "adminDashboard")
public class AdminDashboardBean
{
  private static final Logger logger = Logger.getLogger(AdminDashboardBean.class.getName());

  private DashboardModel model;

  public AdminDashboardBean()
  {
    logger.info("AdminDashboardBean");
    
    model = new DefaultDashboardModel();
    DashboardColumn column1 = new DefaultDashboardColumn();
    DashboardColumn column2 = new DefaultDashboardColumn();
    column1.addWidget("Domains");
    column2.addWidget("Users");
    column2.addWidget("Groups");
    model.addColumn(column1);
    model.addColumn(column2);
  }
  
  public DashboardModel getModel()
  {
    logger.info("AdminDashboardBean.getModel");
    
    return model;
  }

}
