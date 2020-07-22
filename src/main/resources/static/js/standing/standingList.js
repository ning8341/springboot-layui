$(function () {
    layui.use(['table', 'layer', 'form', 'laydate'], function () {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form,
            laydate = layui.laydate;

        var BillList = {
            tableIns: "",
            pageCurr: "",
            init: function () {
                laydate.render({
                    elem: '#start'
                    ,type: 'date'
                });
                laydate.render({
                    elem: '#end'
                    ,type: 'date'
                });

                BillList.initList();

                $('.search_btn').click(function () {
                    BillList.commonSearch();
                });
                $(document).keydown(function (event) {
                    if (event.keyCode == 13) {
                        BillList.commonSearch();
                    }
                });
                $('.add_btn').click(function () {
                    BillList.commonOpen(null, "录入账单");
                });
                form.on('submit(billSubmit)', function (data) {
                    BillList.commonSubmit(data);
                    return false;
                });
                $('.batchDel_btn').click(function () {
                    BillList.commonDelete();
                })
            },
            initList: function () {
                BillList.tableIns = table.render({
                    toolbar: true,
                    elem: '#billList',
                    url: '/standing/standingList',
                    method: 'post',
                    cellMinWidth: 80,
                    page: true,
                    request: {
                        pageName: 'current',
                        limitName: 'size'
                    },
                    response: {
                        statusName: 'code', //数据状态的字段名称，默认：code
                        statusCode: 200, //成功的状态码，默认：0
                        countName: 'totals', //数据总数的字段名称，默认：count
                        dataName: 'list' //数据列表的字段名称，默认：data
                    },
                    cols: [[
                        {type: 'checkbox', fixed: 'left'}
                        , {field: 'createTime', title: '记账日期', align: 'center'}
                        , {field: 'cashIn', title: '现金收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'cardIn', title: '刷卡收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'unionpayIn', title: '银联收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'publicIn', title: '大众点评收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'elmIn', title: '饿了么收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'meituanIn', title: '美团收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'cashOut', title: '现金支出', align: 'center', style: " color: #FC764C;font-size:20px;"}
                        , {field: 'cardOut', title: '刷卡支出', align: 'center', style: " color: #FC764C;font-size:20px;"}
                        , {field: 'total', title: '当日总收入', align: 'center', style: " color: #008000;font-size:20px;"}
                        , {field: 'remark', title: '记账备注', align: 'center'}
                    ]],
                    done: function (res, curr, count) {
                        //得到数据总量
                        BillList.pageCurr = curr;
                        //这块应该前端处理数据，偷懒发个请求后台处理了数据，感兴趣可以找找前端怎么写
                        $.ajax({
                            type: "POST",
                            async:false,
                            data: JSON.stringify(res.list),
                            url: "/standing/calculat",
                            contentType: "application/json",
                            success: function (res) {
                                console.log("res---------",res)
                                if (res.code == 200) {
                                    layer.closeAll();
                                    $('#calculat-in').val(res.data.tatalIn);
                                    $('#calculat-out').val(res.data.tatalOut);
                                    $('#calculat-total').val(res.data.total);

                                }
                            },
                            error: function () {
                                layer.alert("操作请求错误，请您稍后再试", function () {
                                    layer.closeAll();
                                    BillList.initList();
                                });
                            }
                        });

                    },
                    contentType: "application/json"
                });
            },
            commonSearch: function () {
                var startInput = $('.start_input').val();
                var endInput = $('.end_input').val();
                BillList.tableIns.reload({
                    where: { //设定异步数据接口的额外参数，任意设
                        condition: {
                            start: startInput,
                            end:endInput
                        }
                    },
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            },
            commonSubmit: function (data) {
                $.ajax({
                    type: "POST",
                    data: JSON.stringify(data.field),
                    url: "/standing/save",
                    contentType: "application/json",
                    success: function (res) {
                        if (res.code == 1) {
                            layer.alert(res.msg, function () {
                                layer.closeAll();
                                BillList.initList();
                            });
                        } else {
                            layer.alert(res.msg);
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                            BillList.initList();
                        });
                    }
                });
            },
            commonOpen: function (data, title) {
                var parentId = null;
                if (data == null) {
                    $("#id").val("");
                } else {
                    //回显数据
                    $("#id").val(data.id);
                    $("#name").val(data.name);
                    $("#descpt").val(data.descpt);
                    $("#url").val(data.url);
                    parentId = data.pid;
                }
                var pageNum = $(".layui-laypage-skip").find("input").val();
                $("#pageNum").val(pageNum);

                layer.open({
                    type: 1,
                    title: title,
                    fixed: false,
                    resize: false,
                    shadeClose: true,
                    area: ['550px'],
                    content: $('#setBill'),
                    end: function () {
                        BillList.commonClean();
                    }
                });
            },
            commonClean: function () {
                $("#cashIn").val("");
                $("#cardIn").val("");
                $("#unionpayIn").val("");
                $("#publicIn").val("");
                $("#elmIn").val("");
                $("#meituanIn").val("");
                $("#cashOut").val("");
                $("#cardOut").val("");
                $("#remark").val("");
            },
            commonDelete: function () {
                var checkStatus = table.checkStatus('billList');
                if (checkStatus.data.length === 0) {
                    layer.msg("请选择要删除的数据", {icon: 0, time: 2000});
                    return;
                }
                layer.confirm('确定删除选中的信息？', {icon: 3, title: '确认'}, function (index) {
                    var indexMsg = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
                    var ids = [];
                    var ids = new Array(checkStatus.data.length);
                    for (var i = 0; i < checkStatus.data.length; i++) {
                        ids[i] = checkStatus.data[i].id;
                    }
                    $.ajax({
                        type: 'delete',
                        url: '/standing/delBatchByIds',
                        data: JSON.stringify(ids),
                        contentType: "application/json",
                        success: function (data) {
                            if (data.code == 1) {
                                layer.close(indexMsg);
                                layer.msg("删除成功", {icon: 1, time: 2000});
                                BillList.commonSearch();
                            } else {
                                layer.msg(data.msg, {icon: 1});
                            }
                        }
                    });
                });
            }
        };
        BillList.init();
    });
});





