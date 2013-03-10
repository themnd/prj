ApiModel = Backbone.Model.extend({

  defaults: {
    url: 'http://localhost:8080'
  },

  initialize: function() {
    this.broker = broker;
    this.apiUrl = 'api/v1';
    this.sessionid = null;
  },
  
  get_cookie: function() {
    $.cookie.json = true;
    return $.cookie('_myapp_');
  },
  
  set_cookie: function(data) {
    $.cookie.json = true;
    $.cookie('_myapp_', data);
  },
  
  del_cookie: function() {
    $.removeCookie('_myapp_');
  },
  
  set_sessionid: function(sid) {
    this.sessionid = sid;
  },
  
  srv_ajax_post: function(ajax_url, ajax_data, f_success, f_error) {
    $.ajax({
      type: "POST",
      url: this.apiUrl + ajax_url,
      data: ajax_data,
      success: f_success,
      error: f_error
    });    
  },

  srv_ajax_auth_post: function(ajax_url, ajax_data, f_success, f_error) {
    $.ajax({
      type: "POST",
      url: this.apiUrl + ajax_url,
      data: ajax_data,
      headers: {
        'X-Auth-SID': this.sessionid
      },
      success: f_success,
      error: f_error
    });
  }
  
});