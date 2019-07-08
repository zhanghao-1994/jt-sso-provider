package cn.tedu.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.common.vo.SysResult;
import cn.tedu.sso.pojo.User;
import cn.tedu.sso.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/check/{param}/{type}")
	public SysResult check(@PathVariable String param, @PathVariable Integer type) {
		return userService.check(param, type);
	}
	
	@RequestMapping("/register")
	public SysResult register(@RequestBody User user) {
		return userService.register(user);
	}
	
	@RequestMapping("/login")
	public SysResult login(@RequestParam("u") String username, @RequestParam("p") String password) {
		SysResult sysResult = new SysResult();
		try {
			sysResult = userService.login(username, password);
		} catch (Exception e) {
			sysResult.build(201, "登录服务异常！");
		}
		return sysResult;
	}
	
	@RequestMapping("/query/{ticket}")
	public SysResult queryTicket(@PathVariable String ticket) {
		return userService.queryByTicket(ticket);
	}

}
