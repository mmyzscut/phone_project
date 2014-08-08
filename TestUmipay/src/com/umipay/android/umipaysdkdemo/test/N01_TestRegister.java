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

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;

import com.robotium.solo.Solo;
import com.umipay.android.umipaysdkdemo.MainActivity;

public class N01_TestRegister extends ActivityInstrumentationTestCase2<MainActivity>{
	private Solo solo;
	private String[] testData = {
			//"`。？ aj0%,123456,false,false,连接失败，错误码262",
			//"adfHOHsde,123456,false,false,帐号注册成功",
			//"ADFHOHSDE,123456,false,false,帐号名已存在",
			//"abcdefgs,123456,false,false,帐号注册成功",
			//"ANDHKJIH,123456,false,false,帐号注册成功",
			//"12393710,123456,false,fasle,帐号注册成功",
			//"1120123@qq.com,123456,false,false,帐号注册成功",
			//"asjdhflhhjhj,123456,false,false,帐号注册成功",
			//"sdfjkhaskljd,1234567890123456789012,false,false,帐号注册成功",
			//"sdfjhkjahf,12345678,false,false,帐号注册成功",
			//"dhjkshfjksdh,12345,false,false,请输入6~32位长度的密码",
			//实验证明密码没有长度的限制："asdjfhajhfjds,12345678901234567890123,false,false,请输入6~32位长度的密码",
			"sdhfjkha,1234567,123456789012,false,请输入11位手机号码或留空",
			"lsdfkkljsda,1234567,12345678901,false,请输入短信验证码",
			"sdhfjkha,1234567,123456789,false,请输入11位手机号码或留空",
			"ajkdjfldh,1234567,12345678901,true,帐号注册成功"};
	
	public N01_TestRegister(){
		super(MainActivity.class);
	}
	
	@Before
	public void setUp()throws Exception{
		Log.v("N01_TestRegister", "执行setUp()");
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@After
	public void tearDown()throws Exception{
		Log.v("N01_TestRegister", "执行tearDown()");
		solo.finishOpenedActivities();
	}
	
	
	//测试点击注册界面的偶玩服务条款连接
	@Suppress
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
