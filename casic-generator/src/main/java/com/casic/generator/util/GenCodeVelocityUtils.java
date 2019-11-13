package com.casic.generator.util;

import com.casic.common.config.Global;
import com.casic.common.constant.Constants;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.generator.domain.gen.ColumnInfo;
import com.casic.generator.domain.gen.ParentTableInfo;
import com.casic.generator.domain.gen.TableInfo;
import com.casic.generator.domain.gen.TreeTableInfo;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class GenCodeVelocityUtils {
	
	private static Logger logger = LoggerFactory.getLogger(GenCodeVelocityUtils.class);
	
	/** 类型转换 */
    public static Map<String, String> javaTypeMap = new HashMap<String, String>();
	
    static
    {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("time", "Date");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
    }
    
	/**
     * 设置普通列信息
     * @param columns 列集合
     * @return List<ColumnInfo>
     */
    public static List<ColumnInfo> transColums(List<ColumnInfo> columns)
    {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<ColumnInfo>();
        for (ColumnInfo column : columns)
        {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }
    
    /**
     * 设置主键或者父id信息
     * @param column
     * @return ColumnInfo
     */
    public static ColumnInfo transColum(ColumnInfo column)
    {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            return column;
    }
    
    /**
     * 设置集合列或者引用类型属性信息
     * @param column
     * @return ColumnInfo
     */
    public static ColumnInfo transCollectionColum(ColumnInfo column)
    {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));
            //设置集合泛型
            column.setAttrType(column.getAttrType());
            //设置集合类型 目前写死 为 List
            column.setDataType(column.getDataType());
            return column;
    }

    /**
     * 获取模板信息 单表
     * @param table
     * @param packageName
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table,String packageName,String funcName)
    {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", funcName);
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        //velocityContext.put("author", Global.getAuthor());
        velocityContext.put("author","Tom");
        velocityContext.put("datetime", DateUtils.getDate());
        return velocityContext;
    }
    
    /**
     * 获取模板信息 父子表（一父一子  一父多子）
     * @param table 树形表对象
     * @param packageName 包名
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(ParentTableInfo table,String packageName,String funType,String funcName)
    {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        if(table.getChildTable() != null && "mcurd".equals(funType)) {
        		velocityContext.put("childTable", table.getChildTable());
        }
        if(table.getChildTableList() != null && table.getChildTableList().size() > 0 && "mmcurd".equals(funType)) {
        		velocityContext.put("childTableList", table.getChildTableList());
        }
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", funcName);
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        //velocityContext.put("author", Global.getAuthor());
        velocityContext.put("author", "Tom");
        velocityContext.put("datetime", DateUtils.getDate());
        return velocityContext;
    }
    
    /**
     * 获取模板信息 树形表
     * @param table 树形表对象
     * @param packageName 包名
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TreeTableInfo table,String packageName,String funcName)
    {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
    		velocityContext.put("parentId", table.getParentId());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", funcName);
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        //velocityContext.put("author", Global.getAuthor());
        velocityContext.put("author", "Tom");
        velocityContext.put("datetime", DateUtils.getDate());
        return velocityContext;
    }
    

    /**
     * 获取模板信息
     * @param funType 功能类型
     * @return 模板列表
     */
    public static List<String> getTemplates(String funType)
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/"+funType+"/java/domain.java.vm");
        templates.add("vm/"+funType+"/java/Mapper.java.vm");
        templates.add("vm/"+funType+"/java/Service.java.vm");
        templates.add("vm/"+funType+"/java/ServiceImpl.java.vm");
        templates.add("vm/"+funType+"/java/Controller.java.vm");
        templates.add("vm/"+funType+"/xml/Mapper.xml.vm");
        templates.add("vm/"+funType+"/html/list.html.vm");
        templates.add("vm/"+funType+"/html/add.html.vm");
        templates.add("vm/"+funType+"/html/edit.html.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     * @param 表名称
     */
    public static String tableToJava(String tableName)
    {
        if (Constants.AUTO_REOMVE_PRE.equals(Global.getAutoRemovePre()))
        {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotEmpty(Global.getTablePrefix()))
        {
            tableName = tableName.replace(Global.getTablePrefix(), "");
        }
        return StringUtils.convertToCamelCase(tableName);
    }
    
    /**
     * 获取生成文件的存储路径并根据路径创建文件夹
     * @param moduleName 模块名称
     * @param packageFilePathName 包路径
     * @param componentName 组件名称
     * @param dir
     * @return pathMap 文件路径集合
     */
    public static Map<String, String> getRootFilePath(String packageFilePathName, String componentName, String funName, String dir) {
    	
     //   String rootPath = System.getProperty("user.dir") + File.separator + "casic-system-manager-sso" + File.separator + "casic-oauth2-client-";
        String rootPath = dir + File.separator + "casic-oauth2-client-";
        String javaPath = rootPath+componentName+"/src/main/java/"+packageFilePathName+"/";
        String resourcePath = rootPath+componentName+"/src/main/resources/";
        
    		List<String> filePathList = new ArrayList<String>();
    		filePathList.add(javaPath+"domain/"+funName);
    		filePathList.add(javaPath+"mapper/"+funName);
    		filePathList.add(javaPath+"service/"+funName);
    		filePathList.add(javaPath+"service/impl/"+funName);
    		filePathList.add(javaPath+"controller/"+funName);
    		filePathList.add(resourcePath+"templates/"+funName);
    		filePathList.add(resourcePath+"mapper/"+funName);
    		for (String filePath : filePathList) {
    			File rootFilePath = new File(filePath);  
    	        if ( !rootFilePath.exists()){//若此目录不存在，则创建之  
    	        		boolean flag = rootFilePath.mkdirs();
    	        		if(flag) {
    	        			logger.info("=================文件目录创建成功！===============");
    	        		}else {
    	        			logger.info("=================文件目录创建失败！===============");
    	        		}
    	        }
		}
    		
    		Map<String, String> pathMap = new HashMap<String, String>();
    		pathMap.put("javaPath", javaPath);
    		pathMap.put("resourcePath", resourcePath);
    		
        return pathMap;
        
    }
    

    /**
     * 
     * @param template 模版信息
     * @param className 大写类名
     * @param pathMap 生成代码路径 
     * @param moduleName 模块名称
     * @return
     */
    public static String getFileName(String template, String className, Map<String,String> pathMap,String funName)
    {
        //文件全名
        String qualifiedName = null;
        //java文件路径
        String javaPath = pathMap.get("javaPath");
        //resource文件路径
        String resourcePath = pathMap.get("resourcePath");
        
        if (template.contains("domain.java.vm"))
        {
         	qualifiedName = javaPath + "domain/" + funName + "/" + className + ".java";
        }

        if (template.contains("Mapper.java.vm"))
        {
        		qualifiedName = javaPath + "mapper/" + funName + "/" + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm"))
        {
        		qualifiedName = javaPath + "service/" + funName + "/" +  "I" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm"))
        {
        		qualifiedName = javaPath + "service" + "/impl/" +  funName + "/" +  className + "ServiceImpl.java";	
        }

        if (template.contains("Controller.java.vm"))
        {
        		qualifiedName = javaPath + "controller/" +  funName + "/" +  className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm"))
        {
        		qualifiedName = resourcePath + "Mapper/"  + funName +"/"+ className  + "Mapper.xml";
        }

        if (template.contains("list.html.vm"))
        {
        		qualifiedName = resourcePath + "templates/" + funName + "/" + className + "List.html";
        }
        if (template.contains("add.html.vm"))
        {
        		qualifiedName = resourcePath + "templates/" + funName + "/"+ className +"Add.html";
        }
        if (template.contains("edit.html.vm"))
        {
        		qualifiedName = resourcePath + "templates/" + funName + "/"+ className + "Edit.html";
        }
        
        if(qualifiedName != null && !("".equals(qualifiedName))) {
        		//创建文件
			try {
				File checkFile = new File(qualifiedName);
				if (!checkFile.exists()) {
					boolean filFlag =checkFile.createNewFile();
					if(filFlag) {
						logger.info("=================代码文件创建成功！===============");
		        		}else {
		        			logger.info("=================代码文件创建失败！===============");
		        		}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return qualifiedName;
    }
    
    /**
     * 获取模块名
     * 
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }
    
    /**
     * 根据包路径获取对应的文件路径
     * 
     * @param packageName 包名
     * @return String 包文件路径
     */
    public static String getPackageFilePathName(String packageName)
    {
        String packageFilePathName = StringUtils.replace(packageName, ".", "/");
        return packageFilePathName;
    }
    
    /**
     * 替换关键字
     * @param keyword
     * @return
     */
    public static String replaceKeyword(String keyword)
    {
        String keyName = keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }

    /**
     * 生成相应的模版文件
     * 
     * @param template 模版对象
     * @param ctx 引擎上下文对象
     * @param filePath 模版文件全路径
     */
	public static void merge(Template template, VelocityContext ctx, String filePath) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"));
			template.merge(ctx, writer);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	/**
	 * 初始化模版引擎
	 * 
	 * @return VelocityEngine 
	 */
	public static VelocityEngine initVelocityEngine() {
		//1、初始化模版引擎
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
	    
	    Properties prop = new Properties();
	    
	    // 定义字符集
	    prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
	    prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
	    prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
	    
	    ve.init(prop);
	    
	    return ve;
	}
	
    
	/**
	 * 生成(父子或者一父多子)模版文件入口
	 * @param parentTable 父子类型对象
	 * @param packageName 包路径
	 * @param funType 功能类型
	 */
    public static void templateGenerator(ParentTableInfo parentTable,String packageName,String funType,String componentName,String funName, String dir) {
    		
    		//1、初始化模版引擎
    		VelocityEngine ve = initVelocityEngine();
    	
        //2、获取模版对象
        List<String> templateList = null;
        if(funType != null && "mcurd".equals(funType)){
        		templateList = getTemplates("mcurd");
        }else if(funType != null && "mmcurd".equals(funType)){
        		templateList = getTemplates("mmcurd");
        }else {
        		return;
        }
        
        //3、获取生成文件存储路径
        //String moduleName = getModuleName(packageName);
        String packageFilePathName = getPackageFilePathName(packageName);
        Map<String, String> pathMap = getRootFilePath(packageFilePathName,componentName,funName, dir);
        
        Template template = null;
        for (String tempObj : templateList) {
        		//4、设置模版参数信息
        		template = ve.getTemplate(tempObj);
        		//5、获取模版上下文
        		VelocityContext ctx1 = getVelocityContext(parentTable,packageName,funType,funName);
        		//6、获取生成文件路径
        		String filePath1 = getFileName(tempObj, parentTable.getClassName(),pathMap,funName);
        		if(filePath1 != null && !("".equals(filePath1))) {
        			//7、输出根据模版生成的文件
        			 merge(template, ctx1, filePath1);
        			
        		}
        			 
    		}
        
        if(parentTable.getChildTable() != null) {
        		templateGenerator(parentTable.getChildTable(),packageName,componentName,funName, dir);
		}
		if(parentTable.getChildTableList() != null && parentTable.getChildTableList().size() > 0) {
			List<TableInfo> childTableList = parentTable.getChildTableList();
			for (TableInfo tableInfo : childTableList) {
				templateGenerator(tableInfo,packageName,componentName,funName, dir);
			}
		}
			
        
    }
    
    /**
	 * 生成单表模版文件入口
	 * @param table 单表类型对象
	 * @param packageName 包路径
	 * @param componentName 组件名称
	 */
    public static void templateGenerator(TableInfo table,String packageName,String componentName,String funName, String dir) {
    		
    		//1、初始化模版引擎
    		VelocityEngine ve = initVelocityEngine();
    	
        //2、获取模版对象
        List<String> templateList = getTemplates("scurd");
        
        //3、获取生成文件存储路径
        //String moduleName = getModuleName(packageName);
        String packageFilePathName = getPackageFilePathName(packageName);
        Map<String, String> pathMap = getRootFilePath(packageFilePathName,componentName,funName, dir);
        
        Template template = null;
        VelocityContext ctx = null;
        for (String tempObj : templateList) {
        		//4、设置模版参数信息
        		template = ve.getTemplate(tempObj);
        		ctx = getVelocityContext(table,packageName,funName);
        		//5、输出根据模版生成的文件
        		String filePath = getFileName(tempObj, table.getClassName(),pathMap,funName);
        		if(filePath != null && !("".equals(filePath))) {
        			 merge(template, ctx, filePath);
        			
        		}
        		
		}
        
    }
    
    /**
	 * 生成（树形）模版文件入口
	 * @param treeTable 树形类型对象
	 * @param packageName 包路径
	 * @param componentName 组件名称
	 */
    public static void templateGenerator(TreeTableInfo treeTable,String packageName,String componentName,String funName, String dir) {
    		
    		//1、初始化模版引擎
    		VelocityEngine ve = initVelocityEngine();
    	
        //2、获取模版对象
        List<String> templateList = getTemplates("treecurd");
        
        //3、获取生成文件存储路径
        //String moduleName = getModuleName(packageName);
        String packageFilePathName = getPackageFilePathName(packageName);
        Map<String, String> pathMap = getRootFilePath(packageFilePathName,componentName,funName, dir);
        
        Template template = null;
        VelocityContext ctx = null;
        for (String tempObj : templateList) {
        		//4、设置模版参数信息
        		template = ve.getTemplate(tempObj);
        		ctx = getVelocityContext(treeTable,packageName,funName);
        		//5、输出根据模版生成的文件
        		String filePath = getFileName(tempObj, treeTable.getClassName(),pathMap,funName);
        		if(filePath != null && !("".equals(filePath))) {
        			 merge(template, ctx, filePath);
        			
        		}
        		
		}
        
    }
    
    /**
     * 一父一子
     * @param packageName 包路径
     * @param componentName 组件名称
     */
    public static void parentToChild(String packageName,String componentName,String funName) {
    	
    			ParentTableInfo parentTableInfo = new ParentTableInfo();
    			parentTableInfo.setTableName("tc_order");
    			parentTableInfo.setTableComment("订单表");
    			ColumnInfo tableColumnInfoPrimaryKey = new ColumnInfo();
    	        tableColumnInfoPrimaryKey.setAttrName("OrderId");
    	        tableColumnInfoPrimaryKey.setAttrname("orderId");
    	        tableColumnInfoPrimaryKey.setAttrType("String");
    	        tableColumnInfoPrimaryKey.setColumnComment("订单id");
    	        tableColumnInfoPrimaryKey.setColumnName("order_id");
    	        tableColumnInfoPrimaryKey.setDataType("VARCHAR");
    	        parentTableInfo.setPrimaryKey(tableColumnInfoPrimaryKey);
    	        parentTableInfo.setClassName("Order");
    	        parentTableInfo.setClassname("order");
    	        ArrayList<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
    	        ColumnInfo columnInfo = new ColumnInfo();
    	        columnInfo.setAttrName("OrderNO");
    	        columnInfo.setAttrname("orderNO");
    	        columnInfo.setAttrType("String");
    	        columnInfo.setColumnComment("订单编号");
    	        columnInfo.setColumnName("order_no");
    	        columnInfo.setDataType("VARCHAR");
    	        columnInfoList.add(columnInfo);
    	        
  	        parentTableInfo.setColumns(columnInfoList);
    	        
    	        
    	        TableInfo childTableInfo = new TableInfo();
    	        childTableInfo.setTableName("tc_invoice");
    	        childTableInfo.setTableComment("发票表");
    	        childTableInfo.setClassname("invoice");
    	        childTableInfo.setClassName("Invoice");
    	        ColumnInfo columnInfoPrimaryKey = new ColumnInfo();
    	        columnInfoPrimaryKey.setAttrName("InvoiceId");
    	        columnInfoPrimaryKey.setAttrname("invoiceId");
    	        columnInfoPrimaryKey.setAttrType("String");
    	        columnInfoPrimaryKey.setColumnComment("发票id");
    	        columnInfoPrimaryKey.setColumnName("invoice_id");
    	        columnInfoPrimaryKey.setDataType("VARCHAR");
    	        childTableInfo.setPrimaryKey(columnInfoPrimaryKey);
    	        ArrayList<ColumnInfo> childTableColumnInfoList = new ArrayList<ColumnInfo>();
    	        ColumnInfo childTableColumnInfo = new ColumnInfo();
    	        childTableColumnInfo.setAttrName("InvoiceNO");
    	        childTableColumnInfo.setAttrname("invoiceNO");
    	        childTableColumnInfo.setAttrType("String");
    	        childTableColumnInfo.setColumnComment("发票编号");
    	        childTableColumnInfo.setColumnName("invoice_no");
    	        childTableColumnInfo.setDataType("AVRCHAR");
    	        childTableColumnInfoList.add(childTableColumnInfo);
    	        childTableInfo.setColumns(childTableColumnInfoList);
    	        parentTableInfo.setChildTable(childTableInfo);
    	        
    	        String funType = "mcurd";
    	        String dir = "D:";
    			templateGenerator(parentTableInfo,packageName,funType, componentName,funName, dir);
    }
    
    /**
     * 一父多子
     * @param packageName
     * @param componentName 组件名称
     */
    public 	static void parentToChildList(String packageName,String componentName,String funName) {
    	
    		ParentTableInfo parentTableInfo = new ParentTableInfo();
		parentTableInfo.setTableName("mc_user");
		parentTableInfo.setTableComment("用户表");
		ColumnInfo tableColumnInfoPrimaryKey = new ColumnInfo();
        tableColumnInfoPrimaryKey.setAttrName("UserId");
        tableColumnInfoPrimaryKey.setAttrname("userId");
        tableColumnInfoPrimaryKey.setAttrType("String");
        tableColumnInfoPrimaryKey.setColumnComment("用户id");
        tableColumnInfoPrimaryKey.setColumnName("user_id");
        tableColumnInfoPrimaryKey.setDataType("VARCHAR");
        parentTableInfo.setPrimaryKey(tableColumnInfoPrimaryKey);
        parentTableInfo.setClassName("User");
        parentTableInfo.setClassname("user");
        ArrayList<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setAttrName("userName");
        columnInfo.setAttrname("username");
        columnInfo.setAttrType("String");
        columnInfo.setColumnComment("用户名称");
        columnInfo.setColumnName("user_name");
        columnInfo.setDataType("VARCHAR");
        columnInfoList.add(columnInfo);
        
        parentTableInfo.setColumns(columnInfoList);
        
        List<TableInfo> childList = new ArrayList<TableInfo>();
        
        TableInfo childTableInfo = new TableInfo();
        childTableInfo.setTableName("tc_invoice");
        childTableInfo.setTableComment("发票");
        childTableInfo.setClassname("invoiceVO");
        childTableInfo.setClassName("InvoiceVO");
        ColumnInfo columnInfoPrimaryKey = new ColumnInfo();
        columnInfoPrimaryKey.setAttrName("InvoiceId");
        columnInfoPrimaryKey.setAttrname("invoiceId");
        columnInfoPrimaryKey.setAttrType("String");
        columnInfoPrimaryKey.setColumnComment("发票id");
        columnInfoPrimaryKey.setColumnName("invoice_id");
        columnInfoPrimaryKey.setDataType("VARCHAR");
        childTableInfo.setPrimaryKey(columnInfoPrimaryKey);
        ArrayList<ColumnInfo> childTableColumnInfoList = new ArrayList<ColumnInfo>();
        ColumnInfo childTableColumnInfo = new ColumnInfo();
        childTableColumnInfo.setAttrName("InvoiceNO");
        childTableColumnInfo.setAttrname("invoiceNO");
        childTableColumnInfo.setAttrType("String");
        childTableColumnInfo.setColumnComment("发票编号");
        childTableColumnInfo.setColumnName("invoice_no");
        childTableColumnInfo.setDataType("AVRCHAR");
        childTableColumnInfoList.add(childTableColumnInfo);
        childTableInfo.setColumns(childTableColumnInfoList);
        childList.add(childTableInfo);
        
        TableInfo childTableInfo1 = new TableInfo();
        childTableInfo1.setTableName("tc_order");
        childTableInfo1.setTableComment("订单");
		ColumnInfo tableColumnInfoPrimaryKey1 = new ColumnInfo();
        tableColumnInfoPrimaryKey1.setAttrName("OrderId");
        tableColumnInfoPrimaryKey1.setAttrname("orderId");
        tableColumnInfoPrimaryKey1.setAttrType("String");
        tableColumnInfoPrimaryKey1.setColumnComment("订单id");
        tableColumnInfoPrimaryKey1.setColumnName("order_id");
        tableColumnInfoPrimaryKey1.setDataType("VARCHAR");
        childTableInfo1.setPrimaryKey(tableColumnInfoPrimaryKey1);
        childTableInfo1.setClassName("Order");
        childTableInfo1.setClassname("order");
        ArrayList<ColumnInfo> columnInfoList1 = new ArrayList<ColumnInfo>();
        ColumnInfo columnInfo1 = new ColumnInfo();
        columnInfo1.setAttrName("orderNO");
        columnInfo1.setAttrname("orderNO");
        columnInfo1.setAttrType("String");
        columnInfo1.setColumnComment("订单编号");
        columnInfo1.setColumnName("order_no");
        columnInfo1.setDataType("VARCHAR");
        columnInfoList1.add(columnInfo1);
        childTableInfo1.setColumns(columnInfoList1);
        
        childList.add(childTableInfo1);
        parentTableInfo.setChildTableList(childList);
        
        String funType = "mmcurd";
        String dir = "D:";
		templateGenerator(parentTableInfo,packageName,funType,componentName,funName, dir);
    }
    
    /**
     * 单表类型入口
     * @param packageName
     * @param componentName 组件名称
     */
    public static void oneTable(String packageName,String componentName,String funName) {
    		
    		TableInfo parentTableInfo = new TableInfo();
		parentTableInfo.setTableName("mc_user");
		parentTableInfo.setTableComment("用户表");
		ColumnInfo tableColumnInfoPrimaryKey = new ColumnInfo();
        tableColumnInfoPrimaryKey.setAttrName("UserId");
        tableColumnInfoPrimaryKey.setAttrname("userId");
        tableColumnInfoPrimaryKey.setAttrType("String");
        tableColumnInfoPrimaryKey.setColumnComment("用户id");
        tableColumnInfoPrimaryKey.setColumnName("user_id");
        tableColumnInfoPrimaryKey.setDataType("VARCHAR");
        parentTableInfo.setPrimaryKey(tableColumnInfoPrimaryKey);
        parentTableInfo.setClassName("User");
        parentTableInfo.setClassname("user");
        ArrayList<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setAttrName("UserName");
        columnInfo.setAttrname("userName");
        columnInfo.setAttrType("String");
        columnInfo.setColumnComment("用户名称");
        columnInfo.setColumnName("user_name");
        columnInfo.setDataType("VARCHAR");
        columnInfo.setIsInsert("1");
        columnInfo.setIsEdit("1");
        columnInfo.setIsList("1");
        columnInfo.setIsQuery("1");
        columnInfo.setShowType("text");
        columnInfoList.add(columnInfo);
        
        
        ColumnInfo columnInfo1 = new ColumnInfo();
        columnInfo1.setAttrName("UserAccount");
        columnInfo1.setAttrname("userAccount");
        columnInfo1.setAttrType("String");
        columnInfo1.setColumnComment("用户账号");
        columnInfo1.setColumnName("user_account");
        columnInfo1.setDataType("VARCHAR");
        columnInfo1.setIsInsert("1");
        columnInfo1.setIsEdit("0");
        columnInfo1.setIsList("1");
        columnInfo1.setIsQuery("0");
        columnInfo1.setShowType("text");
        columnInfoList.add(columnInfo1);
        
        ColumnInfo columnInfo2 = new ColumnInfo();
        columnInfo2.setAttrName("UserStatus");
        columnInfo2.setAttrname("userStatus");
        columnInfo2.setAttrType("Integer");
        columnInfo2.setColumnComment("用户状态");
        columnInfo2.setColumnName("user_status");
        columnInfo2.setDataType("int");
        columnInfo2.setIsInsert("1");
        columnInfo2.setIsEdit("0");
        columnInfo2.setIsList("0");
        columnInfo2.setIsQuery("1");
        columnInfo2.setShowType("select");
        columnInfoList.add(columnInfo2);
        
        ColumnInfo columnInfo3 = new ColumnInfo();
        columnInfo3.setAttrName("CreateTime");
        columnInfo3.setAttrname("createTime");
        columnInfo3.setAttrType("Date");
        columnInfo3.setColumnComment("创建时间");
        columnInfo3.setColumnName("create_time");
        columnInfo3.setDataType("datetime");
        columnInfo3.setIsInsert("0");
        columnInfo3.setIsEdit("0");
        columnInfo3.setIsList("1");
        columnInfo3.setIsQuery("1");
        columnInfo3.setShowType("dateselect");
        columnInfoList.add(columnInfo3);
        
        ColumnInfo columnInfo4 = new ColumnInfo();
        columnInfo4.setAttrName("RoleType");
        columnInfo4.setAttrname("roleType");
        columnInfo4.setAttrType("Byte");
        columnInfo4.setColumnComment("创建时间");
        columnInfo4.setColumnName("create_time");
        columnInfo4.setDataType("tinyint");
        columnInfo4.setIsInsert("1");
        columnInfo4.setIsEdit("1");
        columnInfo4.setIsList("1");
        columnInfo4.setIsQuery("1");
        columnInfo4.setShowType("radiobox");
        columnInfoList.add(columnInfo3);
        
        parentTableInfo.setColumns(columnInfoList);
        String dir = "D:";
        templateGenerator(parentTableInfo,packageName,componentName, funName, dir);
    }
    
    /**
     * 树形类型入口
     * @param packageName
     * @param componentName 组件名称
     */
    public static void treeTable(String packageName,String componentName,String funName) {
    	
    		TreeTableInfo treeTableInfo = new TreeTableInfo();
    		treeTableInfo.setTableName("cc_category");
    		treeTableInfo.setTableComment("商品分类表");
    		treeTableInfo.setClassName("Category");
    		treeTableInfo.setClassname("category");
    		ColumnInfo columnInfoPrimaryKey = new ColumnInfo();
        columnInfoPrimaryKey.setAttrName("CategoryId");
        columnInfoPrimaryKey.setAttrname("categoryId");
        columnInfoPrimaryKey.setAttrType("String");
        columnInfoPrimaryKey.setColumnComment("分类id");
        columnInfoPrimaryKey.setColumnName("category_id");
        columnInfoPrimaryKey.setDataType("VARCHAR");
        treeTableInfo.setPrimaryKey(columnInfoPrimaryKey);
        
        ColumnInfo columnInfoParentId = new ColumnInfo();
        columnInfoParentId.setAttrName("ParentId");
        columnInfoParentId.setAttrname("parentId");
        columnInfoParentId.setAttrType("String");
        columnInfoParentId.setColumnComment("分类父id");
        columnInfoParentId.setColumnName("parent_id");
        columnInfoParentId.setDataType("VARCHAR");
        treeTableInfo.setParentId(columnInfoParentId);
        
        
        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        
        ColumnInfo columnInf3 = new ColumnInfo();
        columnInf3.setAttrName("CategoryName");
        columnInf3.setAttrname("categoryName");
        columnInf3.setAttrType("String");
        columnInf3.setColumnComment("分类名称");
        columnInf3.setColumnName("category_name");
        columnInf3.setDataType("VARCHAR");
        columnInfoList.add(columnInf3);
        treeTableInfo.setColumns(columnInfoList);

        String dir = "D:";
        templateGenerator(treeTableInfo,packageName,componentName, funName, dir);
    		
    }
    
    public static void main(String[] args) {
    
    		String packageName = "com.casic.project";
    		
    		String componentName = "casic-order";
    		
    		String funName = "order";
    		
    		//GenUtils.getModuleName(packageName);
    		
    		//String packageFilePathName = getPackageFilePathName(packageName);
    		//一父一子
    		//parentToChild(packageName,componentName,funName);
    		//一父多子
    		//parentToChildList(packageName,componentName,funName);
    		
		//单表
    		//oneTable(packageName,componentName,funName);
    		
		//树形表
		treeTable(packageName,componentName,funName);

    }
	
}
