package com.casic.bank.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.casic.bank.domain.*;
import com.casic.bank.mapper.*;
import com.casic.bank.service.BankReceiveFilesService;
import com.casic.common.support.Convert;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.util.ShiroUtils;
import com.casic.system.domain.SysUser;
import com.casic.system.mapper.SysDeptMapper;
import com.casic.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 文件台账业务逻辑层实现
 */
@Service
public class BankReceiveFilesServiceImpl implements BankReceiveFilesService {

    private static final String REGISTRATION_NUM_NOT_UNIQUE = "1";
    private static final String REGISTRATION_NUM_UNIQUE = "0";

    private BankReceiveFilesMapper bankReceiveFilesMapper;

    private BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper;

    private BankFileSignOpinionMapper bankFileSignOpinionMapper;

    private SysDeptMapper sysDeptMapper;

    private BankRecordMapper bankRecordMapper;

    private InCapMapper inCapMapper;

    private SysUserMapper sysUserMapper;

    @Autowired
    public BankReceiveFilesServiceImpl(BankReceiveFilesMapper bankReceiveFilesMapper,
                                       BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper,
                                       SysDeptMapper sysDeptMapper,
                                       BankFileSignOpinionMapper bankFileSignOpinionMapper,
                                       BankRecordMapper bankRecordMapper,
                                       InCapMapper inCapMapper,
                                       SysUserMapper sysUserMapper) {
        this.bankReceiveFilesMapper = bankReceiveFilesMapper;
        this.bankReceiveFilesDetailMapper = bankReceiveFilesDetailMapper;
        this.sysDeptMapper = sysDeptMapper;
        this.bankFileSignOpinionMapper = bankFileSignOpinionMapper;
        this.bankRecordMapper = bankRecordMapper;
        this.inCapMapper = inCapMapper;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 查询文件收文登记列表
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return list列表
     */

    @Override
    public List<BankReceiveFiles> selectBankReceiveFilesList(BankReceiveFiles bankReceiveFiles) {
        List<BankReceiveFiles> list = bankReceiveFilesMapper.selectBankReceiveFilesList(bankReceiveFiles);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据主键查询文件收文登记记录
     *
     * @param id 主键id
     * @return BankReceiveFiles对象
     */
    @Override
    public BankReceiveFiles selectBankReceiveFilesById(String id) {
        return bankReceiveFilesMapper.selectBankReceiveFilesById(id);
    }

    public BankReceiveFiles selectBankReceiveFiles(BankReceiveFiles bankReceiveFiles) {
        return bankReceiveFilesMapper.selectBankReceiveFiles(bankReceiveFiles);
    }

    /**
     * 新增文件收文登记
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return 受影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBankReceiveFiles(BankReceiveFiles bankReceiveFiles) {
        Date date = new Date();
        String capBoardId = bankReceiveFiles.getCapBoardId();
        String registrationNum = bankReceiveFiles.getRegistrationNum();
        int numOfCopies = Integer.valueOf(bankReceiveFiles.getNumOfCopies());
        String deptId = bankReceiveFiles.getDeptId();
        bankReceiveFiles.setCreateTime(date);
        //插入台账记录
        int flag = bankReceiveFilesMapper.insertBankReceiveFiles(bankReceiveFiles);
        if (flag > 0) {
            //插入子表树
            List<BankReceiveFilesDetail> bankReceiveFilesDetails = buildBankReceiveFilesDetailList(numOfCopies, bankReceiveFiles.getId(), bankReceiveFiles.getCreateBy(), date, registrationNum, deptId, capBoardId);
            if (bankReceiveFilesDetails != null && bankReceiveFilesDetails.size() > 0) {
                flag = bankReceiveFilesDetailMapper.insertBankReceiveFilesDetail(bankReceiveFilesDetails);
            }
        }
        return flag;
    }

    /**
     * 构造台账子表文件
     * 记录
     *
     * @param numOfCopies     文件份数
     * @param fileId          文件id
     * @param userId          操作用户id
     * @param date            新增日期
     * @param registrationNum 登记号
     * @param deptId
     * @param capBoardId
     * @return
     */
    private List<BankReceiveFilesDetail> buildBankReceiveFilesDetailList(int numOfCopies, String fileId, String userId, Date date, String registrationNum, String deptId, String capBoardId) {
        List<BankReceiveFilesDetail> bankReceiveFilesDetails = new ArrayList<>();
        if (numOfCopies > 0) {
            for (int i = 0; i < numOfCopies; i++) {
                BankReceiveFilesDetail bankReceiveFilesDetail = new BankReceiveFilesDetail();
                bankReceiveFilesDetail.setId(UuidUtils.getUUIDString());
                bankReceiveFilesDetail.setFileId(fileId);
                bankReceiveFilesDetail.setFlowId(registrationNum + "-" + (i + 1));
                bankReceiveFilesDetail.setCreateBy(userId);
                bankReceiveFilesDetail.setCreateTime(date);
                bankReceiveFilesDetail.setRfid(returnCard());
                bankReceiveFilesDetail.setStatus("1");
                bankReceiveFilesDetail.setSort(i + 1);
                //存储位置
                bankReceiveFilesDetail.setLocationId(capBoardId);
                bankReceiveFilesDetails.add(bankReceiveFilesDetail);
                if (StringUtils.isNotEmpty(deptId)) {
                    bankReceiveFilesDetail.setCurrentDeptId(deptId);
                }
            }
        }
        return bankReceiveFilesDetails;
    }


    /**
     * 修改文件收文登记记录
     *
     * @param bankReceiveFiles     文件收文登记实体
     * @param bankFileSignOpinions 文件签署意见实体
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBankReceiveFiles(BankReceiveFiles bankReceiveFiles, List<BankFileSignOpinion> bankFileSignOpinions) {
        int flag = 0;
        Date date = new Date();
        String userId = ShiroUtils.getUserId();
        bankReceiveFiles.setUpdateTime(date);
        bankReceiveFiles.setUpdateBy(userId);
        //编辑
        List<BankFileSignOpinion> collect_edit = bankFileSignOpinions.stream().filter(bankFileSignOpinion -> StringUtils.isNotEmpty(bankFileSignOpinion.getId())).collect(Collectors.toList());
        collect_edit.forEach(bankFileSignOpinion -> {
            bankFileSignOpinion.setUpdateTime(date);
            bankFileSignOpinion.setUpdateBy(userId);
        });
        //新增
        List<BankFileSignOpinion> collect_add = bankFileSignOpinions.stream().filter(bankFileSignOpinion -> StringUtils.isEmpty(bankFileSignOpinion.getId())).collect(Collectors.toList());
        collect_add.forEach(bankFileSignOpinion -> {
            bankFileSignOpinion.setCreateTime(date);
            bankFileSignOpinion.setCreateBy(userId);
            bankFileSignOpinion.setRegistrationNum(bankReceiveFiles.getRegistrationNum());
            List<BankReceiveFilesDetail> details = bankReceiveFilesDetailMapper.selectBankReceiveFileDetailByFileId(bankReceiveFiles.getId());
            for (BankReceiveFilesDetail filesDetail : details) {
                bankFileSignOpinion.setId(UuidUtils.getUUIDString());
                bankFileSignOpinion.setFileId(filesDetail.getId());
                bankFileSignOpinionMapper.insertBankFileSignOpinion(bankFileSignOpinion);
            }
        });
        flag = bankFileSignOpinionMapper.updateBankFileSignOpinions(bankFileSignOpinions);
        if (flag > 0) {
            flag = bankReceiveFilesMapper.updateBankReceiveFiles(bankReceiveFiles);
            if (flag > 0) {
                BankReceiveFilesDetail bankReceiveFilesDetail = new BankReceiveFilesDetail();
                bankReceiveFilesDetail.setUpdateBy(userId);
                bankReceiveFilesDetail.setUpdateTime(date);
                bankReceiveFilesDetail.setFileId(bankReceiveFiles.getId());
                bankReceiveFilesDetail.setCurrentDeptId(bankReceiveFiles.getDeptId());
                flag = bankReceiveFilesDetailMapper.updateBankReceiveFilesDetailsByAccountId(bankReceiveFilesDetail);
            }
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBankReceiveFilesByIds(String ids) {
        List<BankReceiveFilesDetail> bankReceiveFilesDetails = bankReceiveFilesDetailMapper.selectBankReceiveFileDetailByFileIds(Convert.toStrArray(ids));
        List<BankReceiveFilesDetail> bankReceiveFilesDetailList = bankReceiveFilesDetails.stream().filter(p -> !"1".equals(p.getStatus())).collect(Collectors.toList());
        List<String> detailIds = bankReceiveFilesDetails.stream().map(BankReceiveFilesDetail::getId).collect(Collectors.toList());
        //查询是否在柜
        int inCapCount = 0;
        int recordCount = 0;
        if (detailIds != null && detailIds.size() > 0) {
            inCapCount = inCapMapper.selectCountByFileIds(detailIds);
            recordCount = bankRecordMapper.findCountRecordByFileId(detailIds);
        }
        if (bankReceiveFilesDetailList.size() > 0 || inCapCount > 0 || recordCount > 0) {
            return 0;
        } else {
            return bankReceiveFilesMapper.deleteBankReceiveFilesByIds(Convert.toStrArray(ids));
        }
    }

    /**
     * 登记确认
     *
     * @param ids    待确认id字符串
     * @param userId 操作用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int confirmFileByIds(String ids, String userId) {
        String[] array = Convert.toStrArray(ids);
        AtomicInteger i = new AtomicInteger(0);
        Arrays.stream(array).forEach(id -> {
            BankReceiveFiles bankReceiveFiles = new BankReceiveFiles();
            bankReceiveFiles.setId(id);

            BankReceiveFiles receiveFiles = selectBankReceiveFiles(bankReceiveFiles);
            if (!StringUtils.isNull(receiveFiles)) {
                //录入台账
                BankAccount bankAccount = new BankAccount();
                bankAccount.setCreateTime(new Date());
                bankAccount.setCreateBy(userId);
            }
        });
        return i.get();
    }

    /**
     * 校验登记号是否唯一
     *
     * @param bankReceiveFiles 台账信息
     * @return 结果
     */
    @Override
    public String checkRegistrationNumUnique(BankReceiveFiles bankReceiveFiles) {
        String fileId = StringUtils.isNull(bankReceiveFiles.getId()) ? "-1" : bankReceiveFiles.getId();
        BankReceiveFiles info = bankReceiveFilesMapper.checkRegistrationNumUnique(bankReceiveFiles.getRegistrationNum());
        if (StringUtils.isNotNull(info) && !fileId.equals(info.getId())) {
            return REGISTRATION_NUM_NOT_UNIQUE;
        }
        return REGISTRATION_NUM_UNIQUE;
    }

    /**
     * 判断随机数是否重复
     *
     * @return
     */
    private String returnCard() {
        String card = getCard();
        BankReceiveFilesDetail bankReceiveFilesDetail = bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailRfid(card);
        if (bankReceiveFilesDetail != null) {
            return returnCard();
        } else {
            return card;
        }
    }

    /**
     * 生成随机数
     *
     * @return
     */
    private static String getCard() {
        Random random = new Random();
        StringBuilder catdNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            catdNumber.append(random.nextInt(10));
        }
        return catdNumber.toString();
    }

    /**
     * 文件上交
     *
     * @param jsonArray
     * @return
     */
    @Override
    public String fileHandin(JSONArray jsonArray) {
        int size = jsonArray.size();
        int count = getCount(jsonArray, 0);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.get("id") == null ? "" : jsonObject.get("id").toString();
            String rfid_number = jsonObject.get("rfid_number") == null ? "" : jsonObject.get("rfid_number").toString();
            String advance_type = jsonObject.get("advance_type") == null ? "" : jsonObject.get("advance_type").toString();
            String advance_result = jsonObject.get("advance_result") == null ? "" : jsonObject.get("advance_result").toString();
            String name = jsonObject.get("name") == null ? "" : jsonObject.get("name").toString();
            String userId = jsonObject.get("user_id") == null ? "" : jsonObject.get("user_id").toString();
            String dept = jsonObject.get("dept") == null ? "" : jsonObject.get("dept").toString();
            String position = jsonObject.get("position") == null ? "" : jsonObject.get("position").toString();
            String advance_time = jsonObject.get("advance_time") == null ? "" : jsonObject.get("advance_time").toString();
            BankReceiveFilesDetail bankReceiveFilesDetail =
                    bankReceiveFilesDetailMapper.selectBankReceiveFileDetailRfidNum(rfid_number);
            if (bankReceiveFilesDetail != null && StringUtils.isNotEmpty(bankReceiveFilesDetail.getFileId())) {
                BankReceiveFiles bankReceiveFiles = bankReceiveFilesMapper.selectBankReceiveFilesById(bankReceiveFilesDetail.getFileId());
                BankFileSignOpinion bankFileSignOpinion = new BankFileSignOpinion();
                bankFileSignOpinion.setFileId(bankReceiveFilesDetail.getId());
                bankFileSignOpinion.setLeaderName(name);
                bankFileSignOpinion.setLeaderPost(position);
                bankFileSignOpinion.setRegistrationNum(bankReceiveFiles.getRegistrationNum());
                bankFileSignOpinion.setCreateBy(bankReceiveFilesDetail.getCreateBy());
                bankFileSignOpinion.setCreateTime(new Date());
                bankFileSignOpinion.setOpinion(advance_result);
                bankFileSignOpinion.setOpinionTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",advance_time));
                bankFileSignOpinion.setOpinionType("1");
                bankFileSignOpinion.setId(UuidUtils.getUUIDString());
                bankFileSignOpinionMapper.insertBankFileSignOpinion(bankFileSignOpinion);

                bankReceiveFilesDetail.setStatus("8");
                bankReceiveFilesDetail.setUpdateTime(DateUtils.getNowDate());
                bankReceiveFilesDetailMapper.updateBankReceiveFilesDetail(bankReceiveFilesDetail);
                BankRecord bankRecord = new BankRecord();
                bankRecord.setFileId(bankReceiveFilesDetail.getId());
                bankRecord.setOperateResult("8");
                bankRecord.setOperateTime(new Date());
                bankRecord.setReceiveDept("机要室");
                if (bankReceiveFiles != null && StringUtils.isNotEmpty(bankReceiveFiles.getDeptId())) {
                    bankRecord.setBelongDept("机要室");
                }
                bankRecord.setUserId(userId);
                bankRecord.setId(UuidUtils.getUUIDString());
                bankRecord.setCreateTime(DateUtils.getNowDate());
                bankRecord.setCreateBy(userId);
                bankRecordMapper.insertBankRecord(bankRecord);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (count == size) {
            sb.append("文件上交成功").append(size).append("条");
        } else {
            if (count == 0) {
                sb.append("文件上交成功").append(size).append("条");
            } else {
                sb.append("文件上交成功").append(size - count).append("条，未找到相关FRID").append(count).append("条");
            }
        }
        return sb.toString();
    }

    /**
     * 文件下发
     *
     * @param jsonArray
     * @return
     */
    @Override
    public String fileTrans(JSONArray jsonArray) {
        int size = jsonArray.size();
        int count = getCount(jsonArray, 0);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String rfidNumber = jsonObject.get("rfid_number") == null ? "" : jsonObject.get("rfid_number").toString();
            String id = jsonObject.get("id") == null ? "" : jsonObject.get("id").toString();
            String userId = jsonObject.get("user_id") == null ? "" : jsonObject.get("user_id").toString();
            String transName = jsonObject.get("trans_name") == null ? "" : jsonObject.get("trans_name").toString();
            String deptName = jsonObject.get("dept_name") == null ? "" : jsonObject.get("dept_name").toString();
            String position = jsonObject.get("position") == null ? "" : jsonObject.get("position").toString();
            String time = jsonObject.get("time") == null ? "" : jsonObject.get("time").toString();
            BankReceiveFilesDetail bankReceiveFilesDetail =
                    bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailRfid(rfidNumber);
            if (bankReceiveFilesDetail != null && StringUtils.isNotEmpty(bankReceiveFilesDetail.getFileId())) {
                BankReceiveFiles bankReceiveFiles = bankReceiveFilesMapper.selectBankReceiveFilesById(bankReceiveFilesDetail.getFileId());
                bankReceiveFilesDetail.setStatus("9");
                bankReceiveFilesDetail.setUpdateBy(userId);
                bankReceiveFilesDetail.setUpdateTime(DateUtils.getNowDate());
                bankReceiveFilesDetailMapper.updateBankReceiveFilesDetail(bankReceiveFilesDetail);
                BankRecord bankRecord = new BankRecord();
                bankRecord.setFileId(bankReceiveFilesDetail.getId());
                bankRecord.setOperateResult("9");
                bankRecord.setOperateTime(DateUtils.dateTime("yyyy-MM-dd hh:mm:ss", time));
                bankRecord.setReceiveDept(deptName);
                if (bankReceiveFiles != null && StringUtils.isNotEmpty(bankReceiveFiles.getDeptId())) {
                    bankRecord.setBelongDept(bankReceiveFiles.getDeptName());
                }
                SysUser user = sysUserMapper.selectUserById(userId);
                bankRecord.setUserId(user.getUserName());
                bankRecord.setId(UuidUtils.getUUIDString());
                bankRecord.setCreateTime(DateUtils.getNowDate());
                bankRecord.setCreateBy(userId);
                bankRecordMapper.insertBankRecord(bankRecord);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (count == size) {
            sb.append("文件下发成功").append(size).append("条");
        } else {
            if (count == 0) {
                sb.append("文件下发成功").append(size).append("条");
            } else {
                sb.append("文件下发成功").append(size - count).append("条，未找到相关FRID").append(count).append("条");
            }
        }
        return sb.toString();
    }

    /**
     * 判断RFID不存在的数量
     *
     * @param jsonArray
     * @param count
     * @return
     */
    private int getCount(JSONArray jsonArray, int count) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String rfid = jsonObject.get("rfid_number") == null ? "" : jsonObject.get("rfid_number").toString();
            if (StringUtils.isEmpty(rfid)) {
                count += 1;
            } else {
                BankReceiveFilesDetail bankReceiveFilesDetail =
                        bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailRfid(rfid);
                if (bankReceiveFilesDetail == null) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**
     * 同步基础数据
     *
     * @return
     */
    @Override
    public List<Map> selectBankFileToPhone() {
        List<Map> fileList = bankReceiveFilesDetailMapper.selectBankFileToPhone();
        return fileList;
    }

    /**
     * 检测部门下是否存在文件台账
     *
     * @param deptId 部门id
     * @return
     */
    @Override
    public boolean checkDeptExistFile(String deptId) {
        int result = bankReceiveFilesMapper.checkDeptExistFile(deptId);
        return result > 0 ? true : false;
    }

    @Override
    public String getMaxRegistrationNum() {
        String result = bankReceiveFilesMapper.getMaxRegistrationNum();
        return result;
    }
}
