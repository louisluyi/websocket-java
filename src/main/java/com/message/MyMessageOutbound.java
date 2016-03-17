package com.message;

import org.apache.catalina.websocket.WsOutbound;
import org.apache.coyote.http11.upgrade.UpgradeOutbound;

/**
 * Created by luyi-netease on 2016/3/17.
 */
public class MyMessageOutbound extends WsOutbound {

    public MyMessageOutbound(UpgradeOutbound upgradeOutbound) {
        super(upgradeOutbound, DEFAULT_BUFFER_SIZE, DEFAULT_BUFFER_SIZE);
    }

}
