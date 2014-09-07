// Karma configuration
var shared = require('./karma-shared.conf');
// base path, that will be used to resolve files and exclude
// list of files / patterns to load in the browser
// TODO instead of the app/scripts directive, you can list your files one by
// one, excluding those that are mocked
var unit = function(config) {
	shared(config);
	exclude: [], config.coverageReporter = {
		type : 'lcovonly',
		dir : 'target/coverage/',
		file : 'lcov.info'
	};
	config.frameworks = [ "jasmine" ];
	config.files = shared.sharedfiles.concat([
			unit.mainfolder + 'scripts/services/stompservice.js',
			unit.mainfolder + 'scripts/main/mainRoute.js',
			unit.mainfolder + 'scripts/sample/myDirective.js',
			unit.mainfolder + 'scripts/sample/myController.js',
			unit.bowercomponent + 'angular-mocks/angular-mocks.js',
			/* 'test/mock/*.js', */unit.testfolder + '/spec/**/*.js' ]);
	config.urlRoot = '/__unit/';
	config.port = 8081;
	config.proxies = {
			'/' : 'http://localhost:9001/'
	};
};
unit.mainfolder = 'src/main/javascript/';
unit.bowercomponent = shared.mainfolder + 'bower_components/';
unit.testfolder = 'src/test/javascript/';

module.exports = unit;
