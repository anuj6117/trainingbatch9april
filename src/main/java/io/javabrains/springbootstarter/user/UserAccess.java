package io.javabrains.springbootstarter.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccess {
	
	
	@GetMapping("/hello")
	public String hi()
	{
	return "hello";	
	}
	
	
	List<User> list = new ArrayList<User>();
	static User obj;
	@RequestMapping (value = "/submit", method = RequestMethod.GET)
	public void setUser (@RequestParam("name") String name, @RequestParam("rollNumber")int rollNumber) {
		list.add(new User(name, rollNumber));
		System.out.println(list);
   
	}
	@RequestMapping("/show")
	public List<User> getData(){
		if(!list.isEmpty()) {
			return list;
		}
			else
				return null;
			
		}
	@RequestMapping("/delete")
	public void deleteListData(@RequestParam("rollNumber")int rollNumber) {
		if(!list.isEmpty()) {
			//list.remove(0);
			Iterator<User> itr=list.iterator();
			while(itr.hasNext()) {
				obj=itr.next();
				if(obj.getRollNumber()==rollNumber) {
					itr.remove();
					System.out.println("Delete successfully");
				}
			}
		}
	}
	@RequestMapping("update")
	public void updateList(@RequestParam("name") String name, @RequestParam("rollNumber")int rollNumber) {
		obj=new User(name,rollNumber);
		System.out.println("update  "+obj);
		
		if(!list.isEmpty()) {
		Iterator<User> itr=list.iterator();
		while(itr.hasNext()) {
			obj=itr.next();
			if(obj.getRollNumber()==rollNumber) {
				obj.setName(name);
				System.out.println("Update Successfully");
			}
		}
			
			
		}
		
		
	}
	}

