package com.casic.web.controller.bank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.casic.bank.domain.BankFileSignOpinion;
import com.casic.bank.domain.BankReceiveFiles;
import com.casic.bank.domain.ResultBean;
import com.casic.bank.domain.vo.BankEquipmentVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.service.*;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.config.Global;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.framework.web.service.DictService;
import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysDeptService;
import com.casic.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件接收控制器
 */
@Controller
@RequestMapping(value = "/bank/receive")
public class BankReceiveFilesController extends BaseController {

    private static final String PREFIX = "bank/fileReceive/";

    private BankReceiveFilesService bankReceiveFilesService;

    private BankFileDetailService bankFileDetailService;

    private BankFileSignOpinionService bankFileSignOpinionService;

    private BankEquipmentService bankEquipmentService;

    private ISysUserService userService;

    private ISysDeptService sysDeptService;

    private DictService dictService;

    private BankRecordService bankRecordService;

    @Autowired
    public BankReceiveFilesController(BankReceiveFilesService bankReceiveFilesService,
                                      BankFileDetailService bankFileDetailService,
                                      BankFileSignOpinionService bankFileSignOpinionService,
                                      ISysUserService userService,
                                      ISysDeptService sysDeptService,
                                      DictService dictService,
                                      BankEquipmentService bankEquipmentService,
                                      BankRecordService bankRecordService) {
        this.bankReceiveFilesService = bankReceiveFilesService;
        this.bankFileDetailService = bankFileDetailService;
        this.bankFileSignOpinionService = bankFileSignOpinionService;
        this.userService = userService;
        this.sysDeptService = sysDeptService;
        this.dictService = dictService;
        this.bankEquipmentService = bankEquipmentService;
        this.bankRecordService = bankRecordService;
    }

    @Override
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 列表视图
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("bank:receive:view")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("sysVersion", Global.getConfig("casic.sysVersion"));
        modelMap.put("printPort", Global.getConfig("casic.printPort"));

        BankReceiveFiles bankReceiveFiles = new BankReceiveFiles();
        String secretLevel = request.getParameter("secretLevel");
        StringBuilder selectSb = new StringBuilder();
        if(StringUtils.isNotEmpty(secretLevel)){
            bankReceiveFiles.setSecretLevel(secretLevel);
            selectSb.append("&secretLevel=").append(secretLevel);
        }

        String urgency = request.getParameter("urgency");
        if(StringUtils.isNotEmpty(urgency)){
            bankReceiveFiles.setUrgency(urgency);
            selectSb.append("&urgency=").append(urgency);
        }

        String status = request.getParameter("status");
        if(StringUtils.isNotEmpty(status)){
            bankReceiveFiles.setStatus(status);
            selectSb.append("&status=").append(status);
        }
        if (selectSb.length()>0){
            modelMap.addAttribute("selectStr",selectSb.toString().replaceFirst("&","?"));
        }else{
            modelMap.addAttribute("selectStr","");
        }

        modelMap.addAttribute("bankReceiveFiles",bankReceiveFiles);
        return PREFIX + "list";
    }

    /**
     * 新增视图
     *
     * @return
     */
    @GetMapping(value = "/add")
    @RequiresPermissions("bank:receive:addView")
    public String add(ModelMap modelMap) {
        String registrationNum = bankReceiveFilesService.getMaxRegistrationNum();
//        String year = DateUtils.getDate().substring(0,4);
        modelMap.put("registrationNum", registrationNum);
        modelMap.put("sysVersion", Global.getConfig("casic.sysVersion"));
        modelMap.put("printPort", Global.getConfig("casic.printPort"));
        unionSelect(modelMap);
        return PREFIX + "add";
    }

    /**
     * 查询责任人和部门
     *
     * @param modelMap map集合
     */
    private void unionSelect(ModelMap modelMap) {
        //责任人
        SysUser sysUser = new SysUser();

//        sysUser.setPositionCode("hld");
        List<SysUser> sysUsers = userService.selectSysUserList(sysUser);
        modelMap.put("sysUsers", sysUsers);

        //所属部门
        SysDept sysDept = new SysDept();
        sysDept.setStatus("0");
        List<SysDept> sysDepts = sysDeptService.selectDeptList(sysDept);
        modelMap.put("sysDepts", sysDepts);
        //载体柜
        List<BankEquipmentVO> bankEquipmentVOS = bankEquipmentService.selectBankEquipmentList(new BankEquipmentVO());
        modelMap.put("bankEquipments", bankEquipmentVOS);
    }

    /**
     * 修改视图
     *
     * @param id       主键
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        BankReceiveFiles bankReceiveFiles = bankReceiveFilesService.selectBankReceiveFilesById(id);
        List<BankFileSignOpinion> bankFileSignOpinions = bankFileSignOpinionService.selectBankFileSignOpinionByRegistrationum(bankReceiveFiles.getRegistrationNum());
        Optional<BankFileSignOpinion> directorOpinion = bankFileSignOpinions.stream().filter(p -> "2".equals(p.getOpinionType())).findFirst();
        Optional<BankFileSignOpinion> hostOpinion = bankFileSignOpinions.stream().filter(p -> "3".equals(p.getOpinionType())).findFirst();
//        Optional<BankFileSignOpinion> coOrganzierOpinion = bankFileSignOpinions.stream().filter(p -> "4".equals(p.getOpinionType())).findFirst();
        modelMap.addAttribute("bankReceiveFiles", bankReceiveFiles);
        modelMap.addAttribute("bankFileSignOpinions", bankFileSignOpinions);
        modelMap.addAttribute("directorOpinion", directorOpinion.isPresent() ? directorOpinion.get() : null);
        modelMap.addAttribute("hostOpinion", hostOpinion.isPresent() ? hostOpinion.get() : null);
//        modelMap.addAttribute("coOrganzierOpinion", coOrganzierOpinion.isPresent() ? coOrganzierOpinion.get() : null);
        modelMap.addAttribute("fileId", id);
        modelMap.addAttribute("registrationNum", bankReceiveFiles.getRegistrationNum());
        List<BankEquipmentVO> bankEquipmentVOS = bankEquipmentService.selectBankEquipmentList(new BankEquipmentVO());
        modelMap.put("bankEquipments", bankEquipmentVOS);

        unionSelect(modelMap);

        List<SysUser> usersList = (List<SysUser>)modelMap.get("sysUsers");

        List<String> selUserIdList = bankFileSignOpinions.stream().map(BankFileSignOpinion::getLeaderName).collect(Collectors.toList());

        usersList.stream().forEach(user->{
            if(selUserIdList.contains(user.getUserId()) || selUserIdList.size() == 0){
                user.setStatus("1");
            }else{
                user.setStatus("0");
            }
        });
        modelMap.put("sysUsers",usersList);

        return PREFIX + "edit";
    }

    /**
     * 详情
     *
     * @param id       主键
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") String id, ModelMap modelMap) {
        BankReceiveFiles bankReceiveFiles = bankReceiveFilesService.selectBankReceiveFilesById(id);
        modelMap.addAttribute("bankReceiveFiles", bankReceiveFiles);
        return PREFIX + "detail";
    }

    /**
     * 获取列表数据
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return
     */
    @RequiresPermissions("bank:receive:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(BankReceiveFiles bankReceiveFiles) {
        startPage();

        //获取台账id集合
        List<BankReceiveFiles> list = bankReceiveFilesService.selectBankReceiveFilesList(bankReceiveFiles);
        List<String> fileIdList = list.stream().map(BankReceiveFiles::getId).collect(Collectors.toList());

        //获取台账明细id集合
        //  fileIdList.stream().collect(Collectors.joining(","));
        List<BankReceiveFilesDetailVO> detailList = bankFileDetailService.selectBankFileDetailByFileIds(String.join(",",fileIdList));
        List<String> detailIdList = detailList.stream().map(BankReceiveFilesDetailVO::getId).collect(Collectors.toList());

        //查询各明细离柜日期
        Map<String ,Object> voMap = new HashMap<>();
        voMap.put("idList",detailIdList);
//        voMap.put("type","file");
        List<Map<String,String>> dataList = bankRecordService.getLeaveCupboardDays(voMap);

        //往台账数据中赋离柜日期
        list.stream().forEach(file->{
            dataList.stream().forEach(data->{
                if(file.getId().equals(data.get("fileId".toUpperCase()))){
                    String selDays = file.getLeaveCupboardDays();
                    BigDecimal bd = new BigDecimal(String.valueOf(data.get("days".toUpperCase())));
                    String dayStr = bd.toString();
                    if(StringUtils.isEmpty(selDays) ||
                            Float.valueOf(selDays) < Float.parseFloat(dayStr)){
                        if(dayStr.indexOf(".") > -1){
                            file.setLeaveCupboardDays(dayStr.substring(0,dayStr.indexOf(".")));
                        }else{
                            file.setLeaveCupboardDays(dayStr);
                        }
                    }
                }
            });
        });

        return getDataTable(list);
    }

    /**
     * 新增文件收文登记记录
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return
     */
    @RequiresPermissions("bank:receive:add")
    @Log(title = "台账管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    @ResponseBody
    @ApiOperation(value = "新增文件收文登记记录")
    @ApiParam(name = "bankReceiveFiles", required = true)
    public AjaxResult add(BankReceiveFiles bankReceiveFiles) {
        String uuid = UuidUtils.getUUIDString();
        bankReceiveFiles.setCreateBy(ShiroUtils.getUserId());
        if ("".equals(bankReceiveFiles.getId()) || bankReceiveFiles.getId() == null) {
            bankReceiveFiles.setId(uuid);
        }
        int flag = bankReceiveFilesService.insertBankReceiveFiles(bankReceiveFiles);
        if (flag > 0) {
            return success(uuid);
        } else {
            return error();
        }
    }

    /**
     * 修改文件收文登记记录
     *
     * @param resultBean 文件收文登记实体
     * @return
     */
    @RequiresPermissions("bank:receive:edit")
    @Log(title = "台账管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@RequestBody ResultBean resultBean) {
        BankReceiveFiles bankReceiveFiles = resultBean.getBankReceiveFiles();
        List<BankFileSignOpinion> bankFileSignOpinions = resultBean.getBankFileSignOpinions();

//        办公室主任意见
        BankFileSignOpinion bankFileSignOpinion2 = new BankFileSignOpinion();
        bankFileSignOpinion2.setId(resultBean.getDirectorOpinionId());
        bankFileSignOpinion2.setOpinion(StringUtils.isEmpty(resultBean.getDirectorOpinion()) ? "" : resultBean.getDirectorOpinion());
        bankFileSignOpinion2.setLeaderName(StringUtils.isEmpty(resultBean.getDirectorOpinionUser()) ? "" : resultBean.getDirectorOpinionUser());
        bankFileSignOpinion2.setOpinionTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",
                StringUtils.isEmpty(resultBean.getDirectorOpinionTime()) ? "" : resultBean.getDirectorOpinionTime())) ;
        if (StringUtils.isEmpty(resultBean.getDirectorOpinionId())) {
            bankFileSignOpinion2.setOpinionType("2");
        }
        bankFileSignOpinions.add(bankFileSignOpinion2);

//        承办部门办理情况
        BankFileSignOpinion bankFileSignOpinion3 = new BankFileSignOpinion();
        bankFileSignOpinion3.setId(resultBean.getHostOpinionId());
        bankFileSignOpinion3.setOpinion(StringUtils.isEmpty(resultBean.getHostOpinion()) ? "" : resultBean.getHostOpinion());
        bankFileSignOpinion3.setLeaderName(StringUtils.isEmpty(resultBean.getHostOpinionUser()) ? "" : resultBean.getHostOpinionUser());
        bankFileSignOpinion3.setOpinionTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",
                StringUtils.isEmpty(resultBean.getHostOpinionTime()) ? "" : resultBean.getHostOpinionTime()));
        if (StringUtils.isEmpty(resultBean.getHostOpinionId())) {
            bankFileSignOpinion3.setOpinionType("3");
        }
        bankFileSignOpinions.add(bankFileSignOpinion3);

        return toAjax(bankReceiveFilesService.updateBankReceiveFiles(bankReceiveFiles, bankFileSignOpinions));
    }

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return
     */
    @RequiresPermissions("bank:receive:remove")
    @Log(title = "台账管理", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int count = bankReceiveFilesService.deleteBankReceiveFilesByIds(ids);
        if (count > 0) {
            return toAjax(count);
        } else {
            return error("文件已入柜或正在使用，不可删除！");
        }
    }

    /**
     * 登记确认
     *
     * @param ids 待确认id字符串
     * @return
     */
    @RequiresPermissions("bank:receive:confirm")
    @Log(title = "台账管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/confirm/{ids}")
    @ResponseBody
    public AjaxResult confirm(@PathVariable("ids") String ids) {
        return toAjax(bankReceiveFilesService.confirmFileByIds(ids, ShiroUtils.getUserId()));
    }

    @RequestMapping(value = "/print")
    public String print(String id, ModelMap modelMap) {
        BankReceiveFiles bankReceiveFiles =
                bankReceiveFilesService.selectBankReceiveFilesById(id);
        modelMap.put("title", bankReceiveFiles.getTitle());
        modelMap.put("createTime", bankReceiveFiles.getCreateTime());
        modelMap.put("deptName", bankReceiveFiles.getDeptName());
        modelMap.put("registrationNum", bankReceiveFiles.getRegistrationNum());
        modelMap.put("urgency", dictService.getLabel("urgency", bankReceiveFiles.getUrgency()));
        modelMap.put("secretLevel", dictService.getLabel("secrecy_level", bankReceiveFiles.getSecretLevel()));
        modelMap.put("numOfCopies", bankReceiveFiles.getNumOfCopies());
        modelMap.put("documentNum", bankReceiveFiles.getDocumentNum());
        modelMap.put("handleTime", bankReceiveFiles.getHandleTime());
        modelMap.put("communicationUnit", bankReceiveFiles.getCommunicationUnit());
        modelMap.put("jointUnit", bankReceiveFiles.getJointUnit());
        modelMap.put("contact", bankReceiveFiles.getContact());
        modelMap.put("phone", bankReceiveFiles.getPhone());
        modelMap.put("remark", bankReceiveFiles.getRemark());
        BankFileSignOpinion bankFileSignOpinion = new BankFileSignOpinion();
        //行领导意见
        bankFileSignOpinion.setOpinionType("1");
        bankFileSignOpinion.setRegistrationNum(bankReceiveFiles.getRegistrationNum());
        List<BankFileSignOpinion> bankFileSignOpinions = bankFileSignOpinionService.selectBankFileSignOpinionList(bankFileSignOpinion);
        String leader1Opinion = "";
        for (BankFileSignOpinion bankFileSignOpinion1 : bankFileSignOpinions) {
//            leaderOpinion += (bankFileSignOpinion1.getLeaderName() == null ?
//                    "" : bankFileSignOpinion1.getOpinion() + "&nbsp;&nbsp;&nbsp;&nbsp;" + bankFileSignOpinion1.getLeaderName()) + "&nbsp;&nbsp;"
//                    + bankFileSignOpinion1.getOpinionTime() + "<br/>";
            leader1Opinion += (bankFileSignOpinion1.getLeaderName() == null ?
                    "" : bankFileSignOpinion1.getOpinion() + "&nbsp;&nbsp;&nbsp;&nbsp;" + bankFileSignOpinion1.getLeaderName()) + "&nbsp;&nbsp;"
                    + DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",bankFileSignOpinion1.getOpinionTime()) + "<br/>";
        }
        modelMap.put("leaderOpinion", leader1Opinion);

    //办公室主任意见
        bankFileSignOpinion.setOpinionType("2");
    List<BankFileSignOpinion> bankFileSignOpinionClass = bankFileSignOpinionService.selectBankFileSignOpinionList(bankFileSignOpinion);
        if (bankFileSignOpinionClass != null && bankFileSignOpinionClass.size() > 0) {
        modelMap.put("classOpinion", bankFileSignOpinionClass.get(0) != null ? bankFileSignOpinionClass.get(0).getOpinion() : "");
    } else {
        modelMap.put("classOpinion", "");
    }

        //承办部门意见
        bankFileSignOpinion.setOpinionType("3");
        List<BankFileSignOpinion> bankFileSignOpinionR = bankFileSignOpinionService.selectBankFileSignOpinionList(bankFileSignOpinion);
        if (bankFileSignOpinionR != null && bankFileSignOpinionR.size() > 0) {
            modelMap.put("rOpinion", bankFileSignOpinionR.get(0) != null ? bankFileSignOpinionR.get(0).getOpinion() : "");
        } else {
            modelMap.put("rOpinion", "");
        }

        //协办部门意见
        bankFileSignOpinion.setOpinionType("4");
        List<BankFileSignOpinion> bankFileSignOpinionH = bankFileSignOpinionService.selectBankFileSignOpinionList(bankFileSignOpinion);
        String leader4Opinion = "";
        for (BankFileSignOpinion bankFileSignOpinion4 : bankFileSignOpinionH) {
            leader4Opinion += (bankFileSignOpinion4.getLeaderName() == null ?
                    "" : bankFileSignOpinion4.getOpinion() + "&nbsp;&nbsp;&nbsp;&nbsp;" + bankFileSignOpinion4.getLeaderName()) + "&nbsp;&nbsp;"
                    + DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",bankFileSignOpinion4.getOpinionTime()) + "<br/>";
        }
//        if (bankFileSignOpinionH != null && bankFileSignOpinionH.size() > 0) {
//            modelMap.put("hOpinion", bankFileSignOpinionH.get(0) != null ? bankFileSignOpinionH.get(0).getOpinion() : "");
//        } else {
//            modelMap.put("hOpinion", "");
//        }
        modelMap.put("hOpinion", leader4Opinion);
        return PREFIX + "print";
    }

    @RequestMapping("/selectByIds")
    @ResponseBody
    public AjaxResult selectByIds(String ids) {
        AjaxResult ajaxResult = new AjaxResult();
        List<BankReceiveFilesDetailVO> bankReceiveFilesDetails = bankFileDetailService.selectBankFileDetailByFileIds(ids);
        ajaxResult.put("data", bankReceiveFilesDetails);
        return ajaxResult;
    }

    @RequestMapping("/getMaxRegistrationNum")
    @ResponseBody
    public AjaxResult getMaxRegistrationNum(String ids) {
        AjaxResult ajaxResult = new AjaxResult();
        String registrationNum = bankReceiveFilesService.getMaxRegistrationNum();
//        String year = DateUtils.getDate().substring(0,4);
        ajaxResult.put("registrationNum", registrationNum);
        return ajaxResult;
    }

    /**
     * 校验登记号是否唯一
     *
     * @param bankReceiveFiles 台账信息
     * @return 结果
     */
    @RequestMapping("/checkRegistrationNumUnique")
    @ResponseBody
    public AjaxResult checkRegistrationNumUnique(BankReceiveFiles bankReceiveFiles) {
        AjaxResult ajaxResult = new AjaxResult();
        String flag = bankReceiveFilesService.checkRegistrationNumUnique(bankReceiveFiles);
        ajaxResult.put("flag",flag);
        if("1".equals(flag)){
            String registrationNum = bankReceiveFilesService.getMaxRegistrationNum();
            String year = DateUtils.getDate().substring(0,4);
            ajaxResult.put("registrationNum",year+registrationNum);
        }
        return ajaxResult;
    }

    @RequestMapping("/printTag")
    @ResponseBody
    public AjaxResult printTag(String data) {
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("barcodeList");
        for (int i = 0; i < jsonArray.size(); i++) {
            com.alibaba.fastjson.JSONObject jsonObjectTmp = jsonArray.getJSONObject(i);
            String rfid = jsonObjectTmp.getString("rfid");
            String title = jsonObjectTmp.getString("title");
            String secretLevel = jsonObjectTmp.getString("secretLevel");
            String flowId = jsonObjectTmp.getString("flowId");
            LibraryBSDPrinterInterface.INSTANCE.OpenPort(255);
            LibraryBSDPrinterInterface.INSTANCE.PTK_PcxGraphicsDel("*");
            LibraryBSDPrinterInterface.INSTANCE.PTK_ClearBuffer();
            LibraryBSDPrinterInterface.INSTANCE.PTK_SetDirection('B');
            LibraryBSDPrinterInterface.INSTANCE.PTK_SetPrintSpeed(4);
            LibraryBSDPrinterInterface.INSTANCE.PTK_SetDarkness(20);
            LibraryBSDPrinterInterface.INSTANCE.PTK_SetLabelHeight(260, 24);
            LibraryBSDPrinterInterface.INSTANCE.PTK_SetLabelWidth(600);

            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawTextTrueTypeW
                    (100, 0, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "农业发展银行", 1);

            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawTextTrueTypeW
                    (100, 60, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "RFID编号:" + rfid, 2);

            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawTextTrueTypeW
                    (100, 120, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "密级:" + secretLevel, 3);

            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawTextTrueTypeW
                    (100, 190, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "文件编号:" + flowId, 4);

            String sa = rfid + ";" + flowId + ";" + title;
            String finaStr = "";
            if (sa.getBytes().length > 85) {
                finaStr = sa.substring(0, 25) + title.substring(0, 20);
                if (finaStr.getBytes().length != 85) {
                    for (int j = 0; j < 85 - finaStr.getBytes().length; j++) {
                        finaStr += " ";
                    }
                }
            } else {
                finaStr = sa;
                for (int j = 0; j < 85 - sa.getBytes().length; j++) {
                    finaStr += " ";
                }
            }


            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawBar2D_QR(470, 0, 0, 8, 1, 5, 0, 0, 0, finaStr);

            LibraryBSDPrinterInterface.INSTANCE.PTK_WriteRFID(2, 1, 16, 0, rfid + "0000");
            LibraryBSDPrinterInterface.INSTANCE.PTK_PrintLabel(1, 1);
        }
        return success();
    }

    @RequestMapping("/encodingToGBK")
    @ResponseBody
    public AjaxResult encodingToGBK(String rfid, String flowId, String title, String secretLevel, String documentNum, String urgency, String handleTime, String communicationUnit)
            throws UnsupportedEncodingException {
        AjaxResult ajaxResult = new AjaxResult();
        String sa = "GB"+ rfid + ";" + flowId + ";" + title;
        StringBuilder finaStr = new StringBuilder();
        finaStr.append("GB").append(rfid).append(";");
        finaStr.append(flowId).append(";");
        finaStr.append(title).append(" ; ");
        finaStr.append(secretLevel).append(" ; ");
        finaStr.append(documentNum).append(" ; ");
        finaStr.append(urgency).append(" ; ");
        finaStr.append(handleTime).append(" ; ");
        finaStr.append(communicationUnit).append(" ; ");
//        if (sa.getBytes().length > 85) {
//            finaStr = sa.substring(0, 25) + title.substring(0, 20);
//            if (finaStr.getBytes().length != 85) {
//                for (int j = 0; j < 85 - finaStr.getBytes().length; j++) {
//                    finaStr += " ";
//                }
//            }
//        } else {
//            finaStr = sa;
//            for (int j = 0; j < 85 - sa.getBytes().length; j++) {
//                finaStr += " ";
//            }
//        }

//        byte[] bytes = finaStr.toString().getBytes();
//        return success().put("finaStr", new String(bytes, "GBK"));

        return success().put("finaStr", finaStr);
    }

}
