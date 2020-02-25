package com.casic.web.controller.bank;

import com.casic.bank.mapper.BankAnalysisManageMapper;
import com.casic.bank.mapper.BankReceiveFilesMapper;
import com.casic.bank.mapper.BankRecordMapper;
import com.casic.common.json.JSONObject;
import com.casic.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析控制器
 */
@Controller
@RequestMapping(value = "/bank/statistics")
public class StatisticsController extends BaseController {

    private static final String PREFIX = "bank/statistics";

    private BankReceiveFilesMapper bankReceiveFilesMapper;

    private BankRecordMapper bankRecordMapper;

    private BankAnalysisManageMapper bankAnalysisManageMapper;

    @Autowired
    public StatisticsController(BankReceiveFilesMapper bankReceiveFilesMapper, BankAnalysisManageMapper bankAnalysisManageMapper,
                                BankRecordMapper bankRecordMapper) {
        this.bankReceiveFilesMapper = bankReceiveFilesMapper;
        this.bankAnalysisManageMapper = bankAnalysisManageMapper;
        this.bankRecordMapper = bankRecordMapper;
    }

    /**
     * 统计视图
     *
     * @param modelMap map集合
     * @return
     */
    @GetMapping
    public String index(ModelMap modelMap) {
        return PREFIX + "/statistics";
    }

    /**
     * 按文件类别统计
     *
     * @return
     */
    @GetMapping(value = "/queryFileTypeData")
    @ResponseBody
    public JSONObject queryFileTypeData() {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> maps = bankReceiveFilesMapper.queryFileTypeData();
        if (maps != null && maps.size() > 0) {
            jsonObject.put("seriesData", maps);
            jsonObject.put("legendData", maps.stream().map(p -> p.get("name").toString()).sorted().toArray());
            Map<String,String> dataMap = new HashMap<>();
            maps.stream().forEach(data->{
                dataMap.put(data.get("name").toString(),data.get("DIVTVALUE").toString());
            });
            jsonObject.put("levelMap", dataMap);
        }
        return jsonObject;
    }

    /**
     * 按文件状态统计
     *
     * @return
     */
    @GetMapping(value = "/queryFileStatusData")
    @ResponseBody
    public JSONObject queryFileStatusData() {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> maps = bankReceiveFilesMapper.queryFileStatusData();
        if (maps != null && maps.size() > 0) {
            jsonObject.put("seriesData", maps);
            jsonObject.put("legendData", maps.stream().map(p -> p.get("name").toString()).sorted().toArray());

            Map<String,String> dataMap = new HashMap<>();
            maps.stream().forEach(data->{
                dataMap.put(data.get("name").toString(),data.get("DIVTVALUE").toString());
            });
            jsonObject.put("statusMap", dataMap);
        }
        return jsonObject;
    }

    /**
     * 按紧急程度统计
     *
     * @return
     */
    @GetMapping(value = "/queryFileUrgencyData")
    @ResponseBody
    public JSONObject queryFileUrgencyData() {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> maps = bankReceiveFilesMapper.queryFileUrgencyData();
        if (maps != null && maps.size() > 0) {
            jsonObject.put("seriesData", maps);
            jsonObject.put("legendData", maps.stream().map(p -> p.get("name").toString()).sorted().toArray());

            Map<String,String> dataMap = new HashMap<>();
            maps.stream().forEach(data->{
                dataMap.put(data.get("name").toString(),data.get("DIVTVALUE").toString());
            });
            jsonObject.put("urgencyMap", dataMap);
        }
        return jsonObject;
    }

    /**
     * 按清退计划统计
     *
     * @return
     */
    @GetMapping(value = "/queryTaskData")
    @ResponseBody
    public Object queryTaskData() {
        return bankAnalysisManageMapper.selectBankAnalysisManageByYear();
    }

    /**
     * 按部门使用文件情况统计
     *
     * @param type
     * @return
     */
    @GetMapping(value = "/queryRecordCountData")
    @ResponseBody
    public JSONObject queryRecordCountData(String type) {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> maps = bankRecordMapper.selectCountRecordByDept(type);
        if (maps != null && maps.size() > 0) {
            jsonObject.put("seriesData", maps);
            jsonObject.put("legendData", maps.stream().map(p -> p.get("name").toString()).sorted().toArray());
        }
        return jsonObject;
    }
}
