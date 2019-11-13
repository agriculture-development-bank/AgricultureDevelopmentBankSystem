package org.casic.workflow.mapper;

import java.util.List;

import com.casic.common.utils.Parametermap;
import org.casic.workflow.domain.ProcessDefEntity;

/**
 */
public interface ProcessDefDao {
    /**
     * 查询所有已经部署的流程
     * @param pm
     * @return
     */
    public List<ProcessDefEntity> queryPageAllProcessDefPage(Parametermap pm);

}
