MainView = Backbone.View.extend({
  
  initialize : function(params) {
    this.template = _.template($('#mainTemplate').html());
    this.broker = params.broker;
    
    this.broker.on('logged-in', this.loggedIn, this);
    this.broker.on('logged-out', this.hideView, this);
    this.broker.on('edit', this.conceal, this);
    this.broker.on('settings', this.render, this);
    
    this.model = new MainModel();
  },
  
  render : function() {
    $(this.el).html(this.template({
      url : this.model.get('url')
    }));
  },
  
  hideView : function() {
    $(this.el).html('');
  },
  
  events : {
    'click #logout-btn' : 'do_logout'
  },
  
  loggedIn : function() {
    this.render();
  },
  
  do_logout: function() {
    this.broker.trigger('do-logout');
  },
  
  save_settings : function() {
    var url_val = $(this.el).find('#url').val();
    
    if (url_val != undefined && url_val != null && url_val != "") {
      
      this.model.set('url', this.model.string_util(url_val));
      this.model.change();
      
      this.broker.trigger('notify-success', {
        header : 'Saved'
      });
      
      this.broker.trigger('save-settings');
      this.render();
    } else {
      this.broker.trigger('notify-error', {
        header : 'Warning!',
        body : 'Url is empty'
      });
    }
  }

});
