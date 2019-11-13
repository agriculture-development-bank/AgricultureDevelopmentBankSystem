package com.casic.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Java实现将文件或者文件夹压缩成zip的工具类
 */
public class ZipUtils {

    private static final int  BUFFER_SIZE = 4096 * 1024;

    /**
     * 压缩成ZIP
     * @param srcDir 压缩文件夹路径
     * @param out   压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构; false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 压缩成ZIP
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out  压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        } finally {
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile  源文件
     * @param zos  zip输出流
     * @param name  压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构; false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }


    /**
     * 获取下载zip文件流
     *
     * @param srcSource
     * @return
     * @throws Exception
     */
    public static byte[] createZip(String srcSource) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //将目标文件打包成zip导出
        File file = new File(srcSource);
        a(zip,file,"");
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();

    }

    public static void a(ZipOutputStream zip, File file, String dir) throws Exception {
        //如果当前的是文件夹，则进行进一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            zip.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir.length() == 0 ? "" : dir + "/";
            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                a(zip, files[i], dir + files[i].getName());         //递归处理
            }
        } else {   //当前的是文件，打包处理
            //文件输入流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(dir);
            zip.putNextEntry(entry);
            zip.write(FileUtils.readFileToByteArray(file));
            IOUtils.closeQuietly(bis);
            zip.flush();
            zip.closeEntry();
        }
    }

    public static void deleteFiles(File srcFile) {
        if (srcFile.exists()) {
            //存放文件夹
            LinkedList<File> directories = new LinkedList<>();
            ArrayList<File> directoryList = new ArrayList<>();
            if (srcFile.isDirectory()) {
                directories.add(srcFile);
            } else {
                srcFile.delete();
            }
            while (directories.size() > 0) {
                File[] files = directories.removeFirst().listFiles();

                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            directories.add(f);
                            directoryList.add(f);
                        } else {
                            f.delete();
                        }
                    }
                }
            }
            //这时所有非文件夹都删光了，可以直接删文件夹了(注意从后往前遍历)
            for (int j = directoryList.size() - 1; j >= 0; j--) {
                directoryList.get(j).delete();
            }
        }
    }


    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    public static void main(String[] args) throws Exception {

        /** 测试压缩方法1  */
        /*FileOutputStream fos1 = new FileOutputStream(new File("C:/Users/qh/Desktop/casic-oauth2-client-component1.zip"));
        ZipUtils.toZip("D:/project/706/dev_platform/casic-system-manager-sso/casic-oauth2-client-component1", fos1,true);
*/
        /** 测试压缩方法2  */
        /*List<File> fileList = new ArrayList<>();
        fileList.add(new File("D:/project/706/dev_platform/casic-system-manager-sso/casic-oauth2-client-component1"));
        FileOutputStream fos2 = new FileOutputStream(new File("C:/Users/qh/Desktop/casic-oauth2-client-component1.zip"));
        ZipUtils.toZip(fileList, fos2);*/

        createZip("D:\\project\\706\\dev_platform\\casic-system-manager-sso\\casic-oauth2-client-component1");
    }

}
