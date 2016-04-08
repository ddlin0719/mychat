package com.mychat.web.fore;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mychat.bean.MessageBean;
import com.mychat.bean.UserBean;
import com.mychat.common.manager.MessageManager;
import com.mychat.common.manager.UserManager;
import com.mychat.common.util.Constants;
import com.mychat.common.util.HttpHelper;
import com.mychat.common.util.MsgQueueHelper;
import com.mychat.common.util.AppContextHelper;

@Controller
public class SearchPanelController extends BaseController{

	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	private MessageManager msgManager = (MessageManager) AppContextHelper.getInstance().getBean(MessageManager.class);
	
	@RequestMapping(value="search/search.do",method=RequestMethod.POST)
	public void search(HttpServletRequest request,HttpServletResponse response){
		JSONObject returnJson = new JSONObject();
		String _username= request.getParameter("username");
		String page= request.getParameter("page");
		//System.out.println("search param:"+_username+" | "+page);
		
		List<UserBean> userList = userManager.searchFriendByName(_username,page);
		
		//返回ajax结果
		returnJson.put("status", "1");
		returnJson.put("data", JSONArray.toJSON(userList));
		packetAjaxResult(response,returnJson);
		
	}
	

	@RequestMapping(value="search/addFriend.do",method=RequestMethod.POST)
	public void addFriend(HttpServletRequest request,HttpServletResponse response) throws JMSException{
		JSONObject returnJson = new JSONObject();
		String toUserId = request.getParameter("userid");
		String fromUserId = HttpHelper.getSessionUserid(request);
		
		//打包message
		UserBean user=userManager.getUserById(fromUserId);
		MessageBean message = new MessageBean();
		message.setData(user.getName()+" 请求添加您为好友");
		message.setFromUserId(fromUserId);
		message.setToUserId(toUserId);
		message.setType(Constants.ADD_FRIEND_MESSAGE_TYPE);
		
		MsgQueueHelper.sendMessage(message);
		msgManager.saveMessage(message);
		
		//返回ajax结果
		returnJson.put("status", "1");
		packetAjaxResult(response,returnJson);
	}
}
