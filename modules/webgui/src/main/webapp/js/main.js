$.cookie.json = true;

/* Backbone objects which require global scope. */
var broker = _.clone(Backbone.Events);
var router = Backbone.Router.extend({
  initialize: function() {
    this.broker = broker;
  },

  routes: {
    "edit/:id": "open_article"
  },

  open_article: function(id) {
    this.broker.trigger('open-article', id);
  }
});

/* Instantiates program within function scope */
var application = function() {

  // Singleton
  if (arguments.callee.instance) {
    return arguments.callee.instance;
  }
  
  arguments.callee.instance = this;

  init = function() {

    // Ajax operation flag
    broker.ajax_operation = false;

    /*
    // Load from browser storage (HTML 5 feature)
    if (localStorage.getItem('app_data') === undefined) {
      var content = new Content_Collection();
    } else {
      var content = new Content_Collection(JSON.parse(localStorage.getItem('app_data')));
    }

    this.navbar = new Navbar({
      broker: broker,
      collection: content,
      el: ('#navbar')
    });

    this.overview = new Overview({
      broker: broker,
      collection: content,
      el: ('#overview')
    });

    this.not = new Notification({
      broker: broker,
      collection: content,
      el: ('#notification')
    });

    this.editor = new Editor({
      broker: broker,
      el: ('#article'),
      collection: content
    });

    this.settings = new SettingsView({
      broker: broker,
      collection: content,
      el: ('#settings')
    });
    */
    this.apiModel = new ApiModel();
    
    this.login = new LoginView({
      broker: broker,
      el: ('#login-dlg'),
      apiModel: this.apiModel
    });
    this.main = new MainView({
      broker: broker,
      el: ('#main-content'),
      apiModel: this.apiModel
    });
    
    this.login.checkLogin();
    
  } ();
}

app = new application();
router = new router();
Backbone.history.start();
