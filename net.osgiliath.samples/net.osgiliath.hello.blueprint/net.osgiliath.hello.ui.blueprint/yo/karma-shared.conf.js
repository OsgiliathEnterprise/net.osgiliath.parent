var shared = function(config) {
	config.set({
		plugins : [ 'karma-coverage', 'karma-ng-scenario',
				 'karma-phantomjs-launcher', 'karma-firefox-launcher' , 'karma-chrome-launcher' , 'karma-jasmine'],
		basePath : '',
		autoWatch : false,
		reporters : [ 'coverage' ],
		logLevel: config.LOG_DEBUG,
		loggers : [ {type: 'console'} ],
		proxies : {
			'/' : 'http://localhost:9009/'
		},
		browsers : [ 'PhantomJS' ],
		captureTimeout : 5000,
		runnerPort : 9100,
		port : 8089,
		// these are default values anyway
		singleRun : false,
		colors : true,
		preprocessors : {
			'**/app/scripts/**/*.js' : 'coverage'
		},
	});
};

shared.sharedfiles = [ 'app/bower_components/jquery/jquery.js',
		'app/bower_components/angular/angular.js',
		'app/bower_components/angular-resource/angular-resource.js',
		'app/bower_components/angular-cookies/angular-cookies.js',
		'app/bower_components/angular-sanitize/angular-sanitize.js',
		'app/bower_components/angular-mocks/angular-mocks.js',
		'app/components/stomp/stomp.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-affix.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-alert.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-dropdown.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-tooltip.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-modal.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-transition.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-button.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-popover.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-typeahead.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-carousel.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-scrollspy.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-collapse.js',
		'app/bower_components/bootstrap-sass/js/bootstrap-tab.js',
		'app/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js',
		'app/bower_components/angular-ui/build/angular-ui.min.js',
		'app/bower_components/ng-grid/build/ng-grid.min.js'
		 ];

module.exports = shared;