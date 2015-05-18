(function () {
    var app = angular.module("fabElement", []);

    app.directive("fab", ["$timeout", function ($timeout) {
        return {
            restrict: 'E',
            templateUrl: "js/componentes/fab/fab.html",
            controller: function ($scope) {
                $scope.mouseOver = function () {
                    $scope.fab.miniShow = $scope.fab.miniButtons;

                    $timeout(function () {
                        $($scope.fab.miniShow).each(function (i, item) {
                            var id = item.id;
                            $(".buttons .mini#" + id).tooltip({
                                placement: "left",
                                title: item.alt,
                                html: true
                            });
                        });
                        $("#fab-principal").tooltip({
                            placement: "left",
                            title: $scope.fab.principalAlt,
                            html: true
                        });
                    }, 501);
                };

                $scope.mouseLeave = function () {
                    $scope.fab.miniShow = [];

                    $timeout(function () {
                        $($scope.fab.miniShow).each(function (i, item) {
                            var id = item.id;
                            $(".buttons .mini#" + id).tooltip("destroy");
                        });
                        $("#fab-principal").tooltip("destroy");
                        
                        $(".fab-tooltip").tooltip("destroy");
                    }, 501);
                };
            }
        };
    }]);

})();