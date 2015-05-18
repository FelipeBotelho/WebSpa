(function () {
    var app = angular.module("pagamentoService", []);

    app.service("$pgService", ["$http", function ($http) {
            this.listar = function (sucesso, erro, sempre) {
                var resultado;
                $http.get("/AngularJS/Pagamento").success(function (data) {
                    resultado = data;
                    if (data.Sucesso)
                        sucesso(data.Resultado);
                    else
                        erro(data);
                }).error(function () {
                    erro(resultado.Mensagem);
                });

                sempre(resultado);
            };
        }]);
})();