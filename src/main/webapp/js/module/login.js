/**
 * Created by luyi-netease on 2016/3/18.
 */
define(function(require){
    $('#login_form').on('submit', function(e){
        var username = $('#username').val();
        if($.trim(username) === ''){
            $('.error-row').html('用户名不能为空');
            e.preventDefault();
        }
    })
});