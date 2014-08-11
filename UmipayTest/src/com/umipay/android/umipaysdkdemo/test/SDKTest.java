package com.umipay.android.umipaysdkdemo.test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.umipay.android.view.UmipayActivity;
import android.R.bool;
import android.R.integer;
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

	private String name = "hello2";
	private String pass = "123456";
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
		solo.clickOnText("注册登录");

//		String activityname = getActivity().getClass().getName();
//		Log.d(TAG, activityname);
		solo.clickOnText("注册帐号");
		solo.clearEditText(0);
		solo.enterText(0, name);
		solo.clearEditText(1);
		solo.enterText(1, pass);
		solo.clickOnText("偶玩服务条款");
		assertTrue(solo.waitForText("偶玩游戏平台用户协议"));
		solo.goBack(); //solo.clickOnImage(0);
		//solo.clickOnCheckBox(0);
		solo.clickOnButton("立即注册");
		// assertEquals(false, solo.searchText("请输入6～32位长度的密码"));
		/*
		 * solo.sleep(120000); activityname=getActivity().getClass().getName();
		 * Log.d(TAG,activityname );
		 */
		solo.waitForText("网络正在通讯中，请稍等");
		if(isMainPage()){
			solo.clickOnText("账户中心");
			isOK=isAccountPage();
			solo.takeScreenshot("Registratuion_01");
		}
		Log.i(TAG, "Registratuion"+isOK);
	}

	/*
	 * 登录测试
	 */
	@Test
	public void test02Login() {

		boolean isOK = false;

		solo.clickOnText("注册登录");
		solo.sleep(2000);
		String activityname = getActivity().getClass().getName();
		Log.d(TAG, "login_1  " + activityname);
		solo.takeScreenshot("Login_1_主界面");
		solo.clearEditText(0);
		solo.enterText(0, "hello01234");
		solo.clearEditText(1);
		solo.enterText(1, "123456");
		assertEquals("选中记住密码的复选框！", true, !solo.isCheckBoxChecked(0));
		solo.clickOnButton("登录");
		// activityname = getActivity().getClass().getName();
		// Log.d(TAG, "login_2  " + activityname);

		long time = waitTime("网络正在通讯中，请稍等");
		Log.d(TAG, "Login_登录所用时间_" + time);
		if (isBindphonePage()) {
			solo.clickOnText("跳过此步");
		}
		solo.waitForText("账户中心");
		solo.clickOnText("账户中心");
		time = waitTime("正在努力加载中，请稍等");
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
		solo.clickOnText("账户中心");
		solo.clickOnButton("登录");

		if (isBindphonePage()) {
			solo.clickOnText("跳过此步");
		}

		long time = waitTime("网络正在通讯中，请稍等");
		Log.d(TAG, "RecordInAccount_登录所用时间_" + time);
		if (isBindphonePage()) {
			solo.clickOnText("跳过此步");
		}

		solo.clickOnText("账户中心");
		time = waitTime("正在努力加载中，请稍等");

		if (isAccountPage()) {
			Log.d(TAG, "RecordInAccount_进入账户中心所用时间_" + time);
			solo.clickOnText("订单记录");
			solo.waitForWebElement(By.textContent("返回账户中心"));
			// time = waitTime("返回账户中心");
			if (solo.waitForText("请求错误，请检查网络设置")
					|| solo.waitForText("获取用户订单失败")) {
				/***************** 用searchfor？ *************/

				Log.i(TAG, "获取用户订单失败   或者  请求错误，请检查网络设置");

			} else {
				Log.d(TAG, "RecordInAccount_进入订单记录所用时间" + time);
				getWebs("订单记录");
				solo.takeScreenshot("RecordInAccount_1_订单记录");
				solo.clickOnText("返回账户中心");
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
		// 填入的反馈内容
		String feefbackcontent = "你好";

		solo.clickOnText("账户中心");
		solo.clickOnButton("登录");

		long time = waitTime("网络正在通讯中，请稍等");
		Log.d(TAG, "FeedbackInAccount_登录所用时间" + time);
		if (isBindphonePage()) {
			solo.clickOnText("跳过此步");
		}

		solo.clickOnText("账户中心");
		time = waitTime("正在努力加载中，请稍等");
		if (isAccountPage()) {
			Log.d(TAG, "FeedbackInAccount_1_进入账户中心所用时间" + time);
			solo.clickOnText("问题反馈");
			solo.waitForWebElement(By.textContent("添加反馈"));
			solo.takeScreenshot("FeedbackInAccount_2_意见反馈列表"); // 有已经发表过的反馈意见
			getWebs("意见反馈列表");
			solo.clickOnText("添加反馈");
			waitTime("正在努力加载中，请稍等");// solo.waitForWebElement(By.textContent("提 交"));
									// /****is right*****/
			Log.d(TAG, "进入添加意见反馈界面所需时间" + time);
			getWebs("添加意见反馈");
			solo.takeScreenshot("FeedbackInAccount_3_添加意见反馈");
			assertTrue("点击添加意见反馈按钮进入意见反馈页面", isFeedbackPage());
			solo.clickOnText("提交");

			// 反馈内容输入框空输入，提示“请填写反馈内容”
			if (solo.waitForText("请填写反馈内容")) {
				// WebElement
				// webElement=solo.getWebElement(By.textContent("请填写反馈内容"),0);
				// webElement.setTextContent("你好");
				solo.enterTextInWebElement(By.textContent("请填写反馈内容"),
						feefbackcontent);
				solo.sleep(2000);
				solo.takeScreenshot("FeedbackInAccount_4_填写反馈内容");
			}
			solo.clickOnText("提 交");
			// 不选择反馈类型，提示“请选择反馈类型”
			if (solo.waitForText("请选择反馈类型")) {
				// WebElement
				// webElement=solo.getWebElement(By.textContent("充值问题"), 0);
				solo.clickOnText("充值问题");
				solo.sleep(2000);
				solo.takeScreenshot("FeedbackInAccount_5_选择反馈类型");
			}
			solo.clickOnText("提 交");
			// 正确输入，提交能正确显示在意见反馈列表
			if (isFeedbackPage()) {
				solo.searchText(feefbackcontent);
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
		solo.clickOnText("账户中心");
		solo.clickOnButton("登录");

		long time = waitTime("网络正在通讯中，请稍等");
		Log.d(TAG, "BindemailIn_登录所用时间_" + time);
		if (isBindphonePage()) {
			solo.clickOnText("跳过此步");
		}

		solo.clickOnText("账户中心");
		time = waitTime("正在努力加载中，请稍等");

		if (isAccountPage()) {
			Log.d(TAG, "RecordInAccount_进入账户中心所用时间_" + time);
			solo.clickOnText("绑定邮箱");
			solo.waitForWebElement(By.textContent("邮箱绑定"));
			getWebs("绑定邮箱");
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

	/*
	 * 获取web的元素
	 */
	private void getWebs(String text) {
		String tag = "elements";
		ArrayList<WebElement> aList = solo.getWebElements();
		Log.v(tag, "start " + text);
		for (int i = 0; i < aList.size(); i++) {
			Log.d(tag, "第" + i + "个元素  ClassName()="
					+ aList.get(i).getClassName() + "   Id()="
					+ aList.get(i).getId() + "   name()="
					+ aList.get(i).getName() + "   tagName()="
					+ aList.get(i).getTagName() + "   Text()"
					+ aList.get(i).getText());

			// Log.d(tag, "第" + i + "个元素 "+ aList.get(i).toString());

		}
		Log.v(tag, "end " + text);
	}

}
