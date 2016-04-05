package com.mychat.mybatisTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mychat.bean.UserBean;

public class Main {
	public static void main(String[] args) {
		 ApplicationContext ac = new FileSystemXmlApplicationContext(new String[]{"/WebContent/WEB-INF/spring-context.xml"});
		 //��Spring�����и���bean��idȡ������Ҫʹ�õ�userService����
		 UserManager userManager = (UserManager) ac.getBean("userManager");
		
		UserBean user = userManager.getUserById("13");
		System.out.println(user.getName());
	}
}
