package org.casic.workflow.service.impl;

import java.util.List;

import com.casic.common.utils.Parametermap;
import org.casic.workflow.domain.ProcessDefEntity;
import org.casic.workflow.service.ProcessDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessDefServiceImpl implements ProcessDefService {
	@Autowired
	private org.casic.workflow.mapper.ProcessDefDao ProcessDefDao;
	@Override
	public List<ProcessDefEntity> queryPageAllProcessDef(Parametermap pm) {
		return ProcessDefDao.queryPageAllProcessDefPage(pm);
	}

}
