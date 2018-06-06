
var baseUrl = "http://localhost:8080/image/";

var keyToken = "userToken";

function req(reqEntity, success, fail) {

    if (reqEntity.error == null) {
        reqEntity.error = function () {
            fail("未知错误,可能是请求失败或者是服务器异常");
        }
    }

    $.ajax(reqEntity).done(function (result) {
        console.log(result)
        var resultObject = eval("(" + result + ")");
        if (resultObject.success) {
            if (success != null) {
                success(resultObject.data);
            }
        } else {
            if (fail != null) {
                fail(resultObject.msg);
            }
        }
    });
}