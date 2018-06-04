package com.healthmall.sail.cat_doctor.websocket;


import com.google.gson.Gson;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.bean.User;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.NetUtils;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;


/**
 * 与服务器通过WebSokect建立长连接
 * Created by mai on 2017/11/17.
 */
public class CatWebSocketClient extends WebSocketClient {

//  public static final String uri = "ws://dev-accwssail.healthmall.cn/server/bodyAnaylzer/data";
    public static final String uri = "ws://accwssail.healthmall.cn/server/bodyAnaylzer/data";

    static CatWebSocketClient instance;

    static Subscription sbReconnect;

    static Subscription sbPing;


    private CatWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void connect() {
        super.connect();
    }

    public static CatWebSocketClient getInstance() {

        if (instance == null) {
            synchronized (CatWebSocketClient.class) {
                if (instance == null) {
                    try {
                        instance = new CatWebSocketClient(new URI(uri), new Draft_6455());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;

    }


    @Override
    public void send(String text) throws NotYetConnectedException {
        MLog.log("发送信息--->" + text);
        super.send(text);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        MLog.log("onOpen() returned: " + "连接到服务器");
        if (sbReconnect != null) {
            sbReconnect.unsubscribe();
            sbReconnect = null;
        }

        if (sbPing != null)
            sbPing.unsubscribe();

        sbPing = Observable.interval(10000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                //发送心跳包
                MLog.log("---发送心跳包---");
                instance.sendPing();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        /**
         * 连接上需要注册
         */
        String deviceId = SharedPreferencesHelper.getInstance(MyApplication.get().getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);
        instance.send(CmdUtils.getRegisterMsg(deviceId));
    }

    @Override
    public void onMessage(String message) {
        MLog.log("run() returned: " + message);
        try {
            JSONObject jsonObject = new JSONObject(message);

            switch (jsonObject.getString("message")) {
                case "unlock":
                    User user = new Gson().fromJson(message, User.class);

                    MyApplication.get().loginSucc(user);
                    break;
                case "lock":
                    User user1 = new Gson().fromJson(message, User.class);
                    MyApplication.get().lock(user1);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        MLog.log("onClose() returned: " + reason);
        instance = null;
        if (sbPing != null) {
            sbPing.unsubscribe();
            sbPing = null;
        }
        reConnect();

    }

    @Override
    public void onError(Exception ex) {
        MLog.log("onError() returned: " + ex);
        instance = null;
        if (sbPing != null) {
            sbPing.unsubscribe();
            sbPing = null;
        }
        reConnect();
    }

    /**
     * 开启定期检测线程
     */
    public static void reConnect() {

        if (sbReconnect != null) {
            sbReconnect.unsubscribe();
        }

        sbReconnect = Observable.interval(3000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if (NetUtils.isNetworkAvailable(MyApplication.get().getApplicationContext())) {
                    CatWebSocketClient.getInstance().connect();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
