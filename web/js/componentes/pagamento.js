(function () {
    var app = angular.module("pagamento", []);

    app.controller("PagamentoController", ["$scope", "$http", "$pgService", "notifyService",
        function ($scope, $http, $pgService, notifyService) {
            $scope.itens = [];
            $scope.mensagem = "";
            $scope.mostrarCalendario = false;
            $scope.semanas = [];

            $scope.mouseOverItem = function (item) {

            };

            $scope.mouseLeaveItem = function (item) {

            };

            $scope.mostrarAcaoBotao = function (item) {
                if (item.mostrarBotoes)
                    item.mostrarBotoes = false;
                else
                    item.mostrarBotoes = true;
            };

            $scope.alterarStatusCalendario = function () {
                if ($scope.mostrarCalendario) {
                    $scope.mostrarCalendario = false;
                }
                else {
                    $scope.mostrarCalendario = true;
                }
            };

            $scope.carregarPagamentos = function () {
                $pgService.listar(function (result) {
                    $scope.semanas = result;

                    $(result).each(function (i, w) {
                        $(w.Dias).each(function (j, d) {
                            $(d.Pagamentos).each(function (k, p) {
                                $scope.itens.push(p);
                            });
                        });
                    });
                },
                        function (mensagem) {
                            if (mensagem)
                                $scope.mensagem = mensagem;
                            else
                                $scope.mensagem = "Nao foi possivel comunicar com o servidor.";
                        },
                        function () {
                        });
            };

            $scope.carregarPagamentos();

            $scope.itemEditar = {};

            $scope.editar = function (item) {
                $scope.itemEditar = item;
                $("#editar").modal("show");
            };

            $scope.salvarEdicao = function (item) {
                $("#editar").modal("hide");
                $http.post("/Pagamento", item).success(function () {

                }).error(function () {
                    $scope.mensagem = "Houve um erro ao editar o pagamento " + item.descricao + ".";
                });
            };

            $scope.excluir = function (item) {
                var index;
                $($scope.itens).each(function (i, it) {
                    if (it.ID == item.ID) {
                        $scope.itens.splice(i, 1);
                        return false;
                    }
                });
            };

            $scope.pegarTotal = function () {
                var total = 0;
                $($scope.itens).each(function (i, item) {
                    total += item.valor;
                });
                return total;
            };

            $scope.pegarPorcentagem = function (item) {
                return (item.valor / $scope.pegarTotal()) * 100;
            };

            $scope.mais = function (item) {
                if (item.expandir) {
                    item.expandir = false;
                }
                else
                    item.expandir = true;
            };

            $scope.pagar = function (item) {
                if (item.notificationID == null)
                    item.notificationID = notifyService.add({
                        fixed: true,
                        message: "Deseja realizar o pagamento de " + item.descricao + "?",
                        buttons: [{
                                text: "Sim",
                                parameter: item,
                                f: $scope.realizarPagamento
                            },
                            {
                                text: "Não",
                                parameter: item,
                                f: $scope.cancelarPagamento
                            }
                        ]
                    });
            };

            $scope.realizarPagamento = function (item) {
                item.expandir = false;
                item.notificationID = null;

                notifyService.add({
                    seconds: 3,
                    message: item.descricao + " pago!"
                });
            };

            $scope.cancelarPagamento = function (item) {
                if (item.notificationID != null) {
                    notifyService.remove(item.notificationID);
                    item.notificationID = null;
                }

                $scope.mais(item);
            };

            $scope.clicarPagamentoCalendario = function (item) {
                item.expandir = true;
                window.scrollBy(0, $("#" + item.ID).offset().top - 100);
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
                                        parameter: 12
                                    }, {
                                        text: "Não",
                                        parameter: 12
                                    }]});


                        },
                        id: 1
                    }]
            };
        }]);
})();