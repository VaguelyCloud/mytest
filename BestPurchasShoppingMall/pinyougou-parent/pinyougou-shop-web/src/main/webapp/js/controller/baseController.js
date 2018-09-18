app.controller('baseController',function ($scope) {
    /*      分页空间配置
          currentPage: 当前页
          totalItems: 总记录数
          itemPerPage :每页记录数
          perPageOptions: 分页选项
          onChange: 当页码变更后, 自动触发的方法
  */
    $scope.paginationConf={
        currentPage:1,
        totalItems:10,
        itemsPerPage:10,
        perPageOptions:[5,8,10,15,20],
        onChange:function () {
            $scope.reloadList();
        }
    };



    //刷新列表, 封装一下这个方法
    $scope.reloadList=function () {
        $scope.search();
    };


    $scope.selectIds=[]; //用户勾选的id集合
    //用户去勾选复选框
    $scope.updateSelection=function ($event,id) {
        if($event.target.checked){
            $scope.selectIds.push(id);//push向集合中添加元素
        }
        else {
            var index=$scope.selectIds.indexOf(id);//查找值的位置
            $scope.selectIds.splice(index,1);//参数1就是移除的位置,参数2是移除的个数
        }

    }

    $scope.jsonToString=function (jsonString,key) {
        var json=JSON.parse(jsonString);
        var value="";

        for (var i=0;i<json.length;i++){

            if(i>0){
                value+=",";
            }
            //此处用json[i].key 是不行的,因为此处key是变量,数以使用下面的方法
            value+=json[i][key];
        }
        return value;
    }
});