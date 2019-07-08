package cn.tedu.sso.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.tedu.sso.pojo.User;

public interface UserMapper extends BaseMapper<User>{
	
	@Select("select count(*) from tb_user where ${paramType}=#{param}")
	public Integer check(@Param("paramType") String paramType, @Param("param") String param);
	
	@Insert("INSERT INTO tb_user (id,username,password,phone,email,created,updated) values (null,#{username},#{password},#{phone},#{email},now(),now())")
	public void save(User user);
	
	@Select("select * from tb_user where username=#{username}")
	public User login(@Param("username") String username);
	
}
