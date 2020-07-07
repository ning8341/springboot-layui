$(function () {
    layui.use(['table', 'layer', 'form'], function () {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;

        var PermissionList = {
            tableIns: "",
            pageCurr: "",
            init: function () {

                PermissionList.initList();

                $('.search_btn').click(function () {
                    PermissionList.commonSearch();
                });
                //回车--->执行搜索
                $(document).keydown(function (event) {
                    if (event.keyCode == 13) {
                        PermissionList.commonSearch();
                    }
                });

                $('.add_btn').click(function () {
                    PermissionList.commonOpen(null, "新增");
                });

                form.on('submit(permissionSubmit)', function (data) {
                    PermissionList.commonSubmit(data);
                    return false;
                });

                //监听工具条
                table.on('tool(permissionTable)', function (obj) {
                    var data = obj.data;
                    if (obj.event === 'edit') {
                        PermissionList.commonOpen(data, "编辑");
                    }
                });
                $('.batchDel_btn').click(function () {
                    PermissionList.commonDelete();
                })
            },
            initList: function () {
                PermissionList.tableIns = table.render({
                    toolbar: true,
                    elem: '#permissionList',
                    url: '/permission/permissionList',
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
                        {type: 'checkbox', fixed: 'left'},
                       // {field:'parentId', title:'parentId'}
                       //  {field:'pname', title:'父级菜单',align:'center'
                        {field:'name', title:'菜单名称',align:'center'}
                        ,{field:'descpt', title:'描述',align:'center'}
                        ,{field:'url', title:'菜单url',align:'center'}
                        ,{field:'createTime', title:'创建时间',align:'center'}
                        ,{field:'updateTime', title:'更新时间',align:'center'}
                        ,{fixed:'right',title:'操作',align:'center', toolbar:'#optBar'}
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
                        PermissionList.pageCurr = curr;
                    },
                    contentType: "application/json"
                });
            },
            commonSearch: function () {
                var searchKey = $('.search_input').val();
                PermissionList.tableIns.reload({
                    where: { //设定异步数据接口的额外参数，任意设
                        condition: {
                            name: searchKey
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
                    url: "/permission/save",
                    contentType: "application/json",
                    success: function (res) {
                        if (res.code == 1) {
                            layer.alert(res.msg, function () {
                                layer.closeAll();
                                PermissionList.initList();
                            });
                        } else {
                            layer.alert(res.msg);
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                            PermissionList.initList();
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
                $.ajax({
                    url:'/permission/parentPermissionList',
                    dataType:'json',
                    async: true,
                    success:function(data){
                        $("#pid").html("<option value='0'>根目录</option>");
                        $.each(data,function(index,item){
                            if(!parentId){
                                var option = new Option(item.name,item.id);
                            }else {
                                var option = new Option(item.name,item.id);
                                // // 如果是之前的parentId则设置选中
                                if(item.id == parentId) {
                                    option.setAttribute("selected",'true');
                                }
                            }
                            $('#pid').append(option);//往下拉菜单里添加元素
                            form.render('select'); //这个很重要
                        })
                    }
                });

                layer.open({
                    type:1,
                    title: title,
                    fixed:false,
                    resize :false,
                    shadeClose: true,
                    area: ['550px'],
                    content:$('#setPermission'),
                    end:function(){
                        PermissionList.commonClean();
                    }
                });
            },
            commonClean: function () {
                $("#name").val("");
                $("#descpt").val("");
                $("#url").val("");
            },
            commonDelete: function () {
                var checkStatus = table.checkStatus('permissionList');
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
                        url: '/permission/delBatchByIds',
                        data: JSON.stringify(ids),
                        contentType: "application/json",
                        success: function (data) {
                            if (data.code == 1) {
                                layer.close(indexMsg);
                                layer.msg("删除成功", {icon: 1, time: 2000});
                                PermissionList.commonSearch();
                            } else {
                                layer.msg(data.msg, {icon: 1});
                            }
                        }
                    });
                });
            }
        };
        PermissionList.init();
    });
});





