package com.yanghu.manage.util;


import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Desc 文件处理工具类
 */
@Slf4j
public class FileUtil {



    /**
     * 最大缩略图文件大小 360K
     */
    private static long IMAGE_MAX_SIZE = 1024 * 360;

    /**
     * 找到全部文件
     *
     * @param path
     * @param regx
     * @return
     */
    public static File[] getAllFiles(String path, String regx) {
        List<File> fileList = new ArrayList<File>();
        getFiles(path, fileList, regx);
        return fileList.toArray(new File[fileList.size()]);
    }

    private static void getFiles(String path, List<File> fileList, String regx) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getFiles(f.getAbsolutePath(), fileList, regx);
            } else {
                if (f.getName().matches(regx)) {
                    fileList.add(f);
                }
            }
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
     *
     * @param zipFileName  需要解压的ZIP文件
     * @param descFileName 目标文件
     */
    public static boolean unZipFiles(String zipFileName, String descFileName) {
        String descFileNames = descFileName;
        if (!descFileNames.endsWith(File.separator)) {
            descFileNames = descFileNames + File.separator;
        }
        try {
            // 根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(zipFileName);
            ZipEntry entry;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            // 获取ZIP文件里所有的entry
            Enumeration enums = zipFile.getEntries();
            // 遍历所有entry
            while (enums.hasMoreElements()) {
                entry = (ZipEntry) enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory()) {
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                } else {
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }
                File file = new File(descFileDir);
                // 打开文件输出流
                OutputStream os = new FileOutputStream(file);
                // 从ZipFile对象中打开entry的输入流
                InputStream is = zipFile.getInputStream(entry);
                while ((readByte = is.read(buf)) != -1) {
                    os.write(buf, 0, readByte);
                }
                os.close();
                is.close();
            }
            zipFile.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static final int BUFFEREDSIZE = 1024;

    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param filePath    需要压缩的文件夹或者文件路径
     * @param isDelete    是否删除源文件
     * @throws Exception
     */
    public void toZip(String zipFileName, String filePath, boolean isDelete) throws Exception {
        toZip(zipFileName, new File(filePath), isDelete);
    }

    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param inputFile   需要压缩的文件夹或者文件
     * @param isDelete    是否删除源文件
     * @throws Exception
     */
    public void toZip(String zipFileName, File inputFile, boolean isDelete) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        if (!inputFile.exists()) {
            throw new FileNotFoundException("在指定路径未找到需要压缩的文件！");
        }
        toZip(out, inputFile, "", isDelete);
        out.close();
    }

    /**
     * 递归压缩方法
     *
     * @param out       压缩包输出流
     * @param inputFile 需要压缩的文件
     * @param base      压缩的路径
     * @param isDelete  是否删除源文件
     * @throws Exception
     */
    private void toZip(ZipOutputStream out, File inputFile, String base, boolean isDelete) throws Exception {
        // 如果是目录
        if (inputFile.isDirectory()) {
            File[] inputFiles = inputFile.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < inputFiles.length; i++) {
                toZip(out, inputFiles[i], base + inputFiles[i].getName(), isDelete);
            }
        } else { // 如果是文件
            if (base.length() > 0) {
                out.putNextEntry(new ZipEntry(base));
            } else {
                out.putNextEntry(new ZipEntry(inputFile.getName()));
            }
            FileInputStream in = new FileInputStream(inputFile);
            try {
                int len;
                byte[] buff = new byte[BUFFEREDSIZE];
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
            } catch (IOException e) {
                throw e;
            } finally {
                in.close();
            }
        }
        if (isDelete) {
            inputFile.delete();
        }
    }





    /**
     * 文件下载
     * @param response 响应对象
     * @param path 文件路径
     * @param fileName 文件名
     * @param fileType 文件类型
     */
    public static void download(HttpServletResponse response, String path, String fileName, String fileType) {
        FileInputStream fis;
        String fewFileName;
        try {
            fis = new FileInputStream(path);
            // 支持中文名称
            fewFileName = URLEncoder.encode(fileName,"UTF8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("模版不存在.");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException("文件名称转码失败");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fewFileName + "."+fileType);
        OutputStream os;
        try {
            os = response.getOutputStream();
            IOUtils.copy(fis, os);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException("下载失败.");
        }
    }

    /**
     * 文件上传
      * @param file 要上传的文件
     * @param uploadFileDir 上传地址
     * @param type 文件类型
     * @return 文件存储路径
     */
    public static String upload(MultipartFile file, String uploadFileDir, String type) {

        Assert.notNull(uploadFileDir, "请配置文件上传目录.");
        Assert.notNull(type, "文件类型不能为空.");
        if (ObjectUtil.isNull(file)){
            throw new RuntimeException("文件未找到");
        }
        String fileId = UUIDUtil.getUUID();
        File dir = new File(uploadFileDir);
        String newFileName = fileId + "." +type;
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("文件夹创建失败");
        }
        File saveFile = new File(dir, newFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(saveFile.getAbsolutePath()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("===> 文件上传失败." + e.getMessage());
        }
        return newFileName;
//        return uploadFileDir+"/"+newFileName;
    }

    public static String uploadBySrt(String fileType,String fileName, String fileDataStr, String uploadFileDir){
        Assert.notNull(fileType, "文件类型不能为空.");
        Assert.notNull(fileName, "文件名称不能为空.");
        Assert.notNull(fileDataStr, "文件内容数据不能为空.");
        Assert.notNull(uploadFileDir, "请配置文件上传目录.");

        File dir = new File(uploadFileDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("文件夹创建失败");
        }
        FileOutputStream fos = null;
        try {
            new FileOutputStream( new File(dir,fileName+fileType));
            //注意字符串编码
            fos.write(fileDataStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }


}