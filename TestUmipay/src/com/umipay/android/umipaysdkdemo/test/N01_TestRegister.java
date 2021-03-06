package com.umipay.android.umipaysdkdemo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.robotium.solo.Solo;
import com.umipay.android.umipaysdkdemo.MainActivity;

public class N01_TestRegister extends ActivityInstrumentationTestCase2<MainActivity>{
	private Solo solo;
	
	private String[] testData = {
			"`。？ aj0%,1234567,false,false,请输入有效的偶玩通行证帐号",
			Tools.getRandomName(9)+",1234567,false,false,帐号注册成功",
			Tools.getRandomName(6).toUpperCase()+Tools.getRandomName(3)+ ",1234567,false,false,帐号注册成功",
			Tools.getRandomNameFromNum(9) + ",1234567,false,fasle,帐号注册成功",
			Tools.getRandomNameFromNum(9)+"@qq.com,1234567,false,false,帐号注册成功",
			Tools.getRandomName(9) + ",123456,false,false,帐号注册成功",
			Tools.getRandomName(9) + ",1234567890123456789012,false,false,帐号注册成功",
			Tools.getRandomName(9) + ",12345,false,false,请输入6~32位长度的密码",
			//实验证明密码没有长度的限制："asdjfhajhfjds,12345678901234567890123,false,false,请输入6~32位长度的密码",
			"sdhfjkha,1234567,123456789012,false,请输入11位手机号码或留空",
			"lsdfkkljsda,1234567,12345678901,false,请输入短信验证码",
			"sdhfjkha,1234567,123456789,false,请输入11位手机号码或留空",
			Tools.getRandomName(10)+",1234567,12345678901,true,帐号注册成功"};
	
	public N01_TestRegister(){
		super(MainActivity.class);
	}
	
	@Before
	public void setUp()throws Exception{
		Log.v("N01_TestRegister", "setUp()");
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@After
	public void tearDown()throws Exception{
		Log.v("N01_TestRegister", "tearDown()");
		solo.finishOpenedActivities();
	}
	
	
	//测试点击注册界面的偶玩服务条款连接
	@Test
	public void testRegister_1(){
		Log.v("testRegister_1", "第一个Case开始");
		Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);
		Tools.clickById(solo, Tools.REGISTER_VIEW, Tools.REGISTER_SIGN);
		boolean expected = true;
		boolean actual = false;
		actual = Tools.clickByText(solo, Tools.TERMS_OF_SERVICE, Tools.SERVICE_SIGN);
		assertEquals(Tools.TERMS_OF_SERVICE,expected,actual);
		solo.sleep(2000);
	}
	
	//测试各种账号密码输入情况
	@Test
	public void testRegister_2(){
		Log.v("testRegister_2", "第二个Case开始");
		String line;
		boolean expected = true;		
		for(int i=0;i<testData.length;i++){
			boolean actual = false;
			line = testData[i];
			String[] arr = line.split(",");
			Tools.clickById(solo, Tools.LOGIN_OR_REGISTER_BTN, Tools.LOGIN_SIGN);			
			Tools.clickById(solo, Tools.REGISTER_VIEW, Tools.REGISTER_SIGN);			
			Tools.enterTextByText(solo, Tools.REGISTER_NAME_INPUT, arr[0], 0);
			Tools.enterTextByText(solo, Tools.REGISTER_PSW_INPUT, arr[1], 0);
			if(!arr[2].equals("false")){
				Tools.enterTextById(solo, Tools.PHONE_BOX, arr[2], 0);
			}
			if(!arr[3].equals("false")){
				Tools.clickById(solo, Tools.BIND_BOX, null);
			}
			actual = Tools.clickById(solo, Tools.REGISTER_BTN, arr[4]);
			assertEquals(Tools.REGISTER_BTN+":"+testData[i],expected,actual);
			solo.goBack();
			solo.sleep(3000);
		}
	}
}
