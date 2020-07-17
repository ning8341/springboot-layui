$(function () {
    layui.use(['table', 'layer', 'form','laydate'], function () {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form,
            laydate = layui.laydate;

        var ConsumList = {
            tableIns: "",
            pageCurr: "",
            init: function () {
                //初始化时间
                laydate.render({
                    elem: '#consumTime', //指定元素
                    type:'datetime'
                });
                ConsumList.initList();

                $('.search_btn').click(function () {
                    ConsumList.commonSearch();
                });
                //回车--->执行搜索
                $(document).keydown(function (event) {
                    if (event.keyCode == 13) {
                        ConsumList.commonSearch();
                    }
                });

                $('.add_btn').click(function () {
                    ConsumList.commonOpen(null, "录入账单");
                });

                form.on('submit(consumSubmit)', function (data) {
                    ConsumList.commonSubmit(data);
                    return false;
                });

                $('.batchDel_btn').click(function () {
                    ConsumList.commonDelete();
                })
            },
            initList: function () {
                ConsumList.tableIns = table.render({
                    toolbar: true,
                    elem: '#consumList',
                    url: '/consum/consumList',
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
                        ,{field:'title', title:'支出名称',align:'center'}
                        ,{field:'details', title:'支出详情',align:'center'}
                        ,{field:'price', title:'支出费用(单位:元)',align:'center'}
                        ,{field:'consumTime', title:'支出时间',align:'center'}
                        ,{field:'createTime', title:'创建时间',align:'center'}

                    ]],
                    done: function (res, curr, count) {
                        $("[data-field='userStatus']").children().each(function () {
                            if ($(this).text() == '1') {
                                $(this).text("有效")
                            } else if ($(this).text() == '0') {
                                $(this).text("失效")
                            }
                        });
                        //得到数据总量
                        ConsumList.pageCurr = curr;
                    },
                    contentType: "application/json"
                });
            },
            commonSearch: function () {
                var searchKey = $('.search_input').val();
                ConsumList.tableIns.reload({
                    where: { //设定异步数据接口的额外参数，任意设
                        condition: {
                            title: searchKey
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
                    url: "/consum/save",
                    contentType: "application/json",
                    success: function (res) {
                        if (res.code == 1) {
                            layer.alert(res.msg, function () {
                                layer.closeAll();
                                ConsumList.initList();
                            });
                        } else {
                            layer.alert(res.msg);
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                            ConsumList.initList();
                        });
                    }
                });
            },
            commonOpen: function (data, title) {
                var parentId = null;
                if(data == null){
                    $("#id").val("");
                }else{
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
                    type:1,
                    title: title,
                    fixed:false,
                    resize :false,
                    shadeClose: true,
                    area: ['550px'],
                    content:$('#setConsum'),
                    end:function(){
                        ConsumList.commonClean();
                    }
                });
            },
            commonClean: function () {
                $("#name").val("");
                $("#descpt").val("");
                $("#url").val("");
            },
            commonDelete: function () {
                var checkStatus = table.checkStatus('ConsumList');
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
                        url: '/consum/delBatchByIds',
                        data: JSON.stringify(ids),
                        contentType: "application/json",
                        success: function (data) {
                            if (data.code == 1) {
                                layer.close(indexMsg);
                                layer.msg("删除成功", {icon: 1, time: 2000});
                                ConsumList.commonSearch();
                            } else {
                                layer.msg(data.msg, {icon: 1});
                            }
                        }
                    });
                });
            }
        };
        ConsumList.init();
    });
});





