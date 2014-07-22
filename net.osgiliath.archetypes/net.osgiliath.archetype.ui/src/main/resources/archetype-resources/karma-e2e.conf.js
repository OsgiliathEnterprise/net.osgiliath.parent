// Karma E2E configuration
var shared = require('./karma-shared.conf');
// list of files / patterns to load in the browser
// TODO instead of the app/scripts directive, you can list your files one by
// one, excluding those that are mocked
var e2e = function(config) {
	shared(config);
	config.coverageReporter = {
		type : 'lcovonly',
		dir : 'target/coverage/e2e/',
		file : 'lcov.info'
	};
	config.frameworks = [ "ng-scenario" ];
	config.urlRoot = '/__e2e/';

	config.files = shared.sharedfiles.concat([
			e2e.mainfolder + 'scripts/services/stompservice.js',
			e2e.mainfolder + 'scripts/sample/myDirective.js',
			e2e.mainfolder + 'scripts/sample/myController.js', 
			e2e.testfolder + 'e2e/indexTest.js' ]);
	config.proxies = {
		'/' : 'http://localhost:9001/'
	};
	// list of files to exclude

	// test results reporter to use
	// possible values: dots || progress || growl

};
e2e.mainfolder = 'src/main/javascript/';
e2e.bowercomponent = shared.mainfolder + 'bower_components/';
e2e.testfolder = 'src/test/javascript/';

module.exports = e2e;