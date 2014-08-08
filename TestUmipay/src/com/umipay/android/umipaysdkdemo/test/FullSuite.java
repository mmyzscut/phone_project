package com.umipay.android.umipaysdkdemo.test;

import junit.framework.TestSuite;
import junit.framework.Test;

//合并运行所有测试用例
public class FullSuite extends TestSuite {
	public static Test suite(){
		TestSuite suite = new TestSuite("Umipay Tests");
		suite.addTestSuite(N00_TestMain.class);
		suite.addTestSuite(N01_TestRegister.class);
		suite.addTestSuite(N02_TestLogin.class);
		suite.addTestSuite(N03_TestAccountCenter.class);
		return suite;
	}
}
