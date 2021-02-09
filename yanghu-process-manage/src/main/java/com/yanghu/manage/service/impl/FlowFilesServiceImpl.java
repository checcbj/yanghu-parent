package com.yanghu.manage.service.impl;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanghu.manage.entity.FlowFiles;
import com.yanghu.manage.entity.vo.FlowFilesVo;
import com.yanghu.manage.mapper.FlowFilesMapper;
import com.yanghu.manage.service.FlowFilesService;
import com.yanghu.manage.util.FileUtil;
import com.yanghu.manage.util.StrUtils;
import com.yanghu.manage.util.UUIDUtil;
import org.activiti.engine.impl.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/8
 */
@Service
public class FlowFilesServiceImpl implements FlowFilesService {

    @Value("${my.fileUpload.flow}")
    private String baseFlowPath;

    @Autowired
    public FlowFilesMapper flowFilesMapper;

    @Override
    public List<String> uploadFile(String key, MultipartFile... files) {
        ArrayList<String> ids = new ArrayList<>();
        // 获取当前日期字符串，作为子目录存在
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        // 路径信息
        String filePath = baseFlowPath + format+"/";

        for (MultipartFile file : files) {
            FlowFiles flowFiles =new FlowFiles();
            String uuid = UUIDUtil.getUUID();
            flowFiles.setId(uuid);
            flowFiles.setFlowkey(key);
            ids.add(uuid);
            flowFiles.setCreateTime(new Date());
            flowFiles.setPath(filePath);

            // 获取原文件名
            String originalFilename = file.getOriginalFilename();
            flowFiles.setFileName(originalFilename);

            int i = originalFilename.lastIndexOf(".");
            String substring = originalFilename.substring(i + 1);
            flowFiles.setSuffix(substring);

            // 文件上传，得到上传后的文件名
            String uploadPath = FileUtil.upload(file,filePath , substring);
            flowFiles.setServerFileName(uploadPath);
            flowFilesMapper.insert(flowFiles);
        }
        return ids;
    }

    @Override
    public String createFile(FlowFilesVo flowFilesVo) {
        // 获取当前日期字符串，作为子目录存在
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        // 路径信息
        String filePath = baseFlowPath + format+"/";

        FlowFiles flowFiles =new FlowFiles();
        String uuid = UUIDUtil.getUUID();
        flowFiles.setId(uuid);
        flowFiles.setCreateTime(new Date());
        flowFiles.setFlowkey(flowFilesVo.getKey());
        // 获取原文件名
        flowFiles.setFileName(flowFilesVo.getFileName());
        flowFiles.setPath(filePath);
        flowFiles.setSuffix("bpmn");

        // 文件上传，得到上传后的文件名
        String serverFileName = UUIDUtil.getUUID();
        FileUtil.uploadBySrt("bpmn",serverFileName,flowFilesVo.getFileDataStr() , filePath);
        flowFiles.setServerFileName(serverFileName);

        flowFilesMapper.insert(flowFiles);
        return uuid;
    }

    @Override
    public void updateFlowkey(String key, List<String> ids) {

        String idss = StrUtils.listToString(ids);
        List<FlowFiles> flowFiles = flowFilesMapper.selectList(new QueryWrapper<FlowFiles>().lambda().in(FlowFiles::getId, idss));

        if (CollectionUtil.isNotEmpty(flowFiles)){
            for (FlowFiles flowFile : flowFiles) {
                flowFile.setFlowkey(key);
                flowFilesMapper.updateById(flowFile);
            }
        }

    }

    @Override
    public List<FlowFiles> queryAll() {
        List<FlowFiles> flowFiles = flowFilesMapper.selectList(null);
        return flowFiles;
    }

    @Override
    public FlowFiles findById(String id) {
        return flowFilesMapper.selectById(id);
    }

    @Override
    public List<FlowFiles> findList(String id) {
        FlowFiles flowFiles = flowFilesMapper.selectById(id);
        if (flowFiles.getType() == 1){
            return null;
        }
        String flowkey = flowFiles.getFlowkey();
        LambdaQueryWrapper<FlowFiles> lambda = new QueryWrapper<FlowFiles>().lambda();
        lambda.eq(FlowFiles::getStatus,1);
        lambda.eq(FlowFiles::getType,0);
        lambda.eq(FlowFiles::getFlowkey,flowkey);
        List<FlowFiles> flowFiles1 = flowFilesMapper.selectList(lambda);
        return flowFiles1;
    }

    @Override
    public Integer updateTypeById(String id, int type) {
        FlowFiles flowFiles = flowFilesMapper.selectById(id);
        flowFiles.setType(type);
        return flowFilesMapper.updateById(flowFiles);
    }
}
