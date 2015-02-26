'use strict';

describe('Controller: sampleController', function () {

  // load the controller's module
  beforeEach(module('myApp'));

  var scope, mockedService;
  var myCtrl;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    myCtrl = $controller('sampleController', {
      $scope: scope,
      stompservice: mockedService
    });
  }));

  it('should attach the sampleMessage', function () {
//    expect(scope.sampleMessage).toBe('');
  });
});
