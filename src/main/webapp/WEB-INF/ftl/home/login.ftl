<#include "/common/template.ftl">

<#assign styles>
<style>
    .login-form{
        position: fixed;
        left:50%;
        top:100px;
        margin-left:-300px;
        width:600px;
        text-align: center;
    }
    .welcome{
        font-size:20px;
        line-height: 3;
        font-weight: bold;
        color:#222;
    }
    .input-row{
        display: inline-block;
    }
    .username{
        box-sizing: border-box;
        float: left;
        width:500px;
        height:40px;
        line-height:40px;
        font-size:15px;
        border-width:1px 0 1px 1px;
        border-color:#999;
        color:#333;
        text-indent: 10px;
        outline: 0;
    }
    .submit{
        box-sizing: border-box;
        float: left;
        width:100px;
        height:40px;
        line-height:40px;
        font-size:15px;
        color:#fff;
        border:none;
        text-align: center;
        background: #39f;
        cursor:pointer;
    }
    .error-row{
        font-size:13px;
        line-height: 2;
        color:#c00;
        text-align: left;
    }
</style>
</#assign>
<#assign scripts>
<script>
    seajs.use('/js/module/login');
</script>
</#assign>

<@template "login" styles scripts>
<form action="/login/check" method="post" id="login_form" class="login-form">
    <h3 class="welcome">Welcome to contact room in websocket</h3>
    <p class="input-row">
        <input type="text" name="username" placeholder="请输入您的用户名" id="username" class="username"/>
        <input type="submit" value="进入聊天室" id="submit" class="submit"/>
    </p>
    <p class="error-row">${errorMsg!""}</p>
</form>
</@>
