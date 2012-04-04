package it.snova.web.gui.faces.converter;

import it.snova.web.gui.faces.bean.UserBean;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(forClass = UserBean.class, value = "userPickListConverter")
public class UserConverter implements Converter
{
  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    Object ret = null;
    if (component instanceof PickList) {
      Object dualList = ((PickList) component).getValue();
      @SuppressWarnings("unchecked")
      DualListModel<UserBean> dl = (DualListModel<UserBean>) dualList;
      ret = getUserFromList(dl.getSource(), value);
      if (ret == null) {
        ret = getUserFromList(dl.getTarget(), value);
      }
    }
    return ret;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    String str = "";
    if (value instanceof UserBean) {
      str = Long.toString(((UserBean) value).getId());
    }
    return str;
  }

  private Object getUserFromList(List<UserBean> list, String value)
  {
    for (UserBean u : list) {
      String id = Long.toString(u.getId());
      if (value.equals(id)) {
        return u;
      }
    }
    return null;
  }

}
