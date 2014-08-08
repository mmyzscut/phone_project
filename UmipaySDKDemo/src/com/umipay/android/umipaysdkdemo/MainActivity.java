package com.umipay.android.umipaysdkdemo;

import net.umipay.android.GameParamInfo;
import net.umipay.android.GameUserInfo;
import net.umipay.android.UmiPaySDKManager;
import net.umipay.android.UmiPaymentInfo;
import net.umipay.android.UmipaySDKStatusCode;
import net.umipay.android.interfaces.InitCallbackListener;
import net.umipay.android.interfaces.LoginCallbackListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements 
OnClickListener,
InitCallbackListener,
LoginCallbackListener{
	
	View mLoginButton;
	View mRatePayButton;
	View mQuotaPayButton;
	View mAccountButton;
	ToggleButton mModeButton;
	
	boolean mIsInited = false;
	boolean mIsLogined = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//设置无标题  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //设置全屏  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.activity_demomain);

		//初始化Demo界面
		initView();
		//初始化DemoListener
		initListener();
		//初始化SDK
		initGameInfo();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_demomain);
		//初始化Demo界面
		initView();
		//初始化DemoListener
		initListener();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mLoginButton = findViewById(R.id.login);
		mRatePayButton = findViewById(R.id.rate_pay);
		mQuotaPayButton = findViewById(R.id.quota_pay);
		mAccountButton = findViewById(R.id.account);
		mModeButton = (ToggleButton) findViewById(R.id.mode_btn);
		mModeButton.setChecked(isTestMode());
	}
	/**
	 * 初始化Listener
	 */
	private void initListener() {
		mLoginButton.setOnClickListener(this);
		mRatePayButton.setOnClickListener(this);
		mQuotaPayButton.setOnClickListener(this);
		mAccountButton.setOnClickListener(this);
		mModeButton.setOnClickListener(this);
	}
	
	
	/**
	 * 初始化SDK
	 */
	private void initGameInfo() {
		GameParamInfo gameParamInfo = new GameParamInfo();
		gameParamInfo.setAppId("13480bc2ae0d5e32");//设置AppId
		gameParamInfo.setAppSecret("96f27505691e5f54");//设置AppSecret
		gameParamInfo.setTestMode(isTestMode()); //设置测试模式，如果正式发布时请设置为false
		UmiPaySDKManager.initSDK(this, gameParamInfo, this);//调用初始化接口
	}
	/**
	 * 初始化汇率模式支付页面
	 */
	private void initRatePayView() {
        UmiPaymentInfo paymentInfo = new UmiPaymentInfo();
        //业务类型，SERVICE_TYPE_QUOTA(固定额度模式，充值金额在支付页面不可修改)，SERVICE_TYPE_RATE(汇率模式，充值金额在支付页面可修改）
        paymentInfo.setServiceType(UmiPaymentInfo.SERVICE_TYPE_RATE);
        paymentInfo.setAmount(1000);  //【必填】设置用户需要充值金币(虚拟货币）数量
        paymentInfo.setRoleGrade("12");// 【必填】设置用户的游戏角色等级
        paymentInfo.setRoleId("125456");// 【必填】设置用户的游戏角色的ID
        paymentInfo.setRoleName("偶玩君");// 【必填】设置用户的游戏角色名字
        paymentInfo.setServerId("10086"); //【必填】设置用户所在的服务器ID
        paymentInfo.setSinglePayMode(true);//【可选】false:支付完成会允许继续充值； true： 支付完成后关闭支付界面,不能继续充值
        paymentInfo.setMinFee(1);// 【可选】定义用户每次充值的最低充值额minCost RMB，默认为1 RMB，对于银行卡支付，默认最少必须是2 RMB
        paymentInfo.setCustomInfo("");// 【可选】游戏开发商自定义数据。该值将在用户充值成功后，在充值回调接口通知给游戏开发商时携带该数据
        UmiPaySDKManager.showPayView(this, paymentInfo);//调用充值接口
	}
	/**
	 * 初始化定额模式支付页面
	 */
	private void initQuotaPayView() {
		UmiPaymentInfo paymentInfo = new UmiPaymentInfo();
        //业务类型，SERVICE_TYPE_QUOTA(固定额度模式，充值金额在支付页面不可修改)，SERVICE_TYPE_RATE(汇率模式，充值金额在支付页面可修改）
        paymentInfo.setServiceType(UmiPaymentInfo.SERVICE_TYPE_QUOTA);
        //定额支付金额，单位RMB
        paymentInfo.setPayMoney(10);
        //订单描述
        paymentInfo.setDesc("100元宝");
        //paymentInfo.setTradeno("TN2014041014461234");//（暂不开放）【可选】外部订单号
        paymentInfo.setRoleGrade("12"); //【必填】设置用户的游戏角色等级
        paymentInfo.setRoleId("123456");// 【必填】设置用户的游戏角色的ID
        paymentInfo.setRoleName("偶玩君");// 【必填】设置用户的游戏角色名字
        paymentInfo.setServerId("10086");//【必填】设置用户所在的服务器ID
        paymentInfo.setCustomInfo("");// 【可选】游戏开发商自定义数据。该值将在用户充值成功后，在充值回调接口通知给游戏开发商时携带该数据
        UmiPaySDKManager.showPayView(this, paymentInfo);//调用充值接口
	}
	/**
	 * 登陆
	 */
	private void login() {
		UmiPaySDKManager.showLoginView(this, this);
	}
	/**
	 * 充值
	 */
	private void ratePay() {
		if(mIsLogined) {
			//已登陆，初始化支付页面
			initRatePayView();
		} else {
			//登陆
			login();
		}
	}
	private void quotaPay() {
		if(mIsLogined) {
			//已登陆，初始化支付页面
			initQuotaPayView();
		} else {
			//登陆
			login();
		}
	}
	/**
	 * 账户中心
	 */
	private void showAccountCenter() {
		if(mIsLogined) {
			//已登陆，调用帐号中心接口
			UmiPaySDKManager.showAccountManageView(this);
		} else {
			//登陆
			login();
		}
	}
	/**
	 * 切换帐号
	 */
	private void changeAccount() {
		UmiPaySDKManager.showChangeAccountView(this, this);
	}
	
	private boolean isTestMode(){
		SharedPreferences sp = getSharedPreferences("testmode.prf", 0);
		return sp.getBoolean("testmode", true);
	}
	private void setTestMode(boolean testmode){
		SharedPreferences sp = getSharedPreferences("testmode.prf", 0);
		sp.edit().putBoolean("testmode", testmode).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "切换帐号");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==1) {
			//切换帐号
			changeAccount();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void callback(int code, GameUserInfo userinfo) {
		if (code == UmipaySDKStatusCode.SUCCESS&&userinfo!=null) {
			mIsLogined = true;
			Log.d("UmipaySDKDemo", String.format("登录成功，用户id：%s，签名Sign：%s，timestap：%d", userinfo.getUid(),userinfo.getSign(),userinfo.getTimestamp_s()));
		} else {
			mIsLogined = false;
			toast("取消登录");
		}
		
	}

	@Override
	public void onSdkInitFinished(int code, String msg) {
		if (code == UmipaySDKStatusCode.SUCCESS) {
			mIsInited = true;
			toast("初始化成功！");
		} else {
			mIsInited = false;
			toast("初始化失败："+msg);
		}
		
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mLoginButton)) {
			login();
		} else if (v.equals(mRatePayButton)) {
			ratePay();
		} else if (v.equals(mQuotaPayButton)) {
			quotaPay();
		} else if (v.equals(mAccountButton)) {
			showAccountCenter();
		} else if (v.equals(mModeButton)) {
			setTestMode(mModeButton.isChecked());
			if (isTestMode()) {
				Toast.makeText(this, "当前打开测试模式，请重新登录", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "当前关闭测试模式，请重新登录", Toast.LENGTH_SHORT).show(); 
			}
			initGameInfo();
		}
		
	}
	
	private void toast(String msg){
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
