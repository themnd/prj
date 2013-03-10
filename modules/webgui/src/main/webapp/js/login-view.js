LoginView = Backbone.View.extend({

  initialize: function(params) {
    this.template = _.template($('#loginTemplate').html());
    this.broker = params.broker;

    this.broker.on('logged-in', this.hideView, this);
    this.broker.on('logged-out', this.loggedOut, this);

    this.broker.on('srv-logged-in', this.srv_logged_in, this);
    this.broker.on('srv-logged-out', this.srv_logged_out, this);

    this.model = new LoginModel({
      apiModel: params.apiModel
    });
  },

  render: function() {
    $(this.el).html(this.template());
  },
  
  hideView: function() {
    $(this.el).html('');
  },
  
  checkLogin: function() {
    this.model.checkLogin();
  },
  
  loggedOut: function() {
    this.render();
  },

  srv_logged_in: function(data) {
    this.broker.trigger('logged-in');
  },

  srv_logged_out: function(data) {
    this.broker.trigger('logged-out');
  },
  
  events: {
    'click #login-btn': 'do_login'
  },
  
  do_login: function() {
    var name = $(this.el).find('#login-user-name').val();
    var pwd = $(this.el).find('#login-user-pwd').val();
    
    this.model.login(name, pwd);
  },

  save_settings: function() {
    var url_val = $(this.el).find('#url').val();

    if(url_val != undefined && url_val != null && url_val != "") {

      this.model.set('url', this.model.string_util(url_val));
      this.model.change();

      this.broker.trigger('notify-success', {
        header: 'Saved'
      });

      this.broker.trigger('save-settings');
      this.render();
    }
    else {
      this.broker.trigger('notify-error', {
        header: 'Warning!',
        body: 'Url is empty'
      });
    }
  }

});
