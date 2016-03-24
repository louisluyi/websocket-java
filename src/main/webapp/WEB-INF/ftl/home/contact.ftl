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
    <input type="hidden" id="username" value="${userName}"/>
    <input type="hidden" id="userId" value=""/>
    <div class="middle-container">
        <div class="main-container">
            <ul id="user_info_list" class="user-info-list">
                <li class="owner" title="${userName}">
                    <img src="/img/weixin.png" />
                    <p class="username">${userName}</p>
                    <a href="javascript:void(0);" id="logout" class="logout"></a>
                </li>
            </ul>
            <ul id="dialog_list" class="dialog-list"></ul>
        </div>
    </div>
</@>