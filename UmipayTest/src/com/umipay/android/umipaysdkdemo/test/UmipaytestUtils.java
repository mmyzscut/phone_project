package com.umipay.android.umipaysdkdemo.test;

import java.util.Random;

import android.R.integer;
import android.os.SystemClock;

import com.robotium.solo.Solo;


public class UmipaytestUtils {

	//命名规则 位置_控件类型_控件标识1_标识2
	//控件类型：ET（EditText）,BT （Button）,TAB, LINK,WEB(WebView)

	//主界面
	public static final String MAINPAGE_BT_REGISTRATION_LOGIN="注册登录";
	public static final String MAINPAGE_BT_ACCOUNT="账户中心";
	public static final String MAINPAGE_BT_FIX_RATE_CHARGE="充值中心\n（汇率模式）";
	public static final String MAINPAGE_BT_EXCHANG_RATE_CHARGE="充值中心\n（定额模式）";
	public static final String MAINPAGE_TV_TITTLE="游戏支付平台";
	public static final String MAINPAGE_TB_TESTCLOSED="当前测试模式关闭";
	public static final String MAINPAGE_TB_TESTOPENED="当前测试模式开启";
	
	//注册界面
	public static final String REGISTRATIONPAGE_TAB_REGISTRATION="注册帐号";
	public static final String REGISTRATIONPAGE_ET_NAME="hello01234hello123";
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
	public static final String  ACCOUNTPAGE_WEB_BINDEMAIL="绑定邮箱";
	public static final String ACCOUNTPAGE_WEB_MODIFYPASSWORD="修改密码";
	
	//订单记录界面
	public static final String RECORDPAGE_WEB__RETURN="返回账户中心";
	
	//意见反馈界面
	public static final String FEEDBACKPAGE_WEB_INSERT="添加反馈";
	public static final String FEEDBACKPAGE_WEB_SUBMIT="提 交";
	public static final String FEEDBACK_CONTENT="你好";
	public static final String FEEDBACK_TYPE_CHARGE="充值问题";
	public static final String FEEDBACK_TYPE_ACCOUNT="账户问题";
	public static final String FEEDBACK_TYPE_OTHER="其他";
	

	//提示信息
	public static final String WAIT_NATWORK_COMUNICATION="网络正在通讯中，请稍等";
	public static final String WAIT_LOADING="正在努力加载中，请稍等";
	public static final String REQUEST_ERROR="请求错误，请检查网络设置";
	
	public static final String INIT_SUCCEED="初始化成功";
	
	public static final String ACCOUNT_EXIST="账户名已存在";
	public static final String ACCOUNT_ILLEGAL="连接失败，错误码264";
	
	public static final String GET_RECORD_FIALED="获取用户订单失败";
	public static final String FEEDBACK_CONTENT_INPUT_ERROR="请填写反馈内容";
	public static final String FEEDBACK_TYPE_ERROR="请选择反馈类型";

//	public static final Long TIMEOUT=20000l;
//	
//	public static boolean waitForText(Solo solo, String text){
//			final long endTime = SystemClock.uptimeMillis() + TIMEOUT;
//			boolean foundAnyMatchingView = false;
//			int y = 100;  //自定义滚动距离，指定滑动屏幕高度的1/4距离也可以
//			while (SystemClock.uptimeMillis() < endTime) {
//				foundAnyMatchingView = solo.waitForText(text, 0, 1, false);
//				if (foundAnyMatchingView){
//					return true;
//				}
//				scrollVertical(solo, y);
//			}
//			return foundAnyMatchingView;
//		}
	
	/**
	 * 是否是测试模式
	 * 
	 */
	public static boolean isTestMode(Solo solo){
		boolean result=solo.searchToggleButton(MAINPAGE_TB_TESTOPENED);
		return result;
	}

	

	/**
	 * 是否是主界面
	 */
	public static boolean isMainPage(Solo solo){
		boolean result =solo.waitForText(MAINPAGE_TV_TITTLE);
		return result;
	}

	/**
	 * 是否是绑定手机的界面（登录的时候，未绑定手机）
	 */
	public static boolean isBindphonePage(Solo solo) {
		boolean result = solo.waitForText(BINDPHONEPAGE_BT_IGNORE);
		return result;
	}

	/**
	 * 是否是账户中心的界面
	 */

	public static boolean isAccountPage(Solo solo) {
		boolean result = solo.waitForText(ACCOUNTPAGE_WEB_RECORD) && solo.waitForText(ACCOUNTPAGE_WEB_FEEDBACK)
				&& solo.waitForText(ACCOUNTPAGE_WEB_MODIFYPASSWORD);
		return result;
	}

	/**
	 * 是否是意见反馈界面
	 */
	public static  boolean isFeedbackPage(Solo solo) {
		boolean result = solo.waitForText(FEEDBACK_TYPE_ACCOUNT) & solo.waitForText(FEEDBACKPAGE_WEB_SUBMIT);
		return result;
	}
	
	/**
	 * 生成纯数字帐号
	 * 
	 * @parma type: 0表示超短（<min 4位，在此取5位），1表示正常 (min 4~max 32位)，2表示超长（>max
	 *        32，在此取33位），4取左边缘值（=min 4），5区右边缘值（=max 32）
	 * @return 生成的帐号
	 * 
	 */
	public static String getPureDigital(int type) {

		String string = "";
		Random sandRandom = new Random();
		switch (type) {
		case 0:
			Integer i = sandRandom.nextInt(1000);
			string = i.toString();
			break;
		case 1:
			Long l1 = sandRandom.nextLong();
			l1=Math.abs(l1);
			string =l1.toString();
			int lenth1 = string.length();
			if (lenth1 < 4) {
				string = string + "0000";
			}
			break;
		case 2:
			Long l2 = sandRandom.nextLong();
			l2=Math.abs(l2);
			string = l2.toString();
			int lenth2 = string.length();
			for (int j = 0; j < 33 - lenth2; j++) {
				string = string + "0";
			}
			break;
		case 3:
			Integer i2 = sandRandom.nextInt(9000);
			i2 = i2 + 1000;
			string = i2.toString();
			break;
		case 4:
			Long l3 = sandRandom.nextLong();
			l2=Math.abs(l3);
			string = l2.toString();
			int lenth3 = string.length();
			for (int j = 0; j < 32 - lenth3; j++) {
				string = string + "0";
			}
		}

		return string;
	}
	/**
	 * 加载的时候，等待的时间
	 */

	public static  long waitTime(Solo solo, String text) {
		long startTime = SystemClock.uptimeMillis();
		solo.sleep(2000);
		boolean result = solo.waitForText(text);
		while (result) {
			solo.sleep(1000);
			result = solo.waitForText(text);
		}
		long endTime = SystemClock.uptimeMillis();
		return (endTime - startTime) / 1000;
	}
	

}
