$(function () {
    layui.use(['table', 'layer', 'form'], function () {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;
        var UserList = {
            tableIns: "",
            pageCurr: "",
            init: function () {

                UserList.initList();

                $('.search_btn').click(function () {
                    UserList.commonSearch();
                });
                //回车--->执行搜索
                $(document).keydown(function (event) {
                    if (event.keyCode == 13) {
                        UserList.commonSearch();
                    }
                });

                $('.usersAdd_btn').click(function () {
                    UserList.commonOpen(null, "新增");
                });

                form.on('submit(userSubmit)', function (data) {
                    UserList.commonSubmit(data);
                    return false;
                });

                //监听工具条
                table.on('tool(userTable)', function (obj) {
                    var data = obj.data;
                    if (obj.event === 'del') {
                        UserList.commonStatus(data, data.id, data.name);
                    } else if (obj.event === 'edit') {
                        UserList.commonOpen(data, "编辑");
                    } else if (obj.event === 'recover') {
                        UserList.commonRecover(data, data.id);
                    }
                });
                
                $('.batchDel_btn').click(function () {
                    UserList.commonDelete();
                })
            },
            initList: function () {
                UserList.tableIns = table.render({
                    toolbar: true,
                    elem: '#uesrList',
                    url: '/user/list',
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
                          {field: 'name', title: '用户名', align: 'center'},
                          {field: 'roleId', title: '角色类型', align: 'center', templet: '<div>{{d.roleId === "1" ? "超级管理员" : "普通用户"}}</div>'}
                        , {field: 'phone', title: '手机号', align: 'center'}
                        , {field: 'createTime', title: '注册时间', align: 'center'}
                        , {field: 'status', title: '是否有效', align: 'center', templet: '<div>{{d.status === 1 ? "启用" : "禁用"}}</div>'}
                        , {title: '操作', align: 'center', toolbar: '#optBar'}
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
                        UserList.pageCurr = curr;
                    },
                    contentType: "application/json"
                });
            },
            commonSearch: function () {
                var searchKey = $('.search_input').val();
                UserList.tableIns.reload({
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
                    // data: $("#userForm").serialize(),
                    data: JSON.stringify(data.field),
                    url: "/user/save",
                    contentType: "application/json",
                    success: function (res) {
                        if (res.code == 1) {
                            layer.alert(res.msg, function () {
                                layer.closeAll();
                                UserList.initList();
                            });
                        } else {
                            layer.alert(res.msg);
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                            UserList.initList();
                        });
                    }
                });
            },
            commonOpen: function (data, title) {
                var roleId = null;
                if (data == null || data == "") {
                    $("#id").val("");
                } else {
                    $("#id").val(data.id);
                    $("#username").val(data.name);
                    $("#mobile").val(data.phone);
                    roleId = data.roleId;
                }
                var pageNum = $(".layui-laypage-skip").find("input").val();
                $("#pageNum").val(pageNum);
                $.ajax({
                    url: '/role/getRoles',
                    dataType: 'json',
                    async: true,
                    success: function (data) {
                        $.each(data, function (index, item) {
                            if (!roleId) {
                                var option = new Option(item.roleName, item.id);
                            } else {
                                var option = new Option(item.roleName, item.id);
                                // // 如果是之前的parentId则设置选中
                                if (item.id == roleId) {
                                    option.setAttribute("selected", 'true');
                                }
                            }
                            $('#roleId').append(option);//往下拉菜单里添加元素
                            form.render('select'); //这个很重要
                        })
                    }
                });
                layer.open({
                    type: 1,
                    title: title,
                    fixed: false,
                    resize: false,
                    shadeClose: true,
                    area: ['550px'],
                    content: $('#setUser'),
                    end: function () {
                        UserList.commonClean();
                    }
                });
            },
            commonClean: function () {
                $("#username").val("");
                $("#mobile").val("");
                $("#password").val("");
                $('#roleId').html("");
            },
            commonStatus: function (obj, id, name) {
                var currentUser = $("#currentUser").html();
                if (null != id) {
                    if (currentUser == id) {
                        layer.alert("对不起，您不能执行删除自己的操作！");
                    } else {
                        layer.confirm('您确定要删除' + name + '用户吗？', {
                            btn: ['确认', '返回'] //按钮
                        }, function () {
                            $.post("/user/updateUserStatus", {"id": id, "status": 0}, function (data) {
                                if (data.code == 1) {
                                    layer.alert(data.msg, function () {
                                        layer.closeAll();
                                    });
                                    UserList.commonSearch();
                                } else {
                                    layer.alert(data.msg);
                                }
                            });
                        }, function () {
                            layer.closeAll();
                        });
                    }
                }
            },
            commonRecover: function (obj, id) {
                if (null != id) {
                    layer.confirm('您确定要恢复吗？', {
                        btn: ['确认', '返回'] //按钮
                    }, function () {
                        $.post("/user/updateUserStatus", {"id": id, "status": 1}, function (data) {
                            if (data.code == 1) {
                                layer.alert(data.msg, function () {
                                    layer.closeAll();
                                    UserList.commonSearch();
                                });
                            } else {
                                layer.alert(data.msg);
                            }
                        });
                    }, function () {
                        layer.closeAll();
                    });
                }
            },
            commonDelete:function () {
                var checkStatus = table.checkStatus('uesrList');
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
                        url: '/user/delBatchByIds',
                        data: JSON.stringify(ids),
                        contentType: "application/json",
                        success: function (data) {
                            if (data.code == 1) {
                                layer.close(indexMsg);
                                layer.msg("删除成功", {icon: 1, time: 2000});
                                UserList.commonSearch();
                            } else {
                                layer.msg(data.msg, {icon: 1});
                            }
                        }
                    });
                });
            }
        };
        UserList.init();
    });
});





