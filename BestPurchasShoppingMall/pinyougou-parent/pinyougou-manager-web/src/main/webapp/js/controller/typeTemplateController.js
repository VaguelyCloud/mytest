//控制层
app.controller('typeTemplateController', function ($scope, $controller, typeTemplateService, brandService, specificationService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        typeTemplateService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        typeTemplateService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        typeTemplateService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = typeTemplateService.update($scope.entity); //修改
        } else {
            serviceObject = typeTemplateService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        typeTemplateService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function () {
        typeTemplateService.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //此处必须按照select2 的官方文档的格式来声明这个变量
    $scope.brandList = {data: []};//声明品牌列表
    //此数据用于试用, 静态选择项
    // $scope.brandIds=[{"id":2,"text":"联想2"},{"id":3,"text":"三星"},{"id":4,"text":"华为"}];
    $scope.brandIds=[];

    //读取品牌列表
    $scope.findBrandList = function () {
        brandService.selectOptionList().success(
            function (response) {
                $scope.brandList = {data: response};
            }
        );
    }


    //定义一个规格变量用于接收返回值
    $scope.specList={data:[]};

    $scope.specIds=[];

    //读取规格列表 List<map> 格式
    $scope.findSpecList = function () {
        specificationService.selectSpecList().success(
            function (response) {
                $scope.specList={data:response};

            }
        );
    }

    //修改按钮 加载当前数据
    $scope.changeRow=function (entityid) {
         // $scope.brandIds=JSON.parse(brandRow);
         // $scope.specIds=JSON.parse(specRow);
        $scope.entity={customAttributeItems:[],name:[],brandIds:[],specIds:[],id:[]};
        typeTemplateService.findOne(entityid).success(
            function (response) {
                $scope.entity.id=response.id;
                $scope.entity.brandIds =JSON.parse( response.brandIds);//关联品牌
                $scope.entity.customAttributeItems=JSON.parse(response.customAttributeItems);//关联扩展属性
                $scope.entity.name=response.name;// 分类模板名称
                $scope.entity.specIds=JSON.parse(response.specIds);//关联规格
            }
        );
    }

    //扩展属性添加行
    $scope.addTableRow=function () {
        $scope.entity.customAttributeItems.push({});

    }

    //扩展属性删除行
    $scope.deleTableRow=function (index) {
        $scope.entity.customAttributeItems.splice(index,1);
    }
     

});
