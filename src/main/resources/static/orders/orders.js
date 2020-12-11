angular.module('app').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.getOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET'
         })
            .then(function (response) {
                $scope.orders = response.data;
            });
    };

    $scope.getOrders();
});
