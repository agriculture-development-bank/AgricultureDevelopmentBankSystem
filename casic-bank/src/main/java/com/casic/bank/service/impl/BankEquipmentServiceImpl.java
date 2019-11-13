package com.casic.bank.service.impl;

import com.casic.bank.domain.BankCapBoard;
import com.casic.bank.domain.BankEquipment;
import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.vo.BankEquipmentVO;
import com.casic.bank.mapper.BankCapBoardMapper;
import com.casic.bank.mapper.BankEquipmentMapper;
import com.casic.bank.mapper.BankReceiveFilesDetailMapper;
import com.casic.bank.service.BankEquipmentService;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BankEquipmentServiceImpl implements BankEquipmentService {

    /**
     * 设备IP是否唯一的返回结果码
     */
    public final static String EQUIPMENT_IP_UNIQUE = "0";
    public final static String EQUIPMENT_IP_NOT_UNIQUE = "1";

    private BankEquipmentMapper bankEquipmentMapper;

    private BankCapBoardMapper bankCapBoardMapper;

    private BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper;

    @Autowired
    public BankEquipmentServiceImpl(BankEquipmentMapper bankEquipmentMapper, BankCapBoardMapper bankCapBoardMapper, BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper) {
        this.bankEquipmentMapper = bankEquipmentMapper;
        this.bankCapBoardMapper = bankCapBoardMapper;
        this.bankReceiveFilesDetailMapper = bankReceiveFilesDetailMapper;
    }

    /**
     * 根据主键查询
     *
     * @param id 设备主键id
     * @return
     */
    @Override
    public BankEquipmentVO selectBankEquipmentById(String id) {
        return bankEquipmentMapper.selectBankEquipmentById(id);
    }

    /**
     * 根据设备IP查询设备信息
     *
     * @param ip 设备IP
     * @return
     */
    public BankEquipment selectBankEquipmentByIp(String ip) {
        return bankEquipmentMapper.selectBankEquipmentByIp(ip);
    }

    /**
     * 获取设备列表数据
     *
     * @param bankEquipment BankEquipment对象
     * @return
     */
    @Override
    public List<BankEquipmentVO> selectBankEquipmentList(BankEquipment bankEquipment) {
        List<BankEquipmentVO> list = bankEquipmentMapper.selectBankEquipmentList(bankEquipment);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 保存设备实体
     *
     * @param bankEquipment BankEquipment对象
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBankEquipment(BankEquipment bankEquipment) {
        String uuid = UuidUtils.getUUIDString();
        Date date = new Date();
        int unitLevel = bankEquipment.getUnitLevel();
        if ("".equals(bankEquipment.getId()) || bankEquipment.getId() == null) {
            bankEquipment.setId(uuid);
        }
        bankEquipment.setCreateTime(date);
        //插入载体柜信息
        int flag = bankEquipmentMapper.insertBankEquipment(bankEquipment);
        if (flag > 0) {
            //插入载体柜单元门信息
            List<BankCapBoard> bankCapBoards = buildBankCapBoardList(unitLevel, uuid, bankEquipment.getCreateBy(), date);
            if (bankCapBoards != null && bankCapBoards.size() > 0) {
                flag = bankCapBoardMapper.insertBankCapBoard(bankCapBoards);
            }
        }
        return flag;

    }

    /**
     * 构架载体柜中单元门实体类列表
     *
     * @param unitLevel 单元门数量
     * @param equipmentId 载体柜id
     * @param createBy 创建人id
     * @param date 创建日期
     * @return
     */
    private List<BankCapBoard> buildBankCapBoardList(int unitLevel, String equipmentId, String createBy, Date date) {
        List<BankCapBoard> bankCapBoards = new ArrayList<>();
        if (unitLevel > 0) {
            for (int i = 0; i < unitLevel; i++) {
                BankCapBoard bankCapBoard = new BankCapBoard();
                bankCapBoard.setId(UuidUtils.getUUIDString());
                bankCapBoard.setEquipmentId(equipmentId);
                bankCapBoard.setCapBoardCode(i+1);
                bankCapBoard.setCapBoardName((i+1)+"号门");
                bankCapBoard.setCreateBy(createBy);
                bankCapBoard.setCreateTime(date);
                bankCapBoards.add(bankCapBoard);
            }
        }
        return bankCapBoards;
    }

    /**
     * 编辑设备实体
     *
     * @param bankEquipment BankEquipment对象
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBankEquipment(BankEquipment bankEquipment) {
        int flag = 0;
        bankEquipment.setUpdateTime(new Date());
        String equipmentId = bankEquipment.getId();
        List<BankCapBoard> bankCapBoards = bankCapBoardMapper.selectBankCapBoardByEquipmentId(equipmentId);
        if (bankCapBoards != null && bankCapBoards.size() > 0) {
            int size = bankCapBoards.size();
            if (size != bankEquipment.getUnitLevel()) {
                //删除该载体柜中的所有单元门
                flag = bankCapBoardMapper.deleteBankCapBoardByEquipmentId(equipmentId);
                if (flag > 0) {
                    //插入载体柜单元门信息
                    List<BankCapBoard> capBoards = buildBankCapBoardList(bankEquipment.getUnitLevel(), bankEquipment.getId(), ShiroUtils.getUserId(), new Date());
                    if (capBoards != null && capBoards.size() > 0) {
                        bankCapBoardMapper.insertBankCapBoard(capBoards);
                    }
                }
            }
        }
        //更新载体柜信息
        flag = bankEquipmentMapper.updateBankEquipment(bankEquipment);
        return flag;
    }

    /**
     * 删除设备信息
     *
     * @param ids 待删除的id字符串
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBankEquipmentByIds(String ids) {
        String[] s = ids.split(",");
        AtomicInteger flag = new AtomicInteger(0);
        for (String id : s) {
            List<BankReceiveFilesDetail>  list = bankReceiveFilesDetailMapper.selectBankReceiveFileDetailByEquipmentId(id);
            if (list == null) {
                flag.set(bankEquipmentMapper.deleteBankEquipmentById(id));
            } else {
                return -1;
            }
        }
        return flag.get();

    }

    /**
     * 检测设备IP的唯一性
     *
     * @param bankEquipment 设备实体
     * @return
     */
    @Override
    public String checkEquipmentIpUnique(BankEquipment bankEquipment) {
        String equipmentId = "-1";
        if (StringUtils.isNotNull(bankEquipment) && StringUtils.isNotEmpty(bankEquipment.getId())) {
            equipmentId = bankEquipment.getId();
        }
        BankEquipment equipment = selectBankEquipmentByIp(bankEquipment.getIp());
        if (StringUtils.isNotNull(equipment) && !equipmentId.equals(equipment.getId())) {
            return EQUIPMENT_IP_NOT_UNIQUE;
        }
        return EQUIPMENT_IP_UNIQUE;
    }

    /**
     * 查询部门是否存在设备
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean CheckDeptExistEquipement(String deptId) {
        int result = bankEquipmentMapper.CheckDeptExistEquipement(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 检测设备中存在位置信息
     *
     * @param locationId 位置id
     * @return
     */
    @Override
    public boolean checkEquipementExistLocation(String locationId) {
        int result = bankEquipmentMapper.checkEquipementExistLocation(locationId);
        return result > 0 ? true : false;
    }
}
