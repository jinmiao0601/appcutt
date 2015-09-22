package com.appcutt.demo.fragment;import android.content.BroadcastReceiver;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.os.Handler;import android.support.v4.app.Fragment;import android.view.KeyEvent;import android.widget.TextView;import com.appcutt.demo.handler.RequestHandler;import java.lang.ref.WeakReference;/** * 增加Fragment显示时的调用机制 * * @author ouyangjinmiao 2015-09-21 */public class BaseFragment extends Fragment implements IBaseFragment {    private boolean isSelected = false;    /**     * 首页自定义标题栏     */    protected static int mMessageCount; // 记录消息未读个数    protected TextView mMessageNum;    /**     * 如果Fragment被destroy或detach，则不用回调.     */    protected Handler mRequestHandler = new FragmentRequestHandler(BaseFragment.this);    /**     * 仅用于接受应用退出广播，程序退出时有机会做一些必要的清理工作     */    private BroadcastReceiver mBaseBroadcastReceiver = new BroadcastReceiver() {        @Override        public void onReceive(Context context, Intent intent) {            onReceiverBroadcast(context, intent);            String action = intent.getAction();        }    };    /**     * 当界面显示的时候调用，仅仅是显示时     */    @Override    public void attachToWindow() {        if (isSelected) {            return;        }        setSelected(true);        if (getActivity() != null) {            onActive();        }    }    @Override    public void onResume() {        super.onResume();//        updateNewMessageStatus(mMessageCount);    }    @Override    public void onActivityCreated(Bundle savedInstanceState) {        super.onActivityCreated(savedInstanceState);        if (isSelected) {            onActive();        }    }    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);    }    @Override    public void onDestroy() {        super.onDestroy();    }    /**     * 广播监听     *     * @return     */    protected String[] getIntentFilterActions() {        return null;    }    /**     * 接收到广播时回调     *     * @param context     * @param intent     */    protected void onReceiverBroadcast(Context context, Intent intent) {        String action = intent.getAction();    }    /**     * 当界面移走的时候调用     */    @Override    public void detachToWindow() {        if (!isSelected) {            return;        }        setSelected(false);        onInactive();    }    /**     * 当前界面是否显示     *     * @return     */    public boolean isSelected() {        return isSelected;    }    public void setSelected(boolean selected) {        this.isSelected = selected;    }    /**     * 当Fragment显示到界面上时调用     */    public void onActive() {    }    /**     * 当Fragment不再显示时调用     */    public void onInactive() {    }    /**     * 仅用于Tabs中的Fragment，点击Tab刷新     */    @Override    public void onTabRefresh() {    }    public boolean onKeyDown(int keyCode, KeyEvent event) {        return false;    }    private static class FragmentRequestHandler extends RequestHandler {        private WeakReference<BaseFragment> weakFragment;        public FragmentRequestHandler(BaseFragment baseFragment) {            weakFragment = new WeakReference<BaseFragment>(baseFragment);        }    }}