package org.casic.workflow.service;

import java.util.List;

import org.casic.workflow.domain.ActProcessType;

public interface ActProcessTypeService {
	   List<ActProcessType> selectAll();
	   public int insert(ActProcessType record);
}
