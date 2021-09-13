angular.module('app').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://api.mikhail-stepanov.com:5555';

    $scope.showMyOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.MyOrders = response.data;
        });
    };

    $scope.showMyOrders();
});
