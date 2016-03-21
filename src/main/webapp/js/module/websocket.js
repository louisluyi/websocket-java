/**
 * Created by luyi-netease on 2016/3/21.
 */
'use strict';
define(function(require, exports, module) {
    var MyWebSocket = function(url){
        this.websocket = new WebSocket(url);
        this.onOpen = [];
        this.onMessage = [];
        this.onClose = [];
        this.send = this.websocket.send;
        this.close = this.websocket.close;
    };
    MyWebSocket.prototype.bindEvent = function(){
        var self = this,
            i, len;
        self.websocket.onopen = function(){
            for(i = 0, len = self.onOpen.length; i < len; ++i){
                if(typeof self.onOpen[i] === 'function'){
                    self.onOpen[i].apply(null, arguments);
                }
            }
        };
        self.websocket.onclose = function(){
            for(i = 0, len = self.onClose.length; i < len; ++i){
                if(typeof self.onClose[i] === 'function'){
                    self.onClose[i].apply(null, arguments);
                }
            }
        };
        self.websocket.onmessage = function(){
            for(i = 0, len = self.onMessage.length; i < len; ++i){
                if(typeof self.onMessage[i] === 'function'){
                    self.onMessage[i].apply(null, arguments);
                }
            }
        };
    };
    /**
     * 事件绑定，可以自定义事件
     * @param type
     * @param fn
     * @returns {MyWebSocket}
     */
    MyWebSocket.prototype.on = function(type, fn){
        var self = this;
        var onUserList = function(message){
            try{
                message = JSON.parse(message);
                if(message.type === 1){
                    fn(message);
                }
            }
            catch(e){
                //丢弃
            }
        };
        var onDialogue = function(message){
            try{
                message = JSON.parse(message);
                if(message.type === 2){
                    fn(message);
                }
            }
            catch(e){
                //丢弃
            }
        };
        if(typeof fn !== "function") return;
        switch (type){
            case 'open':
                self.onOpen.push(fn);
                break;
            case 'close':
                self.onClose.push(fn);
                break;
            case 'message':
                self.onMessage.push(fn);
                break;
            case 'userList':
                self.onMessage.push(onUserList);
                break;
            case 'dialogue':
                self.onMessage.push(onDialogue);
                break;
        }
        return self;
    };

    module.exports = MyWebSocket;
});
