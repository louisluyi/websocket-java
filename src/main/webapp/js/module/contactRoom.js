/**
 * Created by louis on 2016/3/20.
 */
define(function(require, exports, module){
    var User = require('/js/model/user');
    var Dialogue = require('/js/model/dialogue');
    var MyWebSocket = require('/js/module/websocket');

    var username = $('#username').val();
    var ws = new MyWebSocket('ws://localhost:8080/ws?username=' + username);
    var $userInfoList = $('.user-info-list'),
        $dialogList = $('.dialog-list');
    var process = {
        init:function(){
            this.createDialogue();
            this.eventHandler();
        },
        createDialogue:function(other){
            var dialogue = new Dialogue({
                owner:{
                    username:username
                },
                others:[other],
                ws:ws,
                userInfoList:$userInfoList[0],
                dialogList:$dialogList
            });
        },
        eventHandler:function(){
            ws.on('open', function(){
                console.log('success');
            });
            ws.on('userList', function(userListDTO){

            });
            /*ws.on('message', function(message){

            });*/
            $(window).on('unload', function(){
                ws.close();
            });
        }
    };
    module.exports = process;
});