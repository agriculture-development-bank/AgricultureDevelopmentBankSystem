package org.casic.workflow.mapper;

import java.util.List;

import org.casic.workflow.domain.TaskAPIData;
import org.casic.workflow.domain.TaskData;
import org.springframework.stereotype.Repository;

/**
 * 人工任务dao
 */
@Repository
public interface TaskDao {
    /**
     * 通过登录人id查询当前用户的待办事项
     * @return
     */
    List<TaskData> queryByUserIdListPage(String userId);
    /**
     * 查询已办事项
     * @param taskAPIData
     * @return
     */
    List<TaskAPIData> queryByUserIdPage(String userId);

    /**
     * 查询代办
     * @param taskAPIData
     * @return
     */
    List<TaskAPIData> queryByUserIdAndSystemIdList(TaskAPIData taskAPIData);

  

    /**
     * 已审核的流程去重
     * @param taskAPIData
     * @return
     */
    List<TaskAPIData> queryDistinctProcessByUserIdAndSystemIdChecked(TaskAPIData taskAPIData);
}
