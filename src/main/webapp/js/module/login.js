/**
 * Created by luyi-netease on 2016/3/18.
 */
define(function(){
    $('#login_form').on('submit', function(e){
        var $username = $('#username'),
            username = $username.val();
        if($.trim(username) === ''){
            $('.error-row').html('用户名不能为空');
            e.preventDefault();
        }
        $username.val(username);
    });
});