/**
 * Created by louis on 2016/3/20.
 */
define(function(require, exports, module){
    var User = require('/js/model/user');
    var Dialogue = require('/js/model/dialogue');
    var MyWebSocket = require('/js/module/websocket');
    var MyNotification = require('/js/model/notification');

    var username = $('#username').val();
    var ws = new MyWebSocket('ws://localhost:8080/ws?username=' + username);
    var $userInfoList = $('#user_info_list'),
        $dialogList = $('#dialog_list');
    var process = {
        init:function(){
            this.notification();
            this.eventHandler();
        },
        createDialogue:function(userListDTO){
            if(!userListDTO) return;
            var i = 0,
                others = userListDTO.userList,
                len = others.length;
            for(; i < len; ++i){
                var dialogue = new Dialogue({
                    owner:{
                        username:username
                    },
                    others:[others[i]],
                    ws:ws,
                    userInfoList:$userInfoList[0],
                    dialogList:$dialogList[0]
                });
            }
        },
        notification:function(){
            var notification = new MyNotification();
            window.setInterval(function(){
                var count = Dialogue.getNewMessageCount();
                if(count > 0){
                    notification.show({
                        url:'/img/weixin.png',
                        title:'来自聊天室的提醒',
                        body:'您有' + count + '条新消息'
                    });
                }
            }, 10 * 1000);
        },
        eventHandler:function(){
            ws.on('open', function(){
                console.log('success');
            });
            ws.on('userList', function(userListDTO){
                process.createDialogue(userListDTO);
            });
            $('#logout').on('click', function(){
                ws.close();
                location.href = '/login';
            });
            $(window).on('unload', function(){
                window.setTimeout(function(){}, 100);
                ws.close();
            });
        }
    };
    module.exports = process;
});