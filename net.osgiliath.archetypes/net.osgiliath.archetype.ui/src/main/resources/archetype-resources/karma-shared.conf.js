var shared = function(config) {
	 
	config.set({
		plugins : [ 'karma-coverage', 'karma-ng-scenario',
				 'karma-phantomjs-launcher', 'karma-firefox-launcher' , 'karma-chrome-launcher' , 'karma-jasmine'],
		basePath : '',
		autoWatch : true,
		reporters : [ 'coverage' ],
		logLevel: config.LOG_DEBUG,
		loggers : [ {type: 'console'} ],
		browsers : [ 'Chrome' ],//'PhantomJS'
		captureTimeout : 5000,
		runnerPort : 9100,
		port : 8080,
		// these are default values anyway
		//singleRun : false,
		colors : true,
		preprocessors : {
			'src/main/javascript/scripts/**/*.js' : 'coverage'
		},
	});
};
shared.mainfolder = 'src/main/javascript/';
shared.bowercomponent = shared.mainfolder + 'bower_components/';


shared.sharedfiles = [ 
                      shared.bowercomponent + 'jquery/jquery.js',
                      shared.bowercomponent + 'angular/angular.js',
                      shared.bowercomponent + 'angular-bootstrap/ui-bootstrap-tpls.js',
                      shared.bowercomponent + 'angular-resource/angular-resource.js',
                      shared.bowercomponent + 'angular-cookies/angular-cookies.js',
                      shared.bowercomponent + 'angular-sanitize/angular-sanitize.js',
                      shared.bowercomponent + 'angular-route/angular-route.js',
                      shared.bowercomponent + 'angular-bootstrap/ui-bootstrap-tpls.min.js',
                      shared.bowercomponent + 'angular-ui/build/angular-ui.min.js',
                      shared.bowercomponent + 'ng-grid/build/ng-grid.min.js',
                      shared.mainfolder + 'components/stomp/stomp.js'
		 ];

module.exports = shared;