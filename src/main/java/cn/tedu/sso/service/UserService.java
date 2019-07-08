package cn.tedu.sso.service;

import cn.tedu.common.vo.SysResult;
import cn.tedu.sso.pojo.User;

public interface UserService {

	//查询用户名是否可用
	public SysResult check(String param, Integer type);
	
	//用户注册
	public SysResult register(User user);
	
	//用户登录
	public SysResult login(String username, String password) throws Exception;
	
	//查询ticket信息
	public SysResult queryByTicket(String ticket);
	
}
