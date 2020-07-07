$(function () {
    layui.use(['table', 'layer', 'form'], function () {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;

        var RoleList = {
            tableIns: "",
            pageCurr: "",
            init: function () {

                RoleList.initList();

                $('.search_btn').click(function () {
                    RoleList.commonSearch();
                });
                //回车--->执行搜索
                $(document).keydown(function (event) {
                    if (event.keyCode == 13) {
                        RoleList.commonSearch();
                    }
                });

                $('.add_btn').click(function () {
                    RoleList.commonOpen(null, "新增");
                });

                form.on('submit(roleSubmit)', function (data) {
                    RoleList.commonSubmit(data);
                    return false;
                });

                //监听工具条
                table.on('tool(roleTable)', function (obj) {
                    var data = obj.data;
                    if (obj.event === 'del') {
                        RoleList.commonStatus(data, data.id, data.roleName);
                    } else if (obj.event === 'edit') {
                        RoleList.commonOpen(data, "编辑");
                    } else if (obj.event === 'recover') {
                        RoleList.commonRecover(data, data.id);
                    }
                });

                $('.batchDel_btn').click(function () {
                    RoleList.commonDelete();
                })
            },
            initList: function () {
                RoleList.tableIns = table.render({
                    toolbar: true,
                    elem: '#roleList',
                    url: '/role/getRoleList',
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
                        {field: 'roleName', title: '角色名称', align: 'center'}
                        , {field: 'roleDesc', title: '角色描述', align: 'center'}
                        , {field: 'permissions', title: '权限', align: 'center'}
                        , {field: 'createTime', title: '创建时间', align: 'center'}
                        , {
                            field: 'roleStatus',
                            title: '是否有效',
                            align: 'center',
                            templet: '<div>{{d.roleStatus === 1 ? "有效" : "禁用"}}</div>'
                        }
                        , {fixed: 'right', title: '操作', align: 'center', toolbar: '#optBars'}
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
                        RoleList.pageCurr = curr;
                    },
                    contentType: "application/json"
                });
            },
            commonSearch: function () {
                var searchKey = $('.search_input').val();
                RoleList.tableIns.reload({
                    where: { //设定异步数据接口的额外参数，任意设
                        condition: {
                            role_name: searchKey
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
                    url: "/role/save",
                    contentType: "application/json",
                    success: function (res) {
                        if (res.code == 1) {
                            layer.alert(res.msg, function () {
                                layer.closeAll();
                                RoleList.initList();
                            });
                        } else {
                            layer.alert(res.msg);
                        }
                    },
                    error: function () {
                        layer.alert("操作请求错误，请您稍后再试", function () {
                            layer.closeAll();
                            RoleList.initList();
                        });
                    }
                });
            },
            commonOpen: function (data, title) {
                var pid = null;
                if (data == null) {
                    $("#id").val("");
                } else {
                    //回显数据
                    $("#id").val(data.id);
                    $("#roleName").val(data.roleName);
                    $("#roleDesc").val(data.roleDesc);
                    pid = data.permissionIds;
                }

                formSelects.data('permissions', 'server', {
                    url: '/permission/parentPermissionList',
                    keyName: 'name',
                    keyVal: 'id',
                    success: function (id, url, searchVal, result) {      //使用远程方式的success回调

                        if (pid != null) {
                            var assistAuditArry = pid.split(",");
                            formSelects.value('permissions', assistAuditArry);
                        }
                    },
                    error: function (id, url, searchVal, err) {
                    },
                });
                var pageNum = $(".layui-laypage-skip").find("input").val();
                $("#pageNum").val(pageNum);
                layer.open({
                    type: 1,
                    title: title,
                    fixed: false,
                    resize: false,
                    shadeClose: true,
                    area: ['550px', '550px'],
                    content: $('#setRole'),
                    end: function () {
                        RoleList.commonClean();
                    }
                });
            },
            commonClean: function () {
                $("#roleName").val("");
                $("#roleDesc").val("");
                $("#permissions").val("");
            },
            commonStatus: function (obj, id, name) {
                if (null != id) {
                    layer.confirm('您确定要禁用' + name + '吗？', {
                        btn: ['确认', '返回'] //按钮
                    }, function () {
                        $.post("/role/updateStatus", {"id": id, "status": 0}, function (data) {
                            if (data.code == 1) {
                                layer.closeAll();
                                RoleList.commonSearch();
                            } else {
                                layer.msg(data.msg);
                            }
                        });
                    }, function () {
                        layer.closeAll();
                    });

                }
            },
            commonRecover: function (obj, id) {
                if (null != id) {
                    layer.confirm('您确定要恢复吗？', {
                        btn: ['确认', '返回'] //按钮
                    }, function () {
                        $.post("/role/updateStatus", {"id": id, "status": 1}, function (data) {
                            if (data.code == 1) {
                                layer.closeAll();
                                RoleList.commonSearch();
                            } else {
                                layer.msg(data.msg);
                            }
                        });
                    }, function () {
                        layer.closeAll();
                    });
                }
            },
            commonDelete: function () {
                var checkStatus = table.checkStatus('roleList');
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
                        url: '/role/delBatchByIds',
                        data: JSON.stringify(ids),
                        contentType: "application/json",
                        success: function (data) {
                            if (data.code == 1) {
                                layer.close(indexMsg);
                                layer.msg("删除成功", {icon: 1, time: 2000});
                                RoleList.commonSearch();
                            } else {
                                layer.msg(data.msg, {icon: 1});
                            }
                        }
                    });
                });
            }
        };
        RoleList.init();
    });
});





