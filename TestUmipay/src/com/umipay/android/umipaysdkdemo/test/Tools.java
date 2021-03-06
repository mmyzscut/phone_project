package com.umipay.android.umipaysdkdemo.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import android.app.Activity;
import android.content.Context;
import android.test.TouchUtils;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class Tools {
	public static final String REGISTER_NAME_INPUT = "帐号（字母/数字/邮箱/QQ/手机号）";
	public static final String REGISTER_PSW_INPUT = "密码（6~32位字符组合）";
	public static final String LOGIN_OR_REGISTER_BTN = "login";
	public static final String RATE_PAY = "rate_pay";
	public static final String QUOTA_PAY = "quota_pay";
	public static final String ACCOUNT_CENTER = "account";
	public static final String LOGIN_VIEW = "umipay_login_tab_tv";
	public static final String REGISTER_VIEW = "umipay_register_tab_tv";
	public static final String LOGIN_BTN = "umipay_login_btn";
	public static final String REGISTER_BTN ="umipay_register_btn";
	public static final String NAME_BOX = "umipay_name_box";
	public static final String PSW_BOX = "umipay_psw_box";
	public static final String PHONE_BOX = "umipay_phone_box";
	public static final String BIND_BOX = "umipay_bind_phone_cb";
	public static final String SELECT_BTN = "umipay_account_select_btn";
	public static final String SELECT_SIGN = "已有帐号登录";
	public static final String TERMS_OF_SERVICE = "偶玩服务条款";
	public static final String LOGIN_SIGN = "一键试玩";
	public static final String REGISTER_SIGN = "立即注册";
	public static final String LOGIN_SUCCESS_SIGN = "正在进入游戏";
	public static final String RATE_PAY_SIGN = "海外便捷支付";
	public static final String QUOTA_PAY_SIGN = "10";
	public static final String ACCOUNT_USER_SIGN = "修改密码";
	public static final String ACCOUNT_FAST_SIGN = "绑定偶玩帐号";
	public static final String SERVICE_SIGN = "偶玩游戏平台用户协议";
	public static final String FORGET_PSW = "忘记密码";
	public static final String FIND_PSW = "找回密码";
	public static final String QQ_OAUTH = "umipay_qq_oauth";
	public static final String SINA_OAUTH = "umipay_sina_oauth";
	public static final String QQ_SIGN = "亿万用户已选择QQ帐号登录应用";
	public static final String SINA_SIGN = "授权 偶玩游戏中心 访问你的微博帐号";
	public static final String MAIN_SIGN = "游戏支付平台";
	public static final String ORDER_LIST = "订单记录";
	public static final String ORDER_SIGN = "没有充值记录";
	public static final String FEEDBACK = "问题反馈";
	public static final String FEEDBACK_SIGN = "意见反馈";
	public static final String EMAIL_BIND = "绑定邮箱";
	public static final String EMAIL_SIGN = "邮箱绑定";
	public static final String PHONE_BIND = "绑定手机";
	public static final String PHONE_SIGN = "您的帐号安全级别";
	public static final String ACCOUNT_BIND = "绑定偶玩帐号";
	public static final String ACCOUNT_BIND_SIGN = "帐号绑定";
	public static final String BACK_TO_ACCOUNTCENTER = "返回账户中心";
	public static final String BACK_TO_MAIN = "退出账户中心";
	public static final String ADD_FEEDBACK = "添加反馈";
	public static final String ADD_FEEDBACK_SIGN = "其他";
	public static final String FEEDBACK_TAG_1 = "充值问题";
	public static final String FEEDBACK_TAG_2 = "帐号问题";
	public static final String FEEDBACK_TAG_3 = "其他";
	public static final String FEEDBACK_INPUT = "content";
	public static final String FEEDBACK_SUBMIT = "btn cf mt12 db tac fs18";
	public static final String FEEDBACK_WRONG_NOTAG = "请选择反馈类型";
	public static final String FEEDBACK_WRONG_NOINPUT = "请填写反馈内容";
	public static final String WEB_NAME_BOX = "username";
	public static final String WEB_PSW_BOX = "password";
	public static final String NAME_BIND_BTN = "btn cf mt12 db tac fs18";
	public static final String NAME_BIND_SUCCESS = "绑定成功";
	public static final String CHANGE_PSW_SUCCESS = "修改成功";
	public static final String OLD_PSW = "oldpw";
	public static final String NEW_PSW = "newpw";
	public static final String PSW_CHANGE_SUBMIT = "btn cf mod mt12 db tac fs18";
	
	
	public static boolean clickById(Solo solo, String strid, String waitForText){
		boolean res = false;
		if(strid=="")
			return res;
		try{
			Activity act = solo.getCurrentActivity();
			int id = act.getResources().getIdentifier(strid, "id", act.getPackageName());
			View view = act.findViewById(id);
			solo.clickOnView(view);
			if(waitForText != null){
				res = solo.waitForText(waitForText);
			}else{
				res = true;
			}
		}catch(Exception ex){
			Log.e("testUmipay", ex.getMessage());
		}
		return res;
	}
	
	public static boolean clickByText(Solo solo, String text, String waitForText){
		boolean res = false;
		if(text == "")
			return res;
		try{
			TextView tv = solo.getText(text);
			solo.clickOnView(tv);
			if(waitForText != null){
				res = solo.waitForText(waitForText);
			}else{
				res = true;
			}
		}catch(Exception ex){
			Log.e("testUmipay", ex.getMessage());
		}
		return res;
	}
	
	public static boolean clickOnWebByClassName(Solo solo, String className, String waitForText){
		boolean res = false;
		if(className == "")
			return res;
		try{
			solo.clickOnWebElement(By.className(className));
			if(waitForText != null){
				res = solo.waitForText(waitForText);
			}else{
				res = true;
			}
		}catch(Exception ex){
			Log.e("testUmipay", ex.getMessage());
		}
		return res;
	}
	
	public static boolean enterTextInWeb(Solo solo, String text, String input_text, int sleepTime){
		boolean res = false;
		if(text == "")
			return res;
		try{
			solo.enterTextInWebElement(By.id(text), input_text);
		}catch(Exception ex){
			Log.e("testUmipay", ex.getMessage());
		}
		
		return res;
	}
	
	public static boolean enterTextByText(Solo solo, String text, String input_text, int sleepTime){
		boolean res = false;
		if(text == "")
			return res;
		try{
			
			EditText et = (EditText) solo.getText(text);
			//et.setInputType(InputType.TYPE_NULL);
			solo.typeText(et, input_text);
			//solo.enterText(et, input_text);
			solo.sleep(sleepTime);
			res = true;
			
		}catch(Exception ex){
			Log.e("testUmipay", ex.getMessage());
		}
		return res;
	}
	
	public static boolean enterTextById(Solo solo, String strid, String text, int sleepTime){
		boolean res = false;
		try{
			Activity act = solo.getCurrentActivity();
			int id = act.getResources().getIdentifier(strid, "id", act.getPackageName());
			EditText et = (EditText)solo.getView(id);
			//et.setInputType(InputType.TYPE_NULL);
			solo.typeText(et, text);
			//solo.enterText(et, text);
			solo.sleep(sleepTime);
			return res=true;
		}catch(Exception ex){
			Log.e("testUmipay", ex.getMessage());
		}
		return res;
	}
	
	public static boolean removeText(Solo solo, String strid){
		boolean res = false;
		Activity act = solo.getCurrentActivity();
		int id = act.getResources().getIdentifier(strid, "id", act.getPackageName());		
		EditText editText = (EditText) act.findViewById(id);
		solo.clearEditText(editText);
		res = true;
		return res;
	}
	
	public static String getRandomName(int len){
		String name = "";
		String chars = "qwertyuiopasdfghjklzxcvbnm1234567890";
		if(len == 0)
			return name;
		else{
			for(int i=0;i<len;i++){
				name = name + chars.charAt((int)(Math.random()*36));
			}
			return name;
		}
	}
	
	public static String getRandomNameFromNum(int len){
		String name = "";
		String chars = "1234567890";
		if(len == 0)
			return name;
		else{
			for(int i=0;i<len;i++){
				name = name + chars.charAt((int)(Math.random()*10));
			}
			return name;
		}
	}
	
	public static boolean login(Solo solo, String name, String psw, String sign){
		boolean actual = false;
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.removeText(solo, Tools.NAME_BOX);
		Tools.enterTextById(solo, Tools.NAME_BOX, name, 1000);
		Tools.enterTextById(solo, Tools.PSW_BOX, psw, 1000);
		actual = Tools.clickById(solo, Tools.LOGIN_BTN, sign);
		if(sign == Tools.LOGIN_SUCCESS_SIGN){
			solo.waitForText(Tools.MAIN_SIGN);
		}else{
			solo.goBack();
			solo.waitForText(Tools.MAIN_SIGN);
		}
		return actual;
	}
	
	public static boolean fastLogin(Solo solo){
		boolean actual = false;
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		actual = Tools.clickByText(solo, Tools.LOGIN_SIGN, Tools.LOGIN_SUCCESS_SIGN);
		solo.waitForText(Tools.MAIN_SIGN);
		return actual;
	}
	
}
