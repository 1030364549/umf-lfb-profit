package com.umf.socket.client;

import java.util.HashMap;
import java.util.Map;

import com.umf.config.InitConfig;
import com.umf.utils.DateUtil;
import com.umf.utils.LogUtil;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 类名:Jpush 描述：todo 作者：luran 时间：2019/7/5 17:31 版本：1.0
 **/
@SuppressWarnings("all")
public class Jpush {

	private DateUtil du = new DateUtil();
	private LogUtil log = new LogUtil();

	// 极光推送>>Android
	// Map<String, String> parm是我自己传过来的参数,同学们可以自定义参数
	public void jpushAndroid(Map<String, String> parm) {
		// 创建JPushClient(极光推送的实例)
		JPushClient jpushClient = new JPushClient(InitConfig.master_secret, InitConfig.app_key);
		// 推送的关键,构造一个payload
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android())// 指定android平台的用户
				.setAudience(Audience.alias(parm.get("agentNum")))// registrationId指定用户
				.setNotification(Notification.android("点击返回应用", parm.get("message"), parm))
				// 发送内容
				.setOptions(Options.newBuilder().setApnsProduction(false).build())
				// 这里是指定开发环境,不用设置也没关系
				.setMessage(Message.content(parm.get("message")))// 自定义信息
				.build();

		try {
			jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			log.errinfoE(e);
		} catch (APIRequestException e) {
			log.errinfoE(e);
		}
	}

	public void jpushIOS(Map<String, String> parm) {
		// 创建JPushClient
		JPushClient jpushClient = new JPushClient(InitConfig.master_secretios, InitConfig.app_keyios);
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios())// ios平台的用户
				.setAudience(Audience.alias(parm.get("agentNum")))// registrationId指定用户
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(parm.get("message")).setBadge(+1)
								.setSound("happy")// 这里是设置提示音(更多可以去官网看看)
								.addExtras(parm).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.setMessage(Message.newBuilder().setMsgContent(parm.get("message")).addExtras(parm).build())// 自定义信息
				.build();
		try {
			jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			log.errinfoE(e);
		} catch (APIRequestException e) {
			log.errinfoE(e);
		}
	}

	public int jpushAll(Map<String, String> parm) {
		// 创建JPushClient
		JPushClient jpushClient = new JPushClient(InitConfig.master_secret, InitConfig.app_key);
		// 创建option
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()) // 所有平台的用户
				.setAudience(Audience.alias(parm.get("agentNum")))// registrationId指定用户
				.setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder() // 发送ios
						.setAlert(parm.get("message")) // 消息体
						.setBadge(+1).setSound("happy") // ios提示音
						.addExtras(parm) // 附加参数
						.build()).addPlatformNotification(AndroidNotification.newBuilder() // 发送android
								.addExtras(parm) // 附加参数
								.setAlert(parm.get("message")) // 消息体
								.build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build())// 指定开发环境 true为生产模式 false 为测试模式
																					// (android不区分模式,ios区分模式)
				.setMessage(Message.newBuilder().setMsgContent(parm.get("message")).addExtras(parm).build())// 自定义信息
				.build();
		try {
			jpushClient.sendPush(payload);
			return 1;
		} catch (APIConnectionException e) {
			log.errinfoE(e);
		} catch (APIRequestException e) {
			log.errinfoE(e);
		}
		return -1;
	}

	public void pAll(String agent_num, String level_tax_profit) throws Exception {
		Map map = new HashMap();
		map.put("agentNum", agent_num);
		map.put("message",
				"尊敬的合作伙伴,于" + du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss") + " 产生分润：" + level_tax_profit + "元。");
		jpushIOS(map);
		jpushAll(map);
	}
}
