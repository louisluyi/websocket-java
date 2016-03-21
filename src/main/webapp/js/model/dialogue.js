/**
 * Created by luyi-netease on 2016/3/21.
 */
'use strict';
define(function(require, exports, module){
    require('/js/tools/DateFormat');
    /**
     * Dialogue 构造函数
     * @param owner 类型：User，发起人
     * @param others 类型：[User]，接受者
     * @param ws 类型：MyWebsocket
     * @param userInfoList 包含用户列表的ul元素
     * @param dialogList 包含对话列表的ul元素
     * @constructor
     */
    var Dialogue = function(param){
        if(!param) throw '参数缺失';
        this.owner = param.owner;
        this.others = param.others;
        this.ws = param.ws;
        this.contentList = [];
        this.newMessageCount = 0;
        this.render(param.userInfoList, param.dialogList);
    };
    Dialogue.prototype.render = function(userInfoList, dialogList){
        var self = this;

        var $userInfo = $('<li class="item"><div class="user-img-container"><i class="new-message-count"></i></div><div class="user-content"><p class="username"></p><p class="text"></p><span class="time"></span></div></li>');
        self.userInfo = $userInfo[0];
        $userInfo.find('.username').html(self.usernames);
        for(var i = 0; i < self.others.length; ++i){
            $userInfo.find('.user-img-container').append('<img src="/img/weixin.png" alt="头像" />');
        }
        $userInfo.find('.time').html(new Date().format('HH:mm'));
        $userInfo.appendTo(userInfoList);

        var $dialog = $('<li class="item"><ul class="dialog-list"></ul><div class="send-container"><input type="text" class="message" /><a class="submit-btn">发送</a></div></li>');
        self.dialog = $dialog[0];
        $dialog.appendTo(dialogList);

        self.eventHandler();
    };
    Dialogue.prototype.eventHandler = function(){
        var self = this,
            $userInfo = $(self.userInfo),
            $dialog = $(self.dialog),
            $message = $dialog.find('.message'),
            $submit = $dialog.find('.submit-btn');
        //点击用户头像，切换对话框，并且清空新信息数
        $userInfo.on('click', function(){
            self.newMessageCount = 0;
            self.hideNewMessageCount();
            if($userInfo.hasClass('selected')) return;
            $userInfo.siblings('.item').removeClass('selected');
            $userInfo.addClass('selected');
            $dialog.siblings('.item').removeClass('selected');
            $dialog.addClass('selected');
        });
        //点击对话框，清空新信息数
        $(self.dialog).on('click', function(){
            self.newMessageCount = 0;
            self.hideNewMessageCount();
        });
        //信息输入时检查信息是否为空
        $dialog.on('keyup blur', '.message', function(){
            var msg = $.trim($(this).val());
            msg === '' ? $submit.removeClass('submit') : $submit.addClass('submit');
        });
        //提交信息
        $dialog.on('click', '.submit', function(){
            var msg = $.trim($(this).val());
            self.sendMessage(msg);
        });
        //接收信息
        self.ws.on('message', function(message){
            self.receiveMessage(message);
        });
    };
    /**
     * 发送信息
     * @param message
     */
    Dialogue.prototype.sendMessage = function(message){
        var self = this,
            messageDTO = {
                fromUser:self.owner.username,
                toUsers:[],
                message:message
            };
        var i, len = self.others.length;
        for(i = 0; i < len; ++i){
            messageDTO.toUsers.push(self.others[i].username);
        }
        self.ws.send(JSON.stringify(messageDTO));
    };
    /**
     * 收到信息
     * @param message
     */
    Dialogue.prototype.receiveMessage = function(message){
        var self = this,
            messageDTO;
        try{
            messageDTO = JSON.parse(message);
            if(self.checkMessage(messageDTO)){
                self.showNewMessage(messageDTO.message, messageDTO.fromUser);
            }
        }
        catch (e){
            //it doesn't belong to this dialogue
        }
    };
    /**
     * 检查信息是不是应该由这个dialogue接收
     * @param messageDTO
     * @returns {boolean}
     */
    Dialogue.prototype.checkMessage = function(messageDTO){
        var self = this,
            fromUser = messageDTO.fromUser.username,
            toUsers = messageDTO.toUsers;
        if(!fromUser || !toUsers) return false;
        var isExisted = function(username){
            var i, len;
            if(username === self.owner.username) return true;
            for(i = 0, len = self.others.length; i < len; ++i){
                if(username === self.others[i].username) return true;
            }
            return false;
        };
        if(!isExisted(fromUser)) return false;
        var i, len;
        for(i = 0, len = toUsers.length; i < len; ++i){
            if(!isExisted(toUsers[i].username)) return false;
        }
        return true;
    };
    /**
     * 显示接收的新信息
     * @param message
     * @param username
     */
    Dialogue.prototype.showNewMessage = function(message, username){
        var self = this,
            $newMessage = $('<li class="item"><img class="user-img" src="/img/weixin.png"><div class="container"><p class="username"></p><p class="message"></p></div></li>');
        self.newMessageCount ++;
        if(username === self.owner.username){
            $newMessage.addClass('from-owner');
        }
        $newMessage.find('.username').html(username);
        $newMessage.find('.message').html(message);
        $newMessage.appendTo($(self.dialog).find('.dialog-list'));
        self.showNewMessageCount();
        self.scrollToCurrentMessage();
    };
    /**
     * 显示当前信息数
     */
    Dialogue.prototype.showNewMessageCount = function(){
        var self = this,
            count = self.newMessageCount;
        count > 0 && $(self.userInfo).find('.new-message-count').show();
    };
    /**
     * 隐藏当前信息数
     */
    Dialogue.prototype.hideNewMessageCount = function(){
        $(this.userInfo).find('.new-message-count').hide();
    };
    /**
     * 滚动到最后一条信息的位置
     */
    Dialogue.prototype.scrollToCurrentMessage = function(){
        var self = this,
            $dialogList = $(self.dialog).find('.dialog-list'),
            top =  $dialogList.find('.item').last().position().top;
        top && $dialogList.scrollTop(top);
    };

    module.exports = Dialogue;
});

