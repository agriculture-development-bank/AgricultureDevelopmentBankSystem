package org.casic.workflow.service;

import java.util.List;

import org.casic.workflow.domain.FlowBusinessRecords;
import org.casic.workflow.domain.TaskData;
import org.casic.workflow.domain.TaskAPIData;

public interface CustomTaskService {
	

    /**
     * 分页查询代办事项
     * @param taskAPIData
     * @param pageSize
     * @return
     */
    List<TaskData> taskListPage(String userId);


    public List<TaskAPIData> queryByUserIdPage(String userId);


    /**
     * 流程业务记录 服务层
     *
     * @author yuzengwen
     * @date 2019-04-10
     */
    interface IFlowBusinessRecordsService
    {
        /**
         * 查询流程业务记录信息
         *
         * @param id 流程业务记录ID
         * @return 流程业务记录信息
         */
        public FlowBusinessRecords selectBusinessRecordsById(String id);

        /**
         * 查询流程业务记录列表
         *
         * @param businessRecords 流程业务记录信息
         * @return 流程业务记录集合
         */
        public List<FlowBusinessRecords> selectBusinessRecordsList(FlowBusinessRecords businessRecords);

        /**
         * 新增流程业务记录
         *
         * @param businessRecords 流程业务记录信息
         * @return 结果
         */
        public int insertBusinessRecords(FlowBusinessRecords businessRecords);

        /**
         * 修改流程业务记录
         *
         * @param businessRecords 流程业务记录信息
         * @return 结果
         */
        public int updateBusinessRecords(FlowBusinessRecords businessRecords);

        /**
         * 删除流程业务记录信息
         *
         * @param ids 需要删除的数据ID
         * @return 结果
         */
        public int deleteBusinessRecordsByIds(String ids);

    }
}
