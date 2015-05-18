(function () {
    var app = angular.module("app", ["pagamento", "ngAnimate", "pagamentoService",
        "routes", "login", "LocalStorageModule", "authService", "home", "fabElement", "notifyElement"]);

    app.config(["$httpProvider", function ($httpProvider) {
            $httpProvider.interceptors.push('authInterceptorService');
        }]);

    app.config(function (localStorageServiceProvider) {
        localStorageServiceProvider
                .setPrefix('antevereTransportesAdmin')
                .setStorageType('sessionStorage')
                .setNotify(true, true);
    });
})();