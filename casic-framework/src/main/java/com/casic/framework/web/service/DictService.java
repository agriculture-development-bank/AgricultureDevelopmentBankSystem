package com.casic.framework.web.service;

import com.casic.common.utils.StringUtils;
import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysDictData;
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysDeptService;
import com.casic.system.service.ISysDictDataService;
import com.casic.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * west首创 html调用 thymeleaf 实现字典读取
 *
 * @author yuzengwen
 */
@Service("dict")
public class DictService {
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictData> getType(String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * 查询角色字典数据
     *
     * @return 字典数据集合信息
     */
    public List<SysDictData> getRoleDictData() {
        return dictDataService.selectRoleDictData();
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue) {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }


    /**
     * 判断param1(变量之间用“,”分隔)中是否包含param2
     *
     * @param param1
     * @param param2
     * @return
     */
    public boolean StrIsContains(String param1, String param2) {
        boolean flag = false;
        String[] array = param1.split(",");
        for (String str : array) {
            if (str.equals(param2)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 根据用户id查询用户姓名
     */
    public String selectUserNameById(String id) {
        String userName = "";
        SysUser user = userService.selectUserById(id);
        if (user != null) {
            userName = user.getUserName();
        }

        return userName;
    }

    /**
     * 根据字典类型和字典标签查询字典键值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @return 字典键值
     */
    public String getValue(String dictType, String dictLabel) {
        return dictDataService.selectDictValue(dictType, dictLabel);
    }

    /**
     * 根据部门id查询部门全名（格式为责任主体->部门名称）
     *
     * @param deptId
     * @return 部门全名
     */
    public String getDutyDeptName(String deptId) {
        SysDept sysDept = deptService.selectDeptById(deptId);
        String deptName = sysDept.getDeptName();
        String ancestors = sysDept.getAncestors();
        return deptName;
    }


}
