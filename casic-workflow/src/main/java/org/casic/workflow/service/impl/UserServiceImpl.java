package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.mapper.UserDao;
import org.casic.workflow.service.UserService;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Override
	public List<UserEntityImpl> queryUserByGroup(GroupEntityImpl group) {
		return userDao.queryUserByGroup(group);
	}

	@Override
	public List<GroupEntityImpl> queryGroupByUser(UserEntityImpl user) {
		return userDao.queryGroupByUser(user);
	}

}
