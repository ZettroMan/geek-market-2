angular.module('app').controller('accountController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.getUserDetails = function () {
        $http({
            url: contextPath + '/api/v1/user',
            method: 'GET'
         })
            .then(function (response) {
                $scope.userInfo = response.data;
            });
    };

    $scope.saveUserInfo = function () {
        $http.put(contextPath + '/api/v1/user', $scope.userInfo)
            .then(function () {
                alert('Изменения сохранены');
            });
    };

    $scope.getUserDetails();
});
