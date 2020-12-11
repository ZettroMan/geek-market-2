angular.module('app').controller('cartController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market';

    $scope.cartContentRequest = function () {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET'
        })
            .then(function (response) {
                console.log(response.data);
                $scope.cart = response.data;
            });
    };

    $scope.decrementItem = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/dec/' + productId,
            method: 'GET'
        })
            .then(function (response) {
                $scope.cartContentRequest();
            });
    };

    $scope.removeItem = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/remove/' + productId,
            method: 'GET'
        })
            .then(function (response) {
                $scope.cartContentRequest();
            });
    };

    $scope.incrementItem = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/' + productId,
            method: 'GET'
        })
            .then(function (response) {
                $scope.cartContentRequest();
            });
    };

    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
            params: {
                deliveryAddress: $scope.deliveryAddress
            }
        })
            .then(function (response) {
                alert('Заказ оформлен');
                $scope.cartContentRequest();
                $location.path('/orders');
            });
    }

    $scope.loadProfile = function () {
        $http.get(contextPath + '/api/v1/profile')
            .then(function (response) {
                $scope.profile = response.data;
                $scope.cartContentRequest();
            });
    }

    $scope.loadProfile();
});
