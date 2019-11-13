package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.domain.TaskAPIData;
import org.casic.workflow.domain.TaskData;
import org.casic.workflow.mapper.TaskDao;
import org.casic.workflow.service.CustomTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomTaskServiceImpl  implements CustomTaskService {
	@Autowired
	private TaskDao taskDao;
	@Override
	public List<TaskData> taskListPage(String userId) {
		return taskDao.queryByUserIdListPage(userId);
	}
	
	@Override
	public List<TaskAPIData> queryByUserIdPage(String userId) {
		return taskDao.queryByUserIdPage(userId);
	}

}
