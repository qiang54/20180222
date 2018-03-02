package com.store.service.impl;



import com.store.dao.UserDao;
import com.store.dao.impl.UserDaoImpl;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.utils.MailUtils;

public class UserServiceImpl implements UserService {

	/**
	 * 用户注册
	 * @throws Exception 
	 */
	@Override
	public void regist(User user) throws Exception {

		UserDao dao = new UserDaoImpl();
		dao.add(user);
		
		//添加用户后，发送激活邮件
		/*String emailMsg = "欢迎你，<a href='http://localhost/store/user?method=active&code="+user.getCode()+"'>点击激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);*/
	}

	/**
	 * 用户激活
	 * @throws Exception 
	 */
	@Override
	public User active(String code) throws Exception {

		UserDao dao = new UserDaoImpl();
		//1.通过code获取用户
		User user = dao.getUserByCode(code);
		
		//2.判断用户是否为空
		if(user == null){
		 return null;
		}
		
		//3.更改激活状态
		user.setState(1);
		dao.update(user);
		return null;
	}

	/**
	 * 用户登录
	 */
	@Override
	public User login(String username, String password)  throws Exception {
		// TODO login
		
		UserDao dao = new UserDaoImpl();
		return dao.getUserByUsernameAndPwd(username, password);
		
	}

	
	
	
	
	

	
	

}
