package com.umipay.android.umipaysdkdemo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;

import com.robotium.solo.Solo;
import com.umipay.android.umipaysdkdemo.MainActivity;

public class N00_TestMain extends ActivityInstrumentationTestCase2<MainActivity>{
	private Solo solo;
	private HTMLLayout layout;
	private WriterAppender appender = null;
	
	public N00_TestMain(){		
		super(MainActivity.class);
	}
	
	@Before
	public void setUp() throws Exception{
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@After
	public void tearDown() throws Exception{
		solo.finishOpenedActivities();
		Activity current = getActivity();
		if(current != null)
			current.finish();
		super.tearDown();
	}
	
	
	//测试未登录时主界面各个按钮点击情况
	@Test
	public void testMain_1(){
		boolean expected = true;
		//测试点击“注册登录”按钮
		boolean actual = Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		assertEquals(Tools.LOGIN_OR_REGISTER_BTN, expected, actual);
		
		//测试切换登录和注册窗口
		actual = Tools.clickById(solo, Tools.REGISTER_VIEW, Tools.REGISTER_NAME_INPUT);
		assertEquals(Tools.REGISTER_VIEW, expected, actual);
		actual = Tools.clickById(solo, Tools.LOGIN_VIEW, Tools.LOGIN_SIGN);
		assertEquals(Tools.LOGIN_VIEW, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.MAIN_SIGN);
		
		//测试未登录时点击主界面各按钮
		actual = Tools.clickById(solo, Tools.RATE_PAY, Tools.LOGIN_SIGN);
		assertEquals(Tools.RATE_PAY, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.MAIN_SIGN);
		
		actual = Tools.clickById(solo, Tools.QUOTA_PAY, Tools.LOGIN_SIGN);
		assertEquals(Tools.QUOTA_PAY, expected, actual);
		solo.goBack();
		solo.waitForText(Tools.MAIN_SIGN);		

		actual = Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.LOGIN_SIGN);
		assertEquals(Tools.ACCOUNT_CENTER, expected, actual);
		solo.goBack();
		solo.sleep(2000);		
	}
	
	
	//测试登录后各个按钮点击情况
	@Test
	public void testMain_2(){
		//logger.info("Second Case");
		boolean expected = true;
		boolean actual = false;
		String input_name = "1120123962@qq.com";//正确帐号
		String input_psw = "liminmin217";//正确密码
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.removeText(solo, Tools.NAME_BOX);
		Tools.enterTextById(solo, Tools.NAME_BOX, input_name, 1000);
		Tools.enterTextById(solo, Tools.PSW_BOX, input_psw, 1000);
		actual = Tools.clickById(solo, Tools.LOGIN_BTN, Tools.LOGIN_SUCCESS_SIGN);
		assertEquals(Tools.LOGIN_BTN,expected,actual);
		solo.sleep(2000);
		
		actual = Tools.clickById(solo, Tools.RATE_PAY, Tools.RATE_PAY_SIGN);
		assertEquals(Tools.RATE_PAY,expected,actual);
		solo.goBack();
		
		actual = Tools.clickById(solo, Tools.QUOTA_PAY, Tools.QUOTA_PAY_SIGN);
		assertEquals(Tools.QUOTA_PAY,expected,actual);
		solo.goBack();
		solo.waitForText(Tools.MAIN_SIGN);
		
		actual = Tools.clickById(solo, Tools.ACCOUNT_CENTER, Tools.ACCOUNT_USER_SIGN);
		assertEquals(Tools.ACCOUNT_CENTER,expected,actual);
		solo.goBack();
		solo.sleep(2000);
	}
	
}
