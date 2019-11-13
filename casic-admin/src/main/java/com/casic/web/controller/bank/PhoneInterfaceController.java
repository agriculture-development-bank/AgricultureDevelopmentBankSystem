package com.casic.web.controller.bank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.casic.bank.service.BankReceiveFilesService;
import com.casic.common.base.AjaxResult;
import com.casic.framework.web.service.DictService;
import com.casic.system.service.ISysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.casic.common.base.AjaxResult.success;

/**
 * 手持机数据接口
 * Created by PC-015 on 2019/7/1.
 */
@RestController
@RequestMapping("/app")
public class PhoneInterfaceController {

    private ISysUserService sysUserService;

    private BankReceiveFilesService bankReceiveFilesService;

    private DictService dictService;

    public PhoneInterfaceController(ISysUserService sysUserService,
                                    BankReceiveFilesService bankReceiveFilesService,
                                    DictService dictService) {
        this.sysUserService = sysUserService;
        this.bankReceiveFilesService = bankReceiveFilesService;
        this.dictService = dictService;
    }

    /**
     * 同步基础数据
     *
     * @return
     */
    @GetMapping("/updateBasicData")
    public String updateBasicData() {
        List<Map> userList = sysUserService.updateBasicData();
        List<Map> assetList = bankReceiveFilesService.selectBankFileToPhone();

        for (Map map : assetList) {
            String srcretLevel = map.get("file_class") == null ? "" : map.get("file_class").toString();
            String fileClass = dictService.getLabel("secrecy_level", srcretLevel);
            map.put("file_class", fileClass);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userList", userList);
        jsonObject.put("assetList", assetList);
        String result = JSON.toJSONString(jsonObject);
        return result;
    }

    /**
     * 文件上交（手持机接口）
     *
     * @param arryList
     * @return
     */
    @PostMapping("/fileHandin")
    public AjaxResult fileHandin(String arryList) {
        try {
            JSONArray jsonArray = JSON.parseArray(arryList);
            String msg = bankReceiveFilesService.fileHandin(jsonArray);
            return success(msg);
        } catch (Exception e) {
            return AjaxResult.error("服务器错误");
        }

    }

    /**
     * 文件下发(手持机)
     *
     * @param arryList
     * @return
     */
    @PostMapping("/fileTrans")
    public AjaxResult fileTrans(String arryList) {
        try {
            JSONArray jsonArray = JSON.parseArray(arryList);
            String msg = bankReceiveFilesService.fileTrans(jsonArray);
            return success(msg);
        } catch (Exception e) {
            return AjaxResult.error("服务器错误");
        }
    }
}
