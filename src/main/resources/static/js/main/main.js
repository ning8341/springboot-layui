$(function () {
    layui.use(['table', 'layer', 'laydate'], function () {
        var table = layui.table,
            layer = layui.layer,
            laydate = layui.laydate;

        var MainList = {
            tableIns: "",
            pageCurr: "",
            init: function () {
                MainList.loadInfo();
            },
            loadInfo: function () {
                $.ajax({
                    type: "get",
                    async: false,
                    url: "/home/getInfo",
                    contentType: "application/json",
                    success: function (res) {
                        if (res.code == 200) {
                            //设置用户数
                            $('.set-user-num').text(res.data.userNum);
                            $('.set-bill-num').text(res.data.billNum);
                            $('.set-consum-num').text(res.data.consumNum);
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                        });
                    }
                });
            }
        };
        MainList.init();
    });
});





