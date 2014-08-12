package com.umipay.android.umipaysdkdemo.test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.umipay.android.view.UmipayActivity;
import android.R.bool;
import android.R.integer;
import android.R.string;
import android.content.res.Configuration;
import android.graphics.AvoidXfermode.Mode;
import android.os.Environment;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.umipay.android.umipaysdkdemo.MainActivity;
import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;
import com.robotium.solo.Solo.Config.ScreenshotFileType;
import com.robotium.solo.WebElement;

public class SDKTest extends ActivityInstrumentationTestCase2 {
	private Solo solo;// 声明Solo

	final String TAG = "调试输出";

	public SDKTest()// 构造方法
	{
		super(MainActivity.class);

	}

	@Before
	public void setUp() throws Exception {
		Config config = new Config();
		config.screenshotFileType = ScreenshotFileType.PNG;
		config.screenshotSavePath = Environment.getExternalStorageDirectory()
				+ "/Robotium/";
		// config.timeout_large=300000;//五分钟
		// Log.d(TAG, "内部存储 "+config.screenshotSavePath);
		solo = new Solo(getInstrumentation(), config);
		getActivity();
		// solo = new Solo(getInstrumentation(), getActivity());
	}

	@After
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	@Test
	public void test01Regisrtation() throws Exception {

		boolean isOK=false;
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_REGISTRATION_LOGIN);
		solo.clickOnText(UmipaytestUtils.REGISTRATIONPAGE_TAB_REGISTRATION);
		solo.clearEditText(0);
		solo.typeText(0, UmipaytestUtils.REGISTRATIONPAGE_ET_NAME);
		solo.clearEditText(1);
		solo.typeText(1, UmipaytestUtils.REGISTRATIONPAGE_ET_PASSWORD);
		solo.clickOnText(UmipaytestUtils.REGISTRATIONPAGE_LINK_OUWANSERVER);
		assertTrue(solo.waitForText(UmipaytestUtils.OUWANSERVERPAGE_CONTENT));
		solo.goBack(); //solo.clickOnImage(0);
		solo.clickOnCheckBox(0);
		solo.clickOnButton(UmipaytestUtils.REGISTRATIONPAGE_BT_REGISTRATION);
		// assertEquals(false, solo.searchText("请输入6～32位长度的密码"));
		/*
		 * solo.sleep(120000); activityname=getActivity().getClass().getName();
		 * Log.d(TAG,activityname );
		 */
		solo.waitForText(UmipaytestUtils.WAIT_NATWORK_COMUNICATION);
		if(isMainPage()){
			solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
			isOK=isAccountPage();
			solo.takeScreenshot("Registratuion_01");
		}
		Log.i(TAG, "Registratuion_结果"+isOK);
	}

	/*
	 * 登录测试
	 */
	@Test
	public void test02Login() {

		boolean isOK = false;

		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_REGISTRATION_LOGIN);
		solo.takeScreenshot("Login_1_主界面");
		solo.clearEditText(0);
		solo.enterText(0, "hello01234");
		solo.clearEditText(1);
		solo.enterText(1, "123456");
		assertEquals("选中记住密码的复选框！", true, !solo.isCheckBoxChecked(0)); 
		solo.clickOnButton(UmipaytestUtils.LOGINPAGE_BT_LOGIN);

		long time = waitTime(UmipaytestUtils.WAIT_NATWORK_COMUNICATION);
		Log.d(TAG, "Login_登录所用时间_" + time);
		if (isBindphonePage()) {
			solo.clickOnText(UmipaytestUtils.BINDPHONEPAGE_BT_IGNORE);
		}
		solo.waitForText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		time = waitTime(UmipaytestUtils.WAIT_LOADING);
		// 通过点击账户中心无须登录验证已经成功登录
		if (isAccountPage()) {
			Log.d(TAG, "Login_进入账户中心所用时间_" + time);
			solo.takeScreenshot("Login_2_账户中心");
			isOK = true;
		} else {
			solo.sleep(2000);
			solo.takeScreenshot("Login_3_网络阻塞");

		}
		Log.i(TAG, "Login_" + isOK);
	}

	// 网络通讯不好的情况下

	/*
	 * 账户中心(订单记录）测试
	 */
	@Test
	public void test03RecordInAccount() {
		boolean isOK = false;
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		solo.clickOnButton(UmipaytestUtils.LOGINPAGE_BT_LOGIN);

		if (isBindphonePage()) {
			solo.clickOnText(UmipaytestUtils.BINDPHONEPAGE_BT_IGNORE);
		}
		long time = waitTime(UmipaytestUtils.WAIT_NATWORK_COMUNICATION);
		Log.d(TAG, "RecordInAccount_登录所用时间_" + time);
		if (isBindphonePage()) {
			solo.clickOnText(UmipaytestUtils.BINDPHONEPAGE_BT_IGNORE);
		}
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		time = waitTime(UmipaytestUtils.WAIT_LOADING);
		if (isAccountPage()) {
			Log.d(TAG, "RecordInAccount_进入账户中心所用时间_" + time);
			solo.clickOnText(UmipaytestUtils.ACCOUNTPAGE_WEB_RECORD);
			solo.waitForWebElement(By.textContent(UmipaytestUtils.RECORDPAGE_WEB__RETURN));
			// time = waitTime("返回账户中心");
			if (solo.waitForText(UmipaytestUtils.REQUEST_ERROR)
					|| solo.waitForText(UmipaytestUtils.GET_RECORD_FIALED)) {

				Log.i(TAG, "获取用户订单失败   或者  请求错误，请检查网络设置");

			} else {
				Log.d(TAG, "RecordInAccount_进入订单记录所用时间" + time);

				solo.takeScreenshot("RecordInAccount_1_订单记录");
				solo.clickOnText(UmipaytestUtils.RECORDPAGE_WEB__RETURN);
				assertTrue("点击返回账户中心按钮无法返回账户中心", isAccountPage());
				isOK = true;
			}
		}
		// 网络通讯不好的情况下
		Log.i(TAG, "RecordInAccount_结果_" + isOK);
	}

	/*
	 * 账户中心（意见反馈）
	 */

	@Test
	public void test04FeedbackInAccount() {

		boolean isOk = false;
		String feedback_content=UmipaytestUtils.FEEDBACK_CONTENT;
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		solo.clickOnButton(UmipaytestUtils.LOGINPAGE_BT_LOGIN);

		long time = waitTime(UmipaytestUtils.WAIT_NATWORK_COMUNICATION);
		Log.d(TAG, "FeedbackInAccount_登录所用时间" + time);
		if (isBindphonePage()) {
			solo.clickOnText(UmipaytestUtils.BINDPHONEPAGE_BT_IGNORE);
		}

		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		time = waitTime(UmipaytestUtils.WAIT_LOADING);
		if (isAccountPage()) {
			Log.d(TAG, "FeedbackInAccount_1_进入账户中心所用时间" + time);
			solo.clickOnText(UmipaytestUtils.ACCOUNTPAGE_WEB_FEEDBACK);
			solo.waitForWebElement(By.textContent(UmipaytestUtils.FEEDBACKPAGE_WEB_INSERT));
			solo.takeScreenshot("FeedbackInAccount_2_意见反馈列表"); // 有已经发表过的反馈意见

			solo.clickOnText(UmipaytestUtils.FEEDBACKPAGE_WEB_INSERT);
			waitTime(UmipaytestUtils.WAIT_LOADING);// solo.waitForWebElement(By.textContent("提 交"));
									// /****is right*****/
			Log.d(TAG, "进入添加意见反馈界面所需时间" + time);

			solo.takeScreenshot("FeedbackInAccount_3_添加意见反馈");
			assertTrue("点击添加意见反馈按钮进入意见反馈页面", isFeedbackPage());
			solo.clickOnText(UmipaytestUtils.FEEDBACKPAGE_WEB_SUBMIT);

			// 反馈内容输入框空输入，提示“请填写反馈内容”
			if (solo.waitForText(UmipaytestUtils.FEEDBACK_CONTENT_INPUT_ERROR)) {
				// WebElement
				// webElement=solo.getWebElement(By.textContent("请填写反馈内容"),0);
				// webElement.setTextContent("你好");
//				solo.enterTextInWebElement(By.textContent("请填写反馈内容"),feedback_content);
//				solo.sleep(2000);
				solo.typeTextInWebElement(By.id("content"), feedback_content);
				solo.takeScreenshot("FeedbackInAccount_4_填写反馈内容");
			}
			solo.clickOnText(UmipaytestUtils.FEEDBACKPAGE_WEB_SUBMIT);
			// 不选择反馈类型，提示“请选择反馈类型”
			if (solo.waitForText(UmipaytestUtils.FEEDBACK_TYPE_ERROR)) {
				// WebElement
				// webElement=solo.getWebElement(By.textContent("充值问题"), 0);
				solo.clickOnText(UmipaytestUtils.FEEDBACK_TYPE_CHARGE);
				solo.sleep(2000);
				solo.takeScreenshot("FeedbackInAccount_5_选择反馈类型");
			}
			solo.clickOnText(UmipaytestUtils.FEEDBACKPAGE_WEB_SUBMIT);
			// 正确输入，提交能正确显示在意见反馈列表
			if (isFeedbackPage()) {
				solo.searchText(feedback_content);
				isOk = true;
				solo.takeScreenshot("FeedbackInAccount_6_添加后的意见反馈列表");
			}
		}
		Log.i(TAG, "FeedbackInAccount_结果_" + isOk);
	}

	/*
	 * 账户中心（绑定邮箱）
	 */

	@Test
	public void test05BindemailInAccount() {
		boolean isOK = false;
		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		solo.clickOnButton(UmipaytestUtils.LOGINPAGE_BT_LOGIN);

		long time = waitTime(UmipaytestUtils.WAIT_NATWORK_COMUNICATION);;
		Log.d(TAG, "BindemailIn_登录所用时间_" + time);
		if (isBindphonePage()) {
			solo.clickOnText(UmipaytestUtils.BINDPHONEPAGE_BT_IGNORE);
		}

		solo.clickOnText(UmipaytestUtils.MAINPAGE_BT_ACCOUNT);
		time = waitTime(UmipaytestUtils.WAIT_LOADING);

		if (isAccountPage()) {
			Log.d(TAG, "RecordInAccount_进入账户中心所用时间_" + time);
			solo.clickOnText(UmipaytestUtils.ACCOUNTPAGE_WEB_BINDEMAIL);
			solo.waitForWebElement(By.textContent(UmipaytestUtils.ACCOUNTPAGE_WEB_BINDEMAIL));

			solo.takeScreenshot("BindemailInAccount_1_绑定邮箱");
			solo.clickOnWebElement(By
					.className("abs db ml10 fs20 c2 icon-arrow-left"));
			/*******************
			 * solo.clickOnImage(0);
			 * **********会点击了右上角的关闭iamage，因为左上角的回到上一级的图标，不是android的原控件
			 *********/
			solo.takeScreenshot("BindemailInAccount_2_绑定邮箱，回到上一级，进入账户中心");
			assertTrue("点击顶部左侧返回图标无法返回账户中心", isAccountPage());
			isOK = true;
		}

		Log.i(TAG, "BindemailInAccount_结果_" + isOK);
	}
	
	/*
	 * 是否是主界面
	 */
	public boolean isMainPage(){
		boolean result =solo.waitForText("游戏支付平台");
		return result;
	}

	/*
	 * 是否是绑定手机的界面（登录的时候，未绑定手机）
	 */
	public boolean isBindphonePage() {
		boolean result = solo.waitForText("跳过此步");
		return result;
	}

	/*
	 * 是否是账户中心的界面
	 */

	public boolean isAccountPage() {
		boolean result = solo.waitForText("订单记录") && solo.waitForText("问题反馈")
				&& solo.waitForText("修改密码");
		return result;
	}

	/*
	 * 是否是意见反馈界面
	 */
	public boolean isFeedbackPage() {
		boolean result = solo.waitForText("充值问题") & solo.waitForText("提 交");
		return result;
	}

	/*
	 * 加载的时候，等待的时间
	 */

	private long waitTime(String text) {
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
