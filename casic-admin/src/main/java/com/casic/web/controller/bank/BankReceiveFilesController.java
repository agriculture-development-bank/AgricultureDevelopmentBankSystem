package com.casic.web.controller.bank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.casic.bank.domain.BankFileSignOpinion;
import com.casic.bank.domain.BankReceiveFiles;
import com.casic.bank.domain.ResultBean;
import com.casic.bank.domain.vo.BankEquipmentVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.service.BankEquipmentService;
import com.casic.bank.service.BankFileDetailService;
import com.casic.bank.service.BankFileSignOpinionService;
import com.casic.bank.service.BankReceiveFilesService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.config.Global;
import com.casic.common.enums.BusinessType;
import com.casic.common.json.JSONObject;
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

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    public BankReceiveFilesController(BankReceiveFilesService bankReceiveFilesService,
                                      BankFileDetailService bankFileDetailService,
                                      BankFileSignOpinionService bankFileSignOpinionService,
                                      ISysUserService userService,
                                      ISysDeptService sysDeptService,
                                      DictService dictService, BankEquipmentService bankEquipmentService) {
        this.bankReceiveFilesService = bankReceiveFilesService;
        this.bankFileDetailService = bankFileDetailService;
        this.bankFileSignOpinionService = bankFileSignOpinionService;
        this.userService = userService;
        this.sysDeptService = sysDeptService;
        this.dictService = dictService;
        this.bankEquipmentService = bankEquipmentService;
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
    public String index(ModelMap modelMap) {
        modelMap.put("sysVersion", Global.getConfig("casic.sysVersion"));
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
        unionSelect(modelMap);
        modelMap.put("sysVersion", Global.getConfig("casic.sysVersion"));
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
        List<BankFileSignOpinion> bankFileSignOpinions = bankFileSignOpinionService.selectBankFileSignOpinionByFileDetailId(id);
        Optional<BankFileSignOpinion> directorOpinion = bankFileSignOpinions.stream().filter(p -> "2".equals(p.getOpinionType())).findFirst();
        Optional<BankFileSignOpinion> hostOpinion = bankFileSignOpinions.stream().filter(p -> "3".equals(p.getOpinionType())).findFirst();
        Optional<BankFileSignOpinion> coOrganzierOpinion = bankFileSignOpinions.stream().filter(p -> "4".equals(p.getOpinionType())).findFirst();
        modelMap.addAttribute("bankReceiveFiles", bankReceiveFiles);
        modelMap.addAttribute("bankFileSignOpinions", bankFileSignOpinions);
        modelMap.addAttribute("directorOpinion", directorOpinion.isPresent() ? directorOpinion.get() : null);
        modelMap.addAttribute("hostOpinion", hostOpinion.isPresent() ? hostOpinion.get() : null);
        modelMap.addAttribute("coOrganzierOpinion", coOrganzierOpinion.isPresent() ? coOrganzierOpinion.get() : null);
        modelMap.addAttribute("fileId", id);
        modelMap.addAttribute("registrationNum", bankReceiveFiles.getRegistrationNum());
        List<BankEquipmentVO> bankEquipmentVOS = bankEquipmentService.selectBankEquipmentList(new BankEquipmentVO());
        modelMap.put("bankEquipments", bankEquipmentVOS);
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
        List<BankReceiveFiles> list = bankReceiveFilesService.selectBankReceiveFilesList(bankReceiveFiles);
        return getDataTable(list);
    }

    /**
     * 新增文件收文登记记录
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return
     */
    @RequiresPermissions("bank:receive:add")
    @Log(title = "新增文件收文登记记录", businessType = BusinessType.INSERT)
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
    @Log(title = "修改文件收文登记记录", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@RequestBody ResultBean resultBean) {
        BankReceiveFiles bankReceiveFiles = resultBean.getBankReceiveFiles();
        List<BankFileSignOpinion> bankFileSignOpinions = resultBean.getBankFileSignOpinions();
        BankFileSignOpinion bankFileSignOpinion;
        for (int i = 0; i < 3; i++) {
            bankFileSignOpinion = new BankFileSignOpinion();
            if (i == 0) {
                bankFileSignOpinion.setId(resultBean.getDirectorOpinionId());
                bankFileSignOpinion.setOpinion(StringUtils.isEmpty(resultBean.getDirectorOpinion()) ? "" : resultBean.getDirectorOpinion());
                if (StringUtils.isEmpty(resultBean.getDirectorOpinionId())) {
                    bankFileSignOpinion.setOpinionType("2");
                }
            } else if (i == 1) {
                bankFileSignOpinion.setId(resultBean.getHostOpinionId());
                bankFileSignOpinion.setOpinion(StringUtils.isEmpty(resultBean.getHostOpinion()) ? "" : resultBean.getHostOpinion());
                if (StringUtils.isEmpty(resultBean.getHostOpinionId())) {
                    bankFileSignOpinion.setOpinionType("3");
                }
            } else {
                bankFileSignOpinion.setId(resultBean.getCoOrganzierOpinionId());
                bankFileSignOpinion.setOpinion(StringUtils.isEmpty(resultBean.getCoOrganzierOpinion()) ? "" : resultBean.getCoOrganzierOpinion());
                if (StringUtils.isEmpty(resultBean.getCoOrganzierOpinionId())) {
                    bankFileSignOpinion.setOpinionType("4");
                }
            }
            bankFileSignOpinions.add(bankFileSignOpinion);
        }
        return toAjax(bankReceiveFilesService.updateBankReceiveFiles(bankReceiveFiles, bankFileSignOpinions));
    }

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return
     */
    @RequiresPermissions("bank:receive:remove")
    @Log(title = "删除文件登记记录", businessType = BusinessType.DELETE)
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
    @Log(title = "新增台账", businessType = BusinessType.INSERT)
    @PostMapping(value = "/confirm/{ids}")
    @ResponseBody
    public AjaxResult confirm(@PathVariable("ids") String ids) {
        return toAjax(bankReceiveFilesService.confirmFileByIds(ids, ShiroUtils.getUserId()));
    }

    /**
     * 校验登记号是否唯一
     *
     * @param bankReceiveFiles 台账信息
     * @return 结果
     */
    @PostMapping("/checkRegistrationNumUnique")
    @ResponseBody
    public String checkRegistrationNumUnique(BankReceiveFiles bankReceiveFiles) {
        return bankReceiveFilesService.checkRegistrationNumUnique(bankReceiveFiles);
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
        String leaderOpinion = "";
        for (BankFileSignOpinion bankFileSignOpinion1 : bankFileSignOpinions) {
            leaderOpinion += (bankFileSignOpinion1.getLeaderName() == null ? "" : bankFileSignOpinion1.getLeaderName()) + "批示：" + bankFileSignOpinion1.getOpinion() + "<br/>";
        }
        modelMap.put("leaderOpinion", leaderOpinion);

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
        if (bankFileSignOpinionH != null && bankFileSignOpinionH.size() > 0) {
            modelMap.put("hOpinion", bankFileSignOpinionH.get(0) != null ? bankFileSignOpinionH.get(0).getOpinion() : "");
        } else {
            modelMap.put("hOpinion", "");
        }
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

    @RequestMapping("/printTag")
    @ResponseBody
    public AjaxResult printTag(String data){
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("barcodeList");
        for(int i = 0; i<jsonArray.size();i++){
            com.alibaba.fastjson.JSONObject jsonObjectTmp = jsonArray.getJSONObject(i);
            String rfid = jsonObjectTmp.getString("rfid");
            String title = jsonObjectTmp.getString("title");
            String secretLevel = jsonObjectTmp.getString("secretLevel");
            String flowId= jsonObjectTmp.getString("flowId");
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
                    (100, 60, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "RFID编号:"+rfid, 2);

            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawTextTrueTypeW
                    (100, 120, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "密级:"+secretLevel, 3);

            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawTextTrueTypeW
                    (100, 190, 33, 0, 0, 0, 0, 0, 0, GlobalVar.BSDFontPath, "文件编号:"+flowId, 4);

            String sa = rfid + ";" + flowId + ";" + title;
            String finaStr = "";
            if(sa.getBytes().length>85){
                finaStr = sa.substring(0,25) + title.substring(0,20);
                if(finaStr.getBytes().length!=85){
                    for(int j = 0; j< 85-finaStr.getBytes().length; j++){
                        finaStr += " ";
                    }
                }
            }else{
                finaStr = sa;
                for(int j = 0; j< 85-sa.getBytes().length; j++){
                    finaStr += " ";
                }
            }


            LibraryBSDPrinterInterface.INSTANCE.PTK_DrawBar2D_QR(470,0,0,8,1,5,0,0,0,finaStr);

            LibraryBSDPrinterInterface.INSTANCE.PTK_WriteRFID(2,1,16,0,rfid+"0000");
            LibraryBSDPrinterInterface.INSTANCE.PTK_PrintLabel(1, 1);
        }
        return success();
    }

    @RequestMapping("/encodingToGBK")
    @ResponseBody
    public AjaxResult encodingToGBK(String rfid, String flowId, String title) throws UnsupportedEncodingException {
        AjaxResult ajaxResult = new AjaxResult();
        String sa = rfid + ";" + flowId + ";" + title;
        String finaStr = "";
        if(sa.getBytes().length>85){
            finaStr = sa.substring(0,25) + title.substring(0,20);
            if(finaStr.getBytes().length!=85){
                for(int j = 0; j< 85-finaStr.getBytes().length; j++){
                    finaStr += " ";
                }
            }
        }else{
            finaStr = sa;
            for(int j = 0; j< 85-sa.getBytes().length; j++){
                finaStr += " ";
            }
        }

        byte[] bytes = finaStr.getBytes();

        return success().put("finaStr",new String(bytes,"GBK"));
    }
}
