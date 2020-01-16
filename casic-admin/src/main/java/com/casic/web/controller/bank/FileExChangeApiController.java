package com.casic.web.controller.bank;

import com.alibaba.fastjson.JSONObject;
import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.BankRecord;
import com.casic.bank.domain.api.FileBase;
import com.casic.bank.domain.api.FileInfo;
import com.casic.bank.domain.api.FileOut;
import com.casic.bank.service.BankFileDetailService;
import com.casic.bank.service.BankRecordService;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qh
 * @Classname FileExChangeApiController
 * @Description 文件交换系统接口控制器
 * @Date 2019/10/22 13:19
 */
@RestController
@RequestMapping(value = "/exchange")
@Api(tags = "exchange", description = "文件交换系统接口控制器")
public class FileExChangeApiController extends BaseController {

    private BankFileDetailService bankFileDetailService;

    private BankRecordService bankRecordService;

    @Autowired
    public FileExChangeApiController(BankFileDetailService bankFileDetailService, BankRecordService bankRecordService) {
        this.bankFileDetailService = bankFileDetailService;
        this.bankRecordService = bankRecordService;
    }

    /**
     * 文件投箱返回信息
     *
     * @param fileInfo map信息
     * @return
     */
    @PostMapping(value = "/PutFile")
    @ApiOperation(value = "文件投箱返回信息")
    public Map<String, Object> putFile(@RequestBody FileInfo fileInfo) {
        Map<String, Object> map = new HashMap<>(5);
        fileInfo.setPuttime(new Date(fileInfo.getPuttime().getTime() - (8 * 60 * 60 * 1000)));
        try {
            List<String> deptNameList= bankRecordService.getDeptName();
            String jiyao_deptName = "机要室";
            if(deptNameList != null && deptNameList.size()>0){
                jiyao_deptName = deptNameList.get(0);
            }

            if (fileInfo != null && StringUtils.isNotEmpty(fileInfo.getFilecode())) {
                BankReceiveFilesDetail filesDetail = bankFileDetailService.selectBankFileDetailByRfid(fileInfo.getFilecode());
                if (filesDetail != null && StringUtils.isNotEmpty(filesDetail.getId())) {
                    //1、根据RFID编号更新文件状态
                    BankReceiveFilesDetail detail = new BankReceiveFilesDetail();
                    detail.setRfid(fileInfo.getFilecode());
                    if (StringUtils.isNotEmpty(fileInfo.getTargetdept())) {
                        if (jiyao_deptName.equalsIgnoreCase(fileInfo.getTargetdept().trim())) {
                            detail.setStatus("4");//已归还
                        } else {
                            detail.setStatus("2");//已投递
                        }
                    }
                    detail.setUpdateTime(fileInfo.getPuttime() != null ? fileInfo.getPuttime() : new Date());
                    bankFileDetailService.updateBankReceiveFilesDetailByRfid(detail);
                    //2、保存文件投箱记录
                    BankRecord bankRecord = new BankRecord();
                    bankRecord.setId(UuidUtils.getUUIDString());
                    bankRecord.setUserId("");
//                    bankRecord.setUserId(StringUtils.isNotEmpty(fileInfo.getPutuser()) ? fileInfo.getPutuser() : "");

                    bankRecord.setOperateTime(fileInfo.getPuttime() != null ? fileInfo.getPuttime() : new Date());
                    if (StringUtils.isNotEmpty(fileInfo.getTargetdept())) {
                        if (jiyao_deptName.equalsIgnoreCase(fileInfo.getTargetdept().trim())) {
                            System.out.println("fileInfo = " + jiyao_deptName.equals(fileInfo.getTargetdept().trim()));
                            bankRecord.setOperateResult("4");//已归还
                            String deptName=bankRecordService.selectReceiveDeptForMaxTimeByFileID(filesDetail.getId());
                            //bankRecord.setBelongDept(deptName);
                            bankRecord.setReceiveDept(StringUtils.isNotEmpty(fileInfo.getTargetdept()) ? fileInfo.getTargetdept() : "");
                        } else {
                            System.out.println("fileInfo = " + jiyao_deptName.equals(fileInfo.getTargetdept().trim()));
                            bankRecord.setOperateResult("2");//已投递
                            //bankRecord.setBelongDept(jiyao_deptName);
                            bankRecord.setReceiveDept(StringUtils.isNotEmpty(fileInfo.getTargetdept()) ? fileInfo.getTargetdept() : "");
                        }
                    }
                    bankRecord.setFileId(filesDetail.getId());
                    bankRecord.setCreateTime(new Date());
                    bankRecordService.insertBankRecord(bankRecord);
                    map.put("result", "succeed");
                    map.put("resmsg", "成功");
                } else {
                    map.put("result", "failed");
                    map.put("resmsg", "文件投箱失败，找不到台账信息");
                }
            } else {
                map.put("result", "failed");
                map.put("resmsg", "文件投箱失败，公文二维条码编号不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
            map.put("resmsg", "文件投箱失败");
        }
        return map;
    }

    /**
     * 文件取件返回信息
     *
     * @param fileOut
     * @return
     */
    @RequestMapping(value = "/GetFile")
    @ApiOperation(value = "文件取件返回信息")
    public Map<String, Object> getFile(@RequestBody FileOut fileOut) {
        Map<String, Object> map = new HashMap<>(5);
        fileOut.setTakeouttime(new Date(fileOut.getTakeouttime().getTime() - (8 * 60 * 60 * 1000)));
        try {
            List<String> deptNameList= bankRecordService.getDeptName();
            String jiyao_deptName = "机要室";
            if(deptNameList != null && deptNameList.size()>0){
                jiyao_deptName = deptNameList.get(0);
            }

            if (fileOut != null && StringUtils.isNotEmpty(fileOut.getFilecode())) {
                BankReceiveFilesDetail filesDetail = bankFileDetailService.selectBankFileDetailByRfid(fileOut.getFilecode());
                if (filesDetail != null && StringUtils.isNotEmpty(filesDetail.getId())) {
                    //1、根据RFID编号更新文件状态
                    BankReceiveFilesDetail detail = new BankReceiveFilesDetail();
                    detail.setRfid(fileOut.getFilecode());
                    if (StringUtils.isNotEmpty(fileOut.getTakeoutdept())) {
                        if (jiyao_deptName.equals(fileOut.getTakeoutdept().trim())) {
                            detail.setStatus("5");
                        } else {
                            detail.setStatus("3");
                        }
                    }
                    bankFileDetailService.updateBankReceiveFilesDetailByRfid(detail);
                    //2、保存文件投箱记录
                    BankRecord bankRecord = new BankRecord();
                    bankRecord.setId(UuidUtils.getUUIDString());
                    bankRecord.setUserId(StringUtils.isNotEmpty(fileOut.getTakeoutuser()) ? fileOut.getTakeoutuser() : "");
                    bankRecord.setBelongDept(StringUtils.isNotEmpty(fileOut.getTakeoutdept()) ? fileOut.getTakeoutdept() : "");
                    bankRecord.setReceiveDept(StringUtils.isNotEmpty(fileOut.getTakeoutdept()) ? fileOut.getTakeoutdept() : "");
                    bankRecord.setOperateTime(fileOut.getTakeouttime() != null ? fileOut.getTakeouttime() : new Date());
                    if (StringUtils.isNotEmpty(fileOut.getTakeoutdept())) {
                        if (jiyao_deptName.equals(fileOut.getTakeoutdept().trim())) {
                            bankRecord.setOperateResult("5");
                        } else {
                            bankRecord.setOperateResult("3");
                        }
                    }
                    bankRecord.setFileId(filesDetail.getId());
                    bankRecord.setCreateTime(new Date());
                    bankRecordService.insertBankRecord(bankRecord);
                    map.put("result", "succeed");
                    map.put("resmsg", "成功");
                } else {
                    map.put("result", "failed");
                    map.put("resmsg", "文件投箱失败，找不到台账信息");
                }
            } else {
                map.put("result", "failed");
                map.put("resmsg", "文件投箱失败，公文二维条码编号不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "failed");
            map.put("resmsg", "失败");
        }
        return map;
    }

    /**
     * 查询基本信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBasicData")
    @ApiOperation(value = "查询基本信息")
    public List<Map<String, Object>> getBasicData(HttpServletRequest request) throws IOException {
        BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String str;
        while ((str = bf.readLine()) != null) {
            sb.append(str);
        }
        String param = URLDecoder.decode(sb.toString(),"utf-8");
        FileBase fileBase = JSONObject.parseObject(param, FileBase.class);
        return bankFileDetailService.selectBankFileDetailAnsyc(fileBase);
    }
}
