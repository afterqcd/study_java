$(document).ready(function() {
    $("#content").text("文档加载完毕");

    $.ajax({
        method: "POST",
        url: "api/auth",
        data: JSON.stringify({
            username: "admin",
            password: "admin"
        }),
        contentType: "application/json; charset=utf-8"
    })
    .done(function() {
        $("#content").text("登陆成功");

        $.ajax("api/posts")
        .done(function(data) {
            $("#content").text(JSON.stringify(data));
        })
        .fail(function(res, status, err) {
            $("#content").text("请求post列表失败");
        })
    })
    .fail(function(res, status, err) {
        $("#content").text("登陆失败 " + status);
    })
});