(function () {
    var app = angular.module("home", []);
    app.controller("HomeController", ["$scope", "$http", "notifyService", "$timeout",
        function ($scope, $http, notifyService, $timeout) {
            $scope.sim = function (arg) {

            };

            $scope.nao = function (arg) {

            };



            $scope.fab = {
                principalClick: function () {
                    $("#editar").modal().modal("show");
                },
                principalIcon: "glyphicon glyphicon-plus",
                secondIcon: "glyphicon glyphicon-file",
                principalAlt: "Único",
                miniButtons: [
                    {
                        icon: "glyphicon glyphicon-refresh",
                        color: "green",
                        alt: "Automáticos",
                        click: function () {
                            alert("icon2");
                        },
                        id: 2
                    }, {
                        icon: "glyphicon glyphicon-duplicate",
                        color: "blue",
                        alt: "Diversos",
                        click: function () {
                            
                            notifyService.add({
                                fixed: true,
                                message: "Deseja realizar o pagamento?",
                                buttons: [{
                                        text: "Sim",
                                        f: $scope.sim,
                                        parameter: 12
                                    }, {
                                        text: "Não",
                                        f: $scope.nao,
                                        parameter: 12
                                    }]});
                        
                        
                        },
                        id: 1
                    }]
            };

            $scope.titulo = "Título do Trabalho";
            $scope.barsVisualization = null;
            $scope.morrisData = null;
            $scope.loadPaymentChart = function () {
                $http.get("/AngularJS/Pagamento").success(function (data) {
                    $scope.morrisData = data;
                    $("#payment-chart").empty();
                    new Morris.Bar({
                        element: 'payment-chart',
                        data: $scope.morrisData,
                        xkey: 'vencimento',
                        ykeys: ['valor'],
                        labels: ['Valor']
                    });
                });
            };
        }]);
})();