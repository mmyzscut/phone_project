package com.umipay.android.umipaysdkdemo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;

import com.robotium.solo.Solo;
import com.umipay.android.umipaysdkdemo.MainActivity;

public class N03_TestAccountCenter extends ActivityInstrumentationTestCase2<MainActivity>{
	private Solo solo;
	private boolean expected = true;
	private boolean actual = false;
	
	
	public N03_TestAccountCenter(){
		super(MainActivity.class);
	}
	
	@Before
	public void setUp() throws Exception{
		Log.v("N03_TestAccountCenter", "执行setup()");
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@After
	public void tearDown() throws Exception{
		Log.v("N03_TestAccountCenter", "执行tearDown()");
		solo.finishOpenedActivities();
	}
	
	//测试是否绑定偶玩帐号对账户中心的影响
	@Test
	public void testAccountCenter_1(){		
		//一键登录
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.clickByText(solo, Tools.LOGIN_SIGN, Tools.LOGIN_SUCCESS_SIGN);
		solo.waitForText(Tools.MAIN_SIGN);
		actual = Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_FAST_SIGN);
		assertEquals(Tools.ACCOUNT_FAST_SIGN, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.MAIN_SIGN);
		//偶玩帐号登录
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.removeText(solo, Tools.NAME_BOX);
		String inputName = "abcdefghgg";
		String inputPsw = "1234567";
		Tools.enterTextById(solo, Tools.NAME_BOX, inputName, 1000);
		Tools.enterTextById(solo, Tools.PSW_BOX, inputPsw, 1000);
		Tools.clickById(solo, Tools.LOGIN_BTN, Tools.MAIN_SIGN);
		actual = Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_USER_SIGN);
		assertEquals(Tools.ACCOUNT_USER_SIGN, expected, actual);
	}
	
	//测试一键登录情况下的账户中心
	@Test
	public void testAccountCenter_2(){
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.clickByText(solo, Tools.LOGIN_SIGN, Tools.MAIN_SIGN);
		Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_FAST_SIGN);
		//测试点击订单记录
		actual = Tools.clickByText(solo, Tools.ORDER_LIST, Tools.ORDER_SIGN);
		assertEquals(Tools.ORDER_LIST, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.ACCOUNT_FAST_SIGN);
		//测试点击问题反馈
		actual = Tools.clickByText(solo, Tools.FEEDBACK, Tools.FEEDBACK_SIGN);
		assertEquals(Tools.FEEDBACK, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.ACCOUNT_FAST_SIGN);
		//测试点击绑定邮箱
		actual = Tools.clickByText(solo, Tools.EMAIL_BIND, Tools.EMAIL_SIGN);
		assertEquals(Tools.EMAIL_BIND, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.ACCOUNT_FAST_SIGN);
		//测试点击绑定手机
		actual = Tools.clickByText(solo, Tools.PHONE_BIND, Tools.PHONE_SIGN);
		assertEquals(Tools.PHONE_BIND, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.ACCOUNT_FAST_SIGN);
		//测试点击绑定偶玩帐号
		actual = Tools.clickByText(solo, Tools.ACCOUNT_BIND, Tools.ACCOUNT_BIND_SIGN);
		assertEquals(Tools.ACCOUNT_BIND, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.ACCOUNT_FAST_SIGN);
	}	
	
	//测试订单记录界面、问题反馈界面
	@Test
	public void testAccountCenter_3(){
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.clickByText(solo, Tools.LOGIN_SIGN, Tools.MAIN_SIGN);
		//solo.waitForText(Tools.MAIN_SIGN);
		Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_FAST_SIGN);
		//测试订单记录页面
		Tools.clickByText(solo, Tools.ORDER_LIST, Tools.ORDER_SIGN);
		actual = Tools.clickByText(solo, Tools.BACK_TO_ACCOUNTCENTER, Tools.ACCOUNT_FAST_SIGN);
		assertEquals(Tools.BACK_TO_ACCOUNTCENTER, expected, actual);
		//测试问题反馈界面
		Tools.clickByText(solo, Tools.FEEDBACK, Tools.FEEDBACK_SIGN);
		actual = Tools.clickByText(solo, Tools.ADD_FEEDBACK, Tools.ADD_FEEDBACK_SIGN);
		assertEquals(Tools.ADD_FEEDBACK, expected, actual);
		//测试添加反馈
		String input_text = "abc1234567890";
		//不选择标签
		Tools.enterTextInWeb(solo, Tools.FEEDBACK_INPUT, input_text, 0);
		actual = Tools.clickOnWebByClassName(solo, Tools.FEEDBACK_SUBMIT, Tools.FEEDBACK_WRONG_NOTAG);
		assertEquals(Tools.FEEDBACK_SUBMIT, expected, actual);
		//选择标签并输入反馈内容
		Tools.clickByText(solo, Tools.FEEDBACK_TAG_2, null);
		actual = Tools.clickOnWebByClassName(solo, Tools.FEEDBACK_SUBMIT, input_text);
		//选择标签但不输入反馈内容
		Tools.clickByText(solo, Tools.ADD_FEEDBACK, Tools.ADD_FEEDBACK_SIGN);
		Tools.clickByText(solo, Tools.FEEDBACK_TAG_2, null);
		actual = Tools.clickOnWebByClassName(solo, Tools.FEEDBACK_SUBMIT, Tools.FEEDBACK_WRONG_NOINPUT);
		assertEquals(Tools.FEEDBACK_SUBMIT, expected, actual);
	}
	
	
	//测试手机验证
	@Suppress
	public void testAccountCenter_4(){
		
	}
	
	//测试绑定偶玩帐号
	@Test
	public void testAccountCenter_5(){
		String name = "Helle1234";//此处也要更新帐号
		String psw = "1234567";
		//选择一键登录并进入帐号中心的帐号绑定界面
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.clickByText(solo, Tools.LOGIN_SIGN, Tools.MAIN_SIGN);
		Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_FAST_SIGN);
		Tools.clickByText(solo, Tools.ACCOUNT_BIND, Tools.ACCOUNT_BIND_SIGN);
		//输入用户名及密码
		Tools.enterTextInWeb(solo, Tools.WEB_NAME_BOX, name, 0);
		Tools.enterTextInWeb(solo, Tools.WEB_PSW_BOX, psw, 0);
		actual = Tools.clickOnWebByClassName(solo, Tools.NAME_BIND_BTN, Tools.NAME_BIND_SUCCESS);
		assertEquals(Tools.NAME_BIND_BTN, expected, actual);
		actual = Tools.clickByText(solo, Tools.BACK_TO_ACCOUNTCENTER, Tools.ACCOUNT_USER_SIGN);
		assertEquals(Tools.BACK_TO_ACCOUNTCENTER, expected, actual);
	}
	
	//测试修改密码
	@Test
	public void testAccountCenter_6(){
		String test_name = "abcdefkk";
		String psw = "1234567";
		String[] testData = {
				"123456,12345678,输入原密码错误，请输入正确密码",
				"1234567,12345678,success",
				"12345678,1234567890123456,新密码为7到15位数字或字母",
				"12345678,12345,新密码为7到15位数字或字母",
				"12345678,123456789012345,success",
				"123456789012345,1234567,success"				
		};
		int len = testData.length;
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.removeText(solo, Tools.NAME_BOX);
		Tools.enterTextById(solo, Tools.NAME_BOX, test_name, 0);
		Tools.enterTextById(solo, Tools.PSW_BOX, psw, 0);
		Tools.clickById(solo, Tools.LOGIN_BTN, Tools.LOGIN_SUCCESS_SIGN);
		solo.waitForText(Tools.MAIN_SIGN);
		Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_USER_SIGN);
		actual = Tools.clickByText(solo, Tools.ACCOUNT_USER_SIGN, Tools.ACCOUNT_USER_SIGN);
		assertEquals(Tools.ACCOUNT_USER_SIGN, expected, actual);
		for(int i=0;i<len;i++){
			String[] arr = testData[i].split(",");
			Tools.enterTextInWeb(solo, Tools.OLD_PSW, arr[0], 0);
			Tools.enterTextInWeb(solo, Tools.NEW_PSW, arr[1], 0);
			if(arr[2].equals("success")){
				actual = Tools.clickOnWebByClassName(solo, Tools.PSW_CHANGE_SUBMIT, Tools.CHANGE_PSW_SUCCESS);
				
			}else{
				actual = Tools.clickOnWebByClassName(solo, Tools.PSW_CHANGE_SUBMIT, arr[2]);
			}
			assertEquals(Tools.PSW_CHANGE_SUBMIT, expected, actual);
			if(i == len-1){
				actual = Tools.clickByText(solo, Tools.BACK_TO_MAIN, Tools.MAIN_SIGN);
				assertEquals(Tools.BACK_TO_MAIN, expected, actual);
			}else{
				solo.goBack();
				Tools.clickByText(solo, Tools.ACCOUNT_USER_SIGN, Tools.ACCOUNT_USER_SIGN);
			}
		}
		
	}
	
}
