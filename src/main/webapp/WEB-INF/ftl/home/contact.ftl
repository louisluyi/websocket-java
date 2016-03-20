<#include "/common/template.ftl">

<#assign styles>
<link href="/css/contact/contact-room.css" rel="stylesheet" type="text/css">
</#assign>
<#assign scripts>
<script>
    seajs.use('/js/module/contactRoom');
</script>
</#assign>

<@template "聊天室" styles scripts>
    <div class="middle-container">
        <h3 class="welcome">
            欢迎你，${username}  <a href="javascript:void(0);" id="logout">[退出]</a>
        </h3>
        <div class="main-container">
            <ul id="user_list" class="user-list"></ul>
            <ul id="contact_content_list" class="contact-content-list"></ul>
        </div>
    </div>
</@>