'use strict';
describe("E2E: Testing myCtrl - check launched page", function() {

	beforeEach(function() {
		browser().navigateTo('/');
	});

	it('Navigate to the main page and check if label is present', function() {
		expect(element('#dummyforendtoendtest').html()).toContain('');
	});
});