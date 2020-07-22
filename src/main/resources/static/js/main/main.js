$(function () {
    layui.use(['table', 'layer', 'laydate'], function () {
        var table = layui.table,
            layer = layui.layer,
            laydate = layui.laydate;

        var MainList = {
            tableIns: "",
            pageCurr: "",
            init: function () {
                MainList.load();
            },
            load: function () {


                var series = [
                    {
                        name: '2088年',
                        data: [2, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0]
                    }
                ];
                $.ajax({
                    type: "get",
                    url: "/standing/getInfo",
                    contentType: "application/json",
                    async:false,
                    success: function (res) {
                        if (res.code == 200) {
                            layer.closeAll();
                            series[0].name = res.data.year+"年";
                            series[0].data = res.data.array;
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                            BillList.initList();
                        });
                    }
                });

                var title = {
                    text: '记账'
                };
                var subtitle = {
                    text: 'dongwn个人记账管理平台'
                };
                var xAxis = {
                    categories: ['1', '2', '3', '4', '5', '6',
                        '7', '8', '9', '10', '11', '12']
                };
                var yAxis = {
                    title: {
                        text: 'powerby-dongwn'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                };

                var tooltip = {
                    valueSuffix: '\xB0C'
                };

                var legend = {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                };

                var json = {};
                json.title = title;
                json.subtitle = subtitle;
                json.xAxis = xAxis;
                json.yAxis = yAxis;
                json.tooltip = tooltip;
                json.legend = legend;
                json.series = series;
                $('#container').highcharts(json);

            }
        };
        MainList.init();
    });
});





