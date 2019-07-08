package cn.tedu.sso.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.geom.transform.BaseTransform.Degree;

import cn.tedu.common.vo.SysResult;
import cn.tedu.sso.mapper.UserMapper;
import cn.tedu.sso.pojo.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;
	
	private static final Map<Integer, String> PARAM_TYPE = new HashMap<Integer, String>();
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	static {
		PARAM_TYPE.put(1, "username");
		PARAM_TYPE.put(2, "phone");
		PARAM_TYPE.put(3, "email");
	}
	
	public SysResult check(String param, Integer type) {
		Integer i = userMapper.check(PARAM_TYPE.get(type), param);
		
		if (i == 0) {
			return SysResult.ok("false");
		} else {
			return SysResult.build(201,  "ok",  "true");
		}
	}
	
	public SysResult register(User user) {
		
		Integer a = userMapper.check(PARAM_TYPE.get(1), user.getUsername());
		Integer b = userMapper.check(PARAM_TYPE.get(2), user.getPhone());
		Integer c = userMapper.check(PARAM_TYPE.get(3), user.getEmail());
		
		if (a==0 && b==0 && c==0) {
			String newPwd = DigestUtils.md5Hex(user.getPassword());
			user.setPassword(newPwd);
			userMapper.save(user);
			return SysResult.build(200, "用户注册成功！", user.getUsername());
		}else {
			return SysResult.build(201, "用户注册失败！", user.getUsername());
		}
	}
	
	public SysResult login(String username, String password) throws Exception{
		User user = userMapper.login(username);
		
		if (user==null) {
			return SysResult.build(201, "用户名不存在！");
		}else {
			String pwd = DigestUtils.md5Hex(password);
			if (!pwd.equals(user.getPassword())) {
				return SysResult.build(201, "密码输入错误！");
			}else {
				String ticket = DigestUtils.md5Hex("JT_TICKEET_"+System.currentTimeMillis()+username);
				String userJson = MAPPER.writeValueAsString(user);
				redisService.set(ticket, userJson, 60*60*24*7);
				return SysResult.build(200, "登陆成功！",ticket);
			}
		}
	}
	
	public SysResult queryByTicket(String ticket) {
		String userJson = redisService.get(ticket);
		if (userJson==null) {
			return SysResult.build(201, "Ticket信息不存在！");
		}else {
			return SysResult.build(200, "Ticket信息查询成功！", userJson);
		}
	}
	
}
