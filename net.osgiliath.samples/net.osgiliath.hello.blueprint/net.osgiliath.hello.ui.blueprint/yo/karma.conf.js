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
		dir : '../target/coverage/',
		file : 'lcov.info'
	};
	config.frameworks = [ "jasmine" ];
	config.files = shared.sharedfiles.concat([
			'app/scripts/services/stompservice.js',
			'app/scripts/directives/hello.js',
			'app/scripts/controllers/hello.js',
			/* 'test/mock/*.js', */'test/spec/**/*.js' ]);
	config.urlRoot = '/__unit/';
};

module.exports = unit;
