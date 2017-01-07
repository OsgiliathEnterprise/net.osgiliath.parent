var shared = function(config) {
	 
	config.set({
		plugins : [ 'karma-coverage', 'karma-ng-scenario',
				 'karma-phantomjs-launcher'/*, 'karma-firefox-launcher' , 'karma-chrome-launcher' */, 'karma-jasmine'],
		basePath : '',
		autoWatch : true,
		reporters : [ 'coverage','progress' ],
		logLevel: config.LOG_DEBUG,
		loggers : [ {type: 'console'} ],
		browsers : [ 'PhantomJS' ],//'Firefox'
		captureTimeout : 5000,
		runnerPort : 9100,
		port : 8080,
		// these are default values anyway
		//singleRun : false,
		colors : true,
		preprocessors : {
			'target/classes/scripts/**/*.js' : 'coverage'
		},
	});
};
shared.mainfolder = 'target/classes/';
shared.bowercomponent = shared.mainfolder + 'bower_components/';


shared.sharedfiles = [ 
                      shared.bowercomponent + 'angular/angular.js',
                      shared.bowercomponent + 'jquery/dist/jquery.js',
                      shared.bowercomponent + 'angular-bootstrap/ui-bootstrap-tpls.js',
                      shared.bowercomponent + 'angular-resource/angular-resource.js',
                      shared.bowercomponent + 'angular-cookies/angular-cookies.js',
                      shared.bowercomponent + 'angular-sanitize/angular-sanitize.js',
                      shared.bowercomponent + 'angular-mocks/angular-mocks.js',
                      shared.bowercomponent + 'angular-resource/angular-resource.js',
                      shared.bowercomponent + 'angular-route/angular-route.js',
                      shared.bowercomponent + 'angular-sanitize/angular-sanitize.js',
                      shared.bowercomponent + 'bootstrap-sass/assets/javascripts/bootstrap.js',
                      shared.bowercomponent + 'ng-grid/build/ng-grid.min.js',
                      shared.mainfolder + 'components/stomp/stomp.js'
		 ];

module.exports = shared;