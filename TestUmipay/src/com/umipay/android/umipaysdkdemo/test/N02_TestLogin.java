package com.umipay.android.umipaysdkdemo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.umipay.android.umipaysdkdemo.MainActivity;

public class N02_TestLogin extends ActivityInstrumentationTestCase2<MainActivity>{

	private Solo solo;
	private String[] testData = {
			"ABCDEFKK,123456789012345,success",
			"abcdefghgg,1234567,success",
			"abcdEFKK,123456789012345,success",
			"abcdefkkk,1234567,帐号名不存在",
			"abcdefkk,12345,密码错误"
	};
	
	public N02_TestLogin(){
		super(MainActivity.class);
	}
	
	@Before
	public void setUp()throws Exception{
		Log.v("N02_TestLogin", "执行setUp()");
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@After
	public void tearDown()throws Exception{
		Log.v("N02_TestLogin", "执行tearDown()");
		solo.finishOpenedActivities();
	}
	
	//测试登录界面的连接是否可达相应界面
	@Test
	public void testLogin_1(){
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		boolean expected = true;
		boolean actual = false;
		
		//测试点击忘记密码链接
		actual = Tools.clickByText(solo, Tools.FORGET_PSW, Tools.FIND_PSW);
		assertEquals(Tools.FORGET_PSW, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.LOGIN_SIGN);
		
		//测试点击QQ授权登录链接
		actual = Tools.clickById(solo, Tools.QQ_OAUTH, Tools.QQ_SIGN);
		assertEquals(Tools.QQ_OAUTH, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.LOGIN_SIGN);
		
		//测试点击新浪授权登录链接
		actual = Tools.clickById(solo, Tools.SINA_OAUTH, Tools.SINA_SIGN);
		assertEquals(Tools.SINA_OAUTH, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.LOGIN_SIGN);
		
		//测试下拉按钮
		actual = Tools.clickById(solo, Tools.SELECT_BTN, Tools.SELECT_SIGN);
		assertEquals(Tools.SELECT_BTN, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.LOGIN_SIGN);
		
	}
	
	//测试正常登录功能
	@Test
	public void testLogin_2(){
		boolean expected = true;
		boolean actual = false;
		int len = testData.length;
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		
		//测试“一键试玩”
		actual = Tools.clickByText(solo, Tools.LOGIN_SIGN, Tools.LOGIN_SUCCESS_SIGN);
		assertEquals( Tools.LOGIN_SUCCESS_SIGN, expected, actual);
		solo.waitForText(Tools.MAIN_SIGN);
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		
		for(int i=0;i<len;i++){
			String[] arr = testData[i].split(",");
			Tools.removeText(solo, Tools.NAME_BOX);
			Tools.enterTextById(solo, Tools.NAME_BOX, arr[0], 1000);
			Tools.enterTextById(solo, Tools.PSW_BOX, arr[1], 1000);
			if(arr[2].equals("success")){
				actual = Tools.clickById(solo, Tools.LOGIN_BTN, Tools.LOGIN_SUCCESS_SIGN);
				assertEquals(Tools.LOGIN_BTN, expected, actual);
				solo.waitForText(Tools.MAIN_SIGN);
				Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
			}else{
				actual = Tools.clickById(solo, Tools.LOGIN_BTN, arr[2]);
				assertEquals(Tools.LOGIN_BTN, expected, actual);
			}
		}
	}
}
