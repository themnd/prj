LoginModel = Backbone.Model.extend({

  defaults: {
    url: 'http://localhost:8080'
  },

  initialize: function(params) {
    this.broker = broker;
    this.broker.on('save-settings', this.save_settings, this);
    this.broker.on('do-logout', this.do_logout, this);

    this.apiModel = params.apiModel;
    this.loginUrl = '/login';

    var stored_url = localStorage.getItem('setting-url');

    if(stored_url != undefined && stored_url != null && stored_url != "") {
      this.set({ url: stored_url });
    }
    else {
      localStorage.setItem('setting-url', this.string_util(this.get('url')));
    }
  },
  
  checkLogin: function() {
    if (this.apiModel.init_from_cookie()) {
      this.srv_loggedin();
    } else {
      this.broker.trigger('logged-out');
    }
  },
  
  login: function(name, pwd) {
    this.srv_login(name, pwd);
  },
  
  do_logout: function() {
    if (this.apiModel.haveSession()) {
      this.srv_logout();
    } else {
      this.broker.trigger('logged-out');
    }
  },
  
  srv_login_ajax_post: function(action, ajax_data, f_success, f_error) {
    this.apiModel.srv_ajax_post(
        this.loginUrl + '/' + action,
        ajax_data,
        f_success,
        f_error);
  },

  srv_login_ajax_auth_post: function(action, ajax_data, f_success, f_error) {
    this.apiModel.srv_ajax_auth_post(
        this.loginUrl + '/' + action,
        ajax_data,
        f_success,
        f_error);
  },

  srv_loggedin: function() {
    
    var self = this;
    
    this.srv_login_ajax_auth_post(
      'loggedin',
      null,
      function(data) {
        self.broker.trigger('srv-logged-in', data);
      },
      function(e) {
        self.broker.trigger('srv-logged-out', e);
      }
    );
  },
  
  srv_login: function(name, pwd) {
    
    var self = this;
    
    this.srv_login_ajax_post(
      'authenticate',
      {
        'username' : name,
        'password' : pwd
      },
      function(data) {
        self.apiModel.init_from_data(data);
        self.broker.trigger('srv-logged-in', data);
      },
      function(e) {
        self.broker.trigger('srv-logged-out', e);
      }
    );
  },
  
  srv_logout: function() {
    
    var self = this;
    
    this.srv_login_ajax_auth_post(
      'logout',
      null,
      function(data) {
        self.apiModel.remove_session_data();        
        self.broker.trigger('srv-logged-out', data);
      },
      function(e) {
        self.broker.trigger('srv-logged-out', e);
      }
    );
  },
  
  save_settings: function() {
    var obj = JSON.parse(JSON.stringify(this));
    localStorage.setItem('setting-url', this.string_util(obj.url));
  },

  string_util: function (str) {
    var len = str.length
    
    if(str.charAt(len-1) == "/") {
      str = str.substr(0,len-1)
    }
    return str;
  }
});