(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'main/main.html'
            })
            .when('/auth', {
                templateUrl: 'auth/auth.html',
                controller: 'authController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order', {
                templateUrl: 'cart/order.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/profile', {
                templateUrl: 'profile/profile.html',
                controller: 'profileController'
            })
            .when('/register', {
                templateUrl: 'register/register.html',
                controller: 'registerController'
            })
            .otherwise({
                redirectTo: '/'
            });

        $httpProvider.interceptors.push(function ($q, $location) {
            return {
                'responseError': function (rejection, $localStorage, $http) {
                    var defer = $q.defer();
                    if (rejection.status == 401 || rejection.status == 403) {
                        console.log('error: 401-403');
                        $location.path('/auth');
                        if (!(localStorage.getItem("localUser") === null)) {
                            delete $localStorage.currentUser;
                            $http.defaults.headers.common.Authorization = '';
                            console.log('zxc');
                        }
                        console.log(rejection.data);
                        var answer = JSON.parse(rejection.data);
                        console.log(answer);
                        // window.alert(answer.message);
                    }
                    defer.reject(rejection);
                    return defer.promise;
                }
            };
        });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();


angular.module('app').controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
            return true;
        } else {
            return false;
        }
    };

    $scope.isAdmin = function () {
        if (!$scope.isUserLoggedIn()) return false;
        let jwt = $localStorage.currentUser.token;

        let jwtData = jwt.split('.')[1];
        let decodedJwtJsonData = window.atob(jwtData);
        let decodedJwtData = JSON.parse(decodedJwtJsonData);
        return decodedJwtData.roles.includes('ROLE_ADMIN');
    };

    // $scope.isAdmin();
});
