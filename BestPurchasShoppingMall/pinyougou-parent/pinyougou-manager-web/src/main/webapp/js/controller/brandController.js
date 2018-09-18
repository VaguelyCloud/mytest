
app.controller('brandController',function ($scope,$controller,brandService) {

    $controller('baseController',{$scope:$scope});


    //查询品牌列表
    $scope.findAll=function () {
        brandService.findAll().success(
            function (response) {
                $scope.list= response;
            }
        )
    };



    //
    // //不通过findAll去查询了, 通过findPage去查询
    // $scope.findPage=function (page,size) {
    //     $http.get('../brand/findPage.do?page='+page+' &size='+size).success(
    //         function (response) {
    //             $scope.list=response.rows;//显示当前页的数据
    //             $scope.paginationConf.totalItems=response.total;//更新总记录数
    //         }
    //     );
    // };


    //新增品牌
    $scope.save=function () {
        var object=null;
        if ($scope.entity.id!=null){
            object=brandService.update($scope.entity);

        }
        else {
            object=brandService.add($scope.entity);
        }

        object.success(
            function (response) {
                if(response.success){
                    alert(response.message)
                    $scope.reloadList();//成功则刷新一下列表即可
                }
                else {
                    alert(response.message);
                }

            }
        )
    };

    //查询实体
    $scope.findOne=function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        );

    };



    //删除
    $scope.dele=function () {
        brandService.dele($scope.selectIds).success(
            function (response) {
                if(response.success){
                    $scope.reloadList();
                }
                else {
                    alert(response.message);
                }
            }
        )
    }

    //初始化 查询条件
    $scope.searchEntity={};
    //查询
    $scope.search=function () {
        brandService.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;//显示当前页的数据
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }
});
