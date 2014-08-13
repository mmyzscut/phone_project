package com.umipay.android.umipaysdkdemo.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.R.bool;
import android.app.Instrumentation;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;
import com.robotium.solo.Solo.Config.ScreenshotFileType;
import com.umipay.android.umipaysdkdemo.MainActivity;

public class RegistrationTest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	private Solo solo;// 声明Solo

	final String TAG = "autotest";
	public RegistrationTest() {
	
		super(MainActivity.class);
	}

	@Before
	public void setUp() throws Exception {
		Config config = new Config();
		config.screenshotFileType = ScreenshotFileType.PNG;
		config.screenshotSavePath = Environment.getExternalStorageDirectory()
				+ "/RobotiumRegistration";
		// Log.d(TAG, "内部存储 "+config.screenshotSavePath);
		solo=new Solo(getInstrumentation(),config);
		getActivity();
	}

	@After
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	@Test
	/**
	 * 纯数字情形：非法（<4）+合法（=4）
	 */
	public void test01PureDigital1() {
		if(!UmipaytestUtils.isTestMode(solo)){
			solo.clickOnToggleButton(UmipaytestUtils.MAINPAGE_TB_TESTCLOSED);
			solo.waitForText(UmipaytestUtils.INIT_SUCCEED);
		}
		boolean isOK = false;
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_REGISTRATION_LOGIN);
		solo.clickOnText(UmipaytestUtils.REGISTRATIONPAGE_TAB_REGISTRATION);
		//<4
		solo.clearEditText(0);
		solo.typeText(0, UmipaytestUtils.getPureDigital(0)); 
		solo.clearEditText(1);
		solo.typeText(1, UmipaytestUtils.REGISTRATIONPAGE_ET_PASSWORD);
		solo.clickOnButton(UmipaytestUtils.REGISTRATIONPAGE_BT_REGISTRATION);
    	UmipaytestUtils.waitTime(solo, UmipaytestUtils.WAIT_NATWORK_COMUNICATION);
		if (solo.waitForText(UmipaytestUtils.ACCOUNT_ILLEGAL))
		{
			solo.takeScreenshot("PureDigital1_01_<4");
		}
		else {
			solo.takeScreenshot("PureDigital1_02");
		}
		//=4
		boolean isAccountExist=true;
		while(isAccountExist){
		solo.clearEditText(0);
		solo.typeText(0, UmipaytestUtils.getPureDigital(3)); 
		solo.clearEditText(1);
		solo.typeText(1, UmipaytestUtils.REGISTRATIONPAGE_ET_PASSWORD);
		solo.clickOnCheckBox(0);
		solo.clickOnButton(UmipaytestUtils.REGISTRATIONPAGE_BT_REGISTRATION);//有可能有网络延迟
		isAccountExist=solo.waitForText(UmipaytestUtils.ACCOUNT_EXIST);
		}
		if (UmipaytestUtils.isMainPage(solo)) {
			solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
			isOK = UmipaytestUtils.isAccountPage(solo);
			solo.takeScreenshot("PureDigita1l_02");
		}
		Log.i (TAG, "PureDigital_1" + isOK);
	}
	@Test
	/**
	 * 纯数字情形：非法（>32）+合法（=32）
	 */
	public void test02PureDigital2() {
		if(!UmipaytestUtils.isTestMode(solo)){
			solo.clickOnToggleButton(UmipaytestUtils.MAINPAGE_TB_TESTCLOSED);
			solo.waitForText(UmipaytestUtils.INIT_SUCCEED);
		}
		boolean isOK = false;
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_REGISTRATION_LOGIN);
		solo.clickOnText(UmipaytestUtils.REGISTRATIONPAGE_TAB_REGISTRATION);
		//>32
		solo.clearEditText(0);
		solo.typeText(0, UmipaytestUtils.getPureDigital(2)); 
		solo.clearEditText(1);
		solo.typeText(1, UmipaytestUtils.REGISTRATIONPAGE_ET_PASSWORD);
		solo.clickOnButton(UmipaytestUtils.REGISTRATIONPAGE_BT_REGISTRATION);
		UmipaytestUtils.waitTime(solo, UmipaytestUtils.WAIT_NATWORK_COMUNICATION);
		if (solo.waitForText(UmipaytestUtils.ACCOUNT_EXIST)||solo.waitForText(UmipaytestUtils.ACCOUNT_ILLEGAL))
		{
			solo.takeScreenshot("PureDigital2_01_>32");
		}
		else {
			solo.takeScreenshot("PureDigital2_02");
		}
		//=32
		boolean isAccountExist=true;
		while(isAccountExist){
		solo.clearEditText(0);
		solo.typeText(0, UmipaytestUtils.getPureDigital(4)); 
		solo.clearEditText(1);
		solo.typeText(1, UmipaytestUtils.REGISTRATIONPAGE_ET_PASSWORD);
		solo.clickOnCheckBox(0);
		solo.clickOnButton(UmipaytestUtils.REGISTRATIONPAGE_BT_REGISTRATION);
		isAccountExist=solo.waitForText(UmipaytestUtils.ACCOUNT_EXIST);
		}
		//solo.waitForText(UmipaytestUtils.WAIT_NATWORK_COMUNICATION); //网络通讯延迟和账户已存在的先后顺序
		if (UmipaytestUtils.isMainPage(solo)) {
			solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
			isOK = UmipaytestUtils.isAccountPage(solo);
			solo.takeScreenshot("PureDigital2_03");
		}
		Log.i (TAG, "PureDigital2_" + isOK);
	}
	/**
	 * 下拉列表+已注册的帐号？
	 */

}
