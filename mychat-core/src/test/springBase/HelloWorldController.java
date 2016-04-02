package springBase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloWorldController {

//	@RequestMapping("/hello.do")
//	public String execute(Model model) {
//		model.addAttribute("name", "�־�");
//		return "test/hello.jsp";
//	}
	
	@RequestMapping("/hello.html")
	public String executeVm(Model model) {
		model.addAttribute("name", "�־�");
		
		List<String> list= new ArrayList<>();
		
		list.add("�ַ��� 1");
		list.add("�ַ��� 2");
		list.add("�ַ��� 3");
		list.add("�ַ��� 4");
		model.addAttribute("list", list);
		
		return "test/hello.vm";
	}
}

