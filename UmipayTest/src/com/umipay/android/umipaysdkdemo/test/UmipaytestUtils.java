package com.umipay.android.umipaysdkdemo.test;


public class UmipaytestUtils {

	//命名规则 位置_控件类型_控件标识1_标识2
	//控件类型：ET（EditText）,BT （Button）,TAB, LINK,WEB(WebView)

	//主界面
	public static final String MAINPAGE_BT_REGISTRATION_LOGIN="注册登录";
	public static final String MAINPAGE_BT_ACCOUNT="账户中心";
	public static final String MAINPAGE_BT_FIX_RATE_CHARGE="充值中心\n（汇率模式）";
	public static final String MAINPAGE_BT_EXCHANG_RATE_CHARGE="充值中心\n（定额模式）";
	
	//注册界面
	public static final String REGISTRATIONPAGE_TAB_REGISTRATION="注册帐号";
	public static final String REGISTRATIONPAGE_ET_NAME="hell01234";
	public static final String REGISTRATIONPAGE_ET_PASSWORD="123456";
	public static  final String REGISTRATIONPAGE_LINK_OUWANSERVER="偶玩服务条款";
	public static final String  OUWANSERVERPAGE_CONTENT="偶玩游戏平台用户协议";
	public static final String REGISTRATIONPAGE_BT_REGISTRATION="立即注册";
	
	//登录界面
	public static final String LOGINPAGE_BT_LOGIN="登录";
	
	//绑定手机对话框
	public static final String BINDPHONEPAGE_BT_IGNORE="跳过此步";
	
	//账户中心界面
	public static final String ACCOUNTPAGE_WEB_RECORD="订单记录";
	public static final String ACCOUNTPAGE_WEB_FEEDBACK="问题反馈";
	public static final String  ACCOUNTPAGE_WEB_BINDEMAIL="";
	
	//订单记录界面
	public static final String RECORDPAGE_WEB__RETURN="返回账户中心";
	
	//意见反馈界面
	public static final String FEEDBACKPAGE_WEB_INSERT="添加反馈";
	public static final String FEEDBACKPAGE_WEB_SUBMIT="提交";
	public static enum FEEDBACKPAGE_WEB_TYPE{
		//充值问题,帐号问题,其他
		RED,YELLO,BLUE
	}
	
	//提示信息
	public static final String WAIT_NATWORK_COMUNICATION="网络正在通讯中，请稍等";
	public static final String WAIT_LOADING="正在努力加载中，请稍等";
	public static final String REQUEST_ERROR="请求错误，请检查网络设置";
	public static final String GET_RECORD_FIALED="获取用户订单失败";
	
	public static void main(String args[]){
		System.out.println(UmipaytestUtils.FEEDBACKPAGE_WEB_TYPE.RED);
		System.out.println(UmipaytestUtils.FEEDBACKPAGE_WEB_TYPE.BLUE);
		System.out.println(UmipaytestUtils.FEEDBACKPAGE_WEB_TYPE.YELLO);
	}
}
