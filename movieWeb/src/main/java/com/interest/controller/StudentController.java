package com.interest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.interest.bean.Student;
import com.interest.bean.User;


@Controller
public class StudentController {
	
	List<Student> list = new ArrayList<Student>();
	
	public StudentController(){//无参数的构造方法会默认被调用一次  
        System.out.println("初始化....");  
        Student s1 = new Student();
		s1.setAge(25);
		s1.setId(1);
		s1.setName("jacky");
		Student s2 = new Student();
		s2.setAge(28);
		s2.setId(2);
		s2.setName("vivian");
		list.add(s1);
		list.add(s2);
		System.out.println(list);
    }  
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public ModelAndView student() {
		return new ModelAndView("student", "command", new Student());
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute("SpringWeb") Student student, ModelMap model) {
		model.addAttribute("name", student.getName());
		model.addAttribute("age", student.getAge());
		model.addAttribute("id", student.getId());
		return "result";
	}
	
	@RequestMapping(value="/studentList")
	public void listStudent(Model model){
		
		model.addAttribute("studentlist", list);
		System.out.println("/studentList");
		
	}
	
	
	@RequestMapping(value="/delStudent",method=RequestMethod.GET)  
	public String deleteStudent(@ModelAttribute("student")Student student)throws Exception { 
		
		System.out.println("excute delStudent");
		if(student != null && student.getId() != null){
			for(Student s : list){
				
				if(s.getId() == student.getId())
					list.remove(s);
				
			}
		}
		System.out.println(list);
		return "redirect:/studentList"; 
	}
	

}
