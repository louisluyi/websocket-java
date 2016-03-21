<#include "/common/template.ftl">

<#assign styles>
<link href="/css/contact/contact-room.css" rel="stylesheet" type="text/css">
</#assign>
<#assign scripts>
<script>
    seajs.use('/js/module/contactRoom', function(process){
        process.init();
    });
</script>
</#assign>

<@template "聊天室" styles scripts>
    <input type="hidden" id="username" value="${username}"/>
    <div class="middle-container">
        <h3 class="welcome">
            欢迎你，${username}  <a href="javascript:void(0);" id="logout">[退出]</a>
        </h3>
        <div class="main-container">
            <ul id="user_info_list" class="user-info-list"></ul>
            <ul id="dialog_list" class="dialog-list"></ul>
        </div>
    </div>
</@>