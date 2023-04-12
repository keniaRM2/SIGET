var siget = angular.module('siget', []);

siget.controller('registroCita', function($scope, $http, $timeout) {

    $scope.vista = 1;
    $scope.objeto = {};
    $scope.enviando = false;

    $scope.verVista = function( vista ) {
        return $scope.vista == vista;
    };

    $scope.cambiarVista = function( vistaNueva) {
        $scope.vista = vistaNueva;
    };



});