'use strict';
describe('Controller: HelloCtrl', function () {

  // load the controller's module
  beforeEach(module('hello'));

  var HelloCtrl,scope, mockedService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HelloCtrl = $controller('HelloCtrl', {
      $scope: scope,
      stompservice: mockedService
    });
  }));

  it('should attach the helloMessage', function () {
	expect(scope.helloMessage).toBe('');
  });
});
