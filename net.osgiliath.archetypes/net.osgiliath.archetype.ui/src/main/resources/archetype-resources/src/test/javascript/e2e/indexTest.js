'use strict';
describe("E2E: Testing ${artifactId}Ctrl - check launched page", function() {

	beforeEach(function() {
		browser().navigateTo('/');
	});

	it('Navigate to the main page and check if label is present', function() {
		expect(element('#dummyforetwoetest').html()).toContain('');
	});
});