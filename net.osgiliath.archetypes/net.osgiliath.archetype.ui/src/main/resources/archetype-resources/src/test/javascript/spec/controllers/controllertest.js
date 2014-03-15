'use strict';

describe('Controller: ${artifactId}Ctrl', function () {

  // load the controller's module
  beforeEach(module('${artifactId}'));

  var scope, mockedService;
  var ${artifactId}Ctrl;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ${artifactId}Ctrl = $controller('${artifactId}Ctrl', {
      $scope: scope,
      stompservice: mockedService
    });
  }));

  it('should attach the ${artifactId}Message', function () {
//    expect(scope.helloMessage).toBe('');
  });
});
