package com.casic.bank.service.impl;


import com.casic.bank.domain.BankAnalysisDetail;
import com.casic.bank.domain.BankAnalysisManage;
import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.BankRecord;
import com.casic.bank.domain.vo.BankAnalysisManageVO;
import com.casic.bank.domain.vo.BankAnalysisVO;
import com.casic.bank.mapper.BankAnalysisDetailMapper;
import com.casic.bank.mapper.BankAnalysisManageMapper;
import com.casic.bank.mapper.BankReceiveFilesDetailMapper;
import com.casic.bank.mapper.BankRecordMapper;
import com.casic.bank.service.BankAnalysisManageService;
import com.casic.common.support.Convert;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.util.ShiroUtils;
import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysUser;
import com.casic.system.mapper.SysDeptMapper;
import com.casic.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 文件清退计划业务逻辑实现
 */
@Service
public class BankAnalysisManageServiceImpl implements BankAnalysisManageService {

    private BankAnalysisManageMapper bankAnalysisManageMapper;

    private BankAnalysisDetailMapper bankAnalysisDetailMapper;

    private BankRecordMapper bankRecordMapper;

    private BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper;

    private SysDeptMapper sysDeptMapper;

    private SysUserMapper sysUserMapper;

    @Autowired
    public BankAnalysisManageServiceImpl(BankAnalysisManageMapper bankAnalysisManageMapper, BankAnalysisDetailMapper bankAnalysisDetailMapper, BankRecordMapper bankRecordMapper, BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper, SysDeptMapper sysDeptMapper, SysUserMapper sysUserMapper) {
        this.bankAnalysisManageMapper = bankAnalysisManageMapper;
        this.bankAnalysisDetailMapper = bankAnalysisDetailMapper;
        this.bankRecordMapper = bankRecordMapper;
        this.bankReceiveFilesDetailMapper = bankReceiveFilesDetailMapper;
        this.sysDeptMapper = sysDeptMapper;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 查询台账列表数据
     *
     * @param bankAnalysisManage 文件台账实体
     * @return 列表数据
     */
    @Override
    public List<BankAnalysisManageVO> findAnalysisManage(BankAnalysisManage bankAnalysisManage) {
        List<BankAnalysisManageVO> list = bankAnalysisManageMapper.findBankAnalysisManageList(bankAnalysisManage);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据主键查询清退计划
     *
     * @param id 清退计划id
     * @return BankAnalysisManage对象
     */
    @Override
    public BankAnalysisManage selectBankAnalysisManageById(String id) {
        return bankAnalysisManageMapper.findAnalysisManageById(id);
    }

    /**
     * 新增清退计划
     *
     * @param bankAnalysisVOs 清退计划
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBankAnalysisManage(List<BankAnalysisVO> bankAnalysisVOs) {
        if (bankAnalysisVOs != null && bankAnalysisVOs.size() > 0) {
            AtomicInteger i = new AtomicInteger(0);
            AtomicInteger count = new AtomicInteger(0);
            bankAnalysisVOs.forEach(bankAnalysisVO -> {
                count.addAndGet(Integer.parseInt(bankAnalysisVO.getSelectedNum()));
            });
            saveAnalysisManagePlan(i, count, bankAnalysisVOs);
            return count.get() == i.get() ? 1 : 0;
        }
        return 0;
    }

    private void saveAnalysisManagePlan(AtomicInteger i, AtomicInteger count, List<BankAnalysisVO> bankAnalysisVOs) {
        BankAnalysisManage bankAnalysisManage = new BankAnalysisManage();
        bankAnalysisManage.setCreateBy(ShiroUtils.getUserId());
        bankAnalysisManage.setCreateTime(new Date());
        String uuid = UuidUtils.getUUIDString();
        bankAnalysisManage.setId(uuid);
        //未开始
        bankAnalysisManage.setPlanStatus("0");
        LocalDate now = LocalDate.now();
        //开始日期
        bankAnalysisManage.setStartTime(now);
        bankAnalysisManage.setDeptId(ShiroUtils.getUser().getDeptId());
        //默认为机要处
        SysDept sysDept = sysDeptMapper.selectDeptByCode("001");
        bankAnalysisManage.setBelongDeptId(sysDept.getDeptId());
        bankAnalysisManage.setNum(String.valueOf(count.get()));
        AtomicInteger flag = new AtomicInteger(bankAnalysisManageMapper.insertBankAnalysisManage(bankAnalysisManage));
        //插入清退子表
        if (flag.get() > 0) {
            bankAnalysisVOs.forEach(bankAnalysisVO -> {
                String fileDetailIds = bankAnalysisVO.getFileDetailIds();
                String accountId = bankAnalysisVO.getId();
                String[] fileIds = fileDetailIds.split(",");
                Arrays.stream(fileIds).forEach(fileId -> {
                    BankAnalysisDetail bankAnalysisDetail = new BankAnalysisDetail();
                    bankAnalysisDetail.setId(UuidUtils.getUUIDString());
                    bankAnalysisDetail.setPlanId(uuid);
                    bankAnalysisDetail.setAccountId(accountId);
                    bankAnalysisDetail.setFileId(fileId);
                    bankAnalysisDetail.setStatus("0");
                    bankAnalysisDetail.setCreateTime(new Date());
                    bankAnalysisDetail.setCreateBy(ShiroUtils.getUserId());
                    flag.set(bankAnalysisDetailMapper.insertBankAnalysisDetail(bankAnalysisDetail));
                    if (flag.get() > 0) {
                        i.getAndIncrement();
                    }
                });
            });
        }
    }

    /**
     * 编辑清退计划
     *
     * @param bankAnalysisVOs 台账信息
     * @param planId          清退计划id
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBankAnalysisManage(List<BankAnalysisVO> bankAnalysisVOs, String planId) {
        if (bankAnalysisVOs != null && bankAnalysisVOs.size() > 0) {
            AtomicInteger i = new AtomicInteger(0);
            AtomicInteger count = new AtomicInteger(0);
            bankAnalysisVOs.forEach(bankAnalysisVO -> {
                count.addAndGet(Integer.parseInt(bankAnalysisVO.getSelectedNum()));
            });
            int s = bankAnalysisDetailMapper.deleteBankAnalysisDetailByAnalysisManageId(planId);
            if (s > 0) {
                s = bankAnalysisManageMapper.deleteById(planId);
                if (s > 0) {
                    saveAnalysisManagePlan(i, count, bankAnalysisVOs);
                }
            }
            return count.get() == i.get() ? 1 : 0;
        }
        return 0;
    }

    /**
     * 批量删除清退任务
     *
     * @param ids 待删除id字符串
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBankAnalysisManageByIds(String ids) {
        //删除子表信息
        int flag = bankAnalysisDetailMapper.deleteBankAnalysisDetailByAnalysisManageIds(Convert.toStrArray(ids));
        if (flag > 0) {
            flag = bankAnalysisManageMapper.deleteByIds(Convert.toStrArray(ids));
        }
        return flag;
    }

    /**
     * 清退文件
     *
     * @param ids    待清退文件id字符串
     * @param planId 清退计划id
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int analysisBankAnalysisManageByIds(String ids, String planId) {
        BankAnalysisManage manage = bankAnalysisManageMapper.findAnalysisManageById(planId);
        int flag = 0;
        String[] detailIds = Convert.toStrArray(ids);
        for (String id : detailIds) {
            //清退文件
            flag = bankAnalysisDetailMapper.analysisBankAnalysisDetailById(id);
            if (flag > 0) {
                BankAnalysisDetail bankAnalysisDetail = bankAnalysisDetailMapper.selectBankAnalysisDetailById(id);
                String fileId = bankAnalysisDetail.getFileId();
                BankReceiveFilesDetail bankReceiveFilesDetail = bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailById(fileId);
                //清退完成之后，插入流程记录
                BankRecord bankRecord = new BankRecord();
                bankRecord.setId(UuidUtils.getUUIDString());
                SysUser user = ShiroUtils.getUser();
                bankRecord.setUserId(user != null ? user.getUserName() : "");
                bankRecord.setCreateTime(new Date());
                bankRecord.setOperateResult("7");
                SysDept userDept = user.getDept();
                bankRecord.setBelongDept(userDept != null ? userDept.getDeptName() : "");
                String currentDeptId = bankReceiveFilesDetail.getCurrentDeptId();
                //文件当前所在部门
                SysDept sysDept = sysDeptMapper.selectDeptById(currentDeptId);
                bankRecord.setReceiveDept(sysDept.getDeptName());
                bankRecord.setCreateBy(user.getUserId());
                bankRecord.setFileId(fileId);
                bankRecord.setOperateTime(new Date());
                bankRecord.setAnalysisDetailId(bankAnalysisDetail.getId());
                bankRecordMapper.insertBankRecord(bankRecord);
                //更改文件状态
                BankReceiveFilesDetail bankReceiveFilesDetail1 = new BankReceiveFilesDetail();
                bankReceiveFilesDetail1.setId(fileId);
                bankReceiveFilesDetail1.setStatus("7");
                bankReceiveFilesDetail1.setUpdateTime(new Date());
                bankReceiveFilesDetail1.setUpdateBy(user.getUserId());
                flag = bankReceiveFilesDetailMapper.updateBankReceiveFilesDetail(bankReceiveFilesDetail1);
            }
        }
        flag = analysisManagePlanUpdate(planId, manage, flag);
        return flag;
    }

    /**
     * 取消已清退的文件
     *
     * @param ids    待清退文件id字符串
     * @param planId 清退计划id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelAnalysisBankAnalysisManageByIds(String ids, String planId) {
        BankAnalysisManage manage = bankAnalysisManageMapper.findAnalysisManageById(planId);
        int flag = 0;
        String[] detailIds = Convert.toStrArray(ids);
        for (String id : detailIds) {
            //取消清退文件
            flag = bankAnalysisDetailMapper.cancelAnalysisBankAnalysisManageById(id);
            if (flag > 0) {
                BankAnalysisDetail bankAnalysisDetail = bankAnalysisDetailMapper.selectBankAnalysisDetailById(id);
                String fileId = bankAnalysisDetail.getFileId();
                BankReceiveFilesDetail bankReceiveFilesDetail = bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailById(fileId);
                //取消清退完成之后，插入流程记录
                BankRecord bankRecord = new BankRecord();
                bankRecord.setId(UuidUtils.getUUIDString());
                bankRecord.setUserId(ShiroUtils.getUser().getUserName());
                bankRecord.setOperateResult("6");
                bankRecord.setBelongDept(ShiroUtils.getUser().getDept().getDeptName());
                String currentDeptId = bankReceiveFilesDetail.getCurrentDeptId();
                SysDept sysDept = sysDeptMapper.selectDeptById(currentDeptId);
                bankRecord.setReceiveDept(sysDept.getDeptName());
                bankRecord.setUpdateTime(new Date());
                bankRecord.setUpdateBy(ShiroUtils.getUserId());
                bankRecord.setFileId(fileId);
                bankRecord.setOperateTime(new Date());
                bankRecord.setAnalysisDetailId(bankAnalysisDetail.getId());
                bankRecordMapper.insertBankRecord(bankRecord);
                //更改文件状态
                BankReceiveFilesDetail bankReceiveFilesDetail1 = new BankReceiveFilesDetail();
                bankReceiveFilesDetail1.setId(fileId);
                bankReceiveFilesDetail1.setStatus("1");
                bankReceiveFilesDetail1.setUpdateTime(new Date());
                bankReceiveFilesDetail1.setUpdateBy(ShiroUtils.getUserId());
                flag = bankReceiveFilesDetailMapper.updateBankReceiveFilesDetail(bankReceiveFilesDetail1);
            }
        }
        flag = analysisManagePlanUpdate(planId, manage, flag);
        return flag;
    }

    /**
     * 根据清退计划id查询该计划下的所有文件信息
     *
     * @param bankAnalysisVO
     * @return
     */
    @Override
    public List<BankAnalysisVO> findSelectedBankAnalysisDetail(BankAnalysisVO bankAnalysisVO) {
        List<BankAnalysisVO> list = bankAnalysisManageMapper.findSelectedBankAnalysisDetail(bankAnalysisVO);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 更新清退计划状态
     *
     * @param planId 计划id
     * @param manage
     * @param flag
     * @return
     */
    private int analysisManagePlanUpdate(String planId, BankAnalysisManage manage, int flag) {
        if (flag > 0) {
            //更细清退计划
            List<BankAnalysisDetail> bankAnalysisDetails = bankAnalysisDetailMapper.selectBankAnalysisDetail(planId);
            if (bankAnalysisDetails != null && bankAnalysisDetails.size() > 0) {
                List<BankAnalysisDetail> analysisDetails = bankAnalysisDetails.stream().filter(bankAnalysisDetail -> "0".equals(bankAnalysisDetail.getStatus())).collect(Collectors.toList());
                BankAnalysisManage bankAnalysisManage = new BankAnalysisManage();
                bankAnalysisManage.setId(planId);
                if (analysisDetails.size() != 0) {
                    if (analysisDetails.size() == Integer.valueOf(manage.getNum())) {
                        bankAnalysisManage.setPlanStatus("0");
                        bankAnalysisManage.setEndTime(null);
                    } else {
                        bankAnalysisManage.setPlanStatus("1");
                        bankAnalysisManage.setEndTime(null);
                    }
                } else {
                    bankAnalysisManage.setEndTime(LocalDate.now());
                    bankAnalysisManage.setPlanStatus("2");
                }
                flag = bankAnalysisManageMapper.updateBankAnalysisManage(bankAnalysisManage);
            }
        }
        return flag;
    }
}
