(function () {
    var app = angular.module("routes", ["ngRoute"]);
    app.config(["$routeProvider", function ($routeProvider) {
            $routeProvider.when('/Home', {
                templateUrl: 'componentes/home.html',
                controller: 'HomeController'
            });

            $routeProvider.when('/Pagamentos', {
                templateUrl: 'componentes/pagamento.html',
                controller: 'PagamentoController'
            });

            $routeProvider.when('/Login', {
                templateUrl: 'componentes/login.html',
                controller: 'loginController'
            });

            $routeProvider.when('/Signup', {
                templateUrl: 'componentes/signup.html',
                controller: 'signupController'
            });

            $routeProvider.otherwise({
                redirectTo: '/Home'
            });
        }]);
})();