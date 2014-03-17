'use strict';
describe("E2E: Testing UserCtrl - check launched page", function() {

	beforeEach(function() {
		browser().navigateTo('/');
	});

	it('Navigate to the main page and check if label is present', function() {
		expect(element('#helloFieldLabel').html()).toContain('Message');
	});
});