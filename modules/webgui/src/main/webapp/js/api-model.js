ApiModel = Backbone.Model.extend({

  defaults: {
    url: 'http://localhost:8080'
  },

  initialize: function() {
    this.broker = broker;
    this.apiUrl = 'api/v1';
    this.sessionid = null;
    this.userId = null;
    this.userName = null;
  },
  
  init_from_cookie: function() {
    var data = $.cookie('_myapp_');
    this.init_from_data(data, true);
    return data;
  },
  
  save_to_cookie: function(data) {
    $.cookie('_myapp_', data);
  },

  remove_cookie: function() {
    $.removeCookie('_myapp_');
  },
  
  init_from_data: function(data, doNotSaveToCookie) {
    this.sessionid = data.sessionId;
    this.userId = data.id;
    this.userName = data.name;
    
    if (!doNotSaveToCookie) {
      this.save_to_cookie(data);
    }
  },
  
  remove_session_data: function() {
    this.remove_cookie();
    this.sessionid = null;
    this.userId = null;
    this.userName = null;
  },
  
  haveSession: function() {
    return this.sessionid;
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
  },
  
  srv_ajax_auth_get: function(ajax_url, ajax_data, f_success, f_error) {
    $.ajax({
      type: "GET",
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