MainModel = Backbone.Model.extend({

  defaults: {
    url: 'http://localhost:8080'
  },

  initialize: function() {
    this.broker = broker;
    this.broker.on('save-settings', this.save_settings, this);
   
    var stored_url = localStorage.getItem('setting-url');

    if(stored_url != undefined && stored_url != null && stored_url != "") {
      this.set({ url: stored_url });
    }
    else {
      localStorage.setItem('setting-url', this.string_util(this.get('url')));
    }
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