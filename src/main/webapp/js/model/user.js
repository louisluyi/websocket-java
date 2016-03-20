/**
 * Created by louis on 2016/3/21.
 */
define(function(require, exports, module){

    var userList = [];
    var User = function(username){
        this.username = username;
        userList.push(this);
    }
    User.prototype.removeUser = function(username){
        for(var i = 0, len = userList.length; i < len; ++i){
            if(userList[i].username === username){
                return userList.splice(i, 1);
            }
        }
    }
    User.getUserList = function(){
        return userList;
    }

    module.exports = User;
});