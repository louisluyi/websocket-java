/**
 * Created by louis on 2016/3/20.
 */
define(function(require, exports, module){
    var User = require('/js/model/user');
    var Dialogue = require('/js/model/dialogue');
    var MyWebSocket = require('/js/module/websocket');

    var username = $('#username').val();
    var ws = new MyWebSocket('ws://localhost:8080/ws?username=' + username);
    var $userInfoList = $('#user_info_list'),
        $dialogList = $('#dialog_list');
    var process = {
        init:function(){
            this.createDialogue();
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
        eventHandler:function(){
            ws.on('open', function(){
                console.log('success');
            });
            ws.on('userList', function(userListDTO){
                process.createDialogue(userListDTO);
            });
            /*ws.on('message', function(message){

            });*/
            $(window).on('unload', function(){
                window.setTimeout(function(){}, 0);
                ws.close();
            });
        }
    };
    module.exports = process;
});