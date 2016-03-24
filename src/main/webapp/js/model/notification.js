/**
 * Created by luyi-netease on 2016/3/24.
 */
'use strict';

define(function(require, exports, module){

    var MyNotification = function(){
        this.allow = false;
        this.check();
    };

    MyNotification.prototype.check = function(){
        var self = this;
        if(window.Notification){
            switch (Notification.permission){
                case 'default':
                    Notification.requestPermission(function(){
                        if(Notification.permission === 'granted'){
                            self.allow = true;
                        }
                    });
                    break;
                case 'granted':
                    self.allow = true;
                    break;
                default:
                    this.allow = false;
            }
        }
        else if(window.webkitNotifications){
            if(webkitNotifications.checkPermission() === 0){
                self.allow = true;
            }
            else{
                webkitNotifications.requestPermission(function(){
                    if(webkitNotifications.checkPermission() === 0){
                        self.allow = true;
                    }
                });
            }
        }
    };

    /**
     * 显示通知
     * @param url 图片url
     * @param title 标题
     * @param body 主体内容
     */
    MyNotification.prototype.show = function(param){
        if(!param) throw '参数不足';
        var self = this;
        if(!self.allow) return;
        self.close(); //关闭之前的通知，只保留最新一条
        if(window.Notification){
            self.sample = new Notification(param.title, {
                body:param.body,
                icon:param.url
            });
        }
        else if(window.webkitNotifications){
            self.sample = webkitNotifications.createNotification(param.url, param.title, param.body);
            self.sample.show();
        }
    };
    MyNotification.prototype.close = function(){
        var self = this;
        if(self.sample){
            typeof self.sample.close === "function" ? self.sample.close() : self.sample.cancel();
        }
    };

    module.exports = MyNotification;
});