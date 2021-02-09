package com.yanghu.manage.service;


import com.yanghu.manage.entity.FlowFiles;
import com.yanghu.manage.entity.vo.FlowFilesVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/8
 */
public interface FlowFilesService {

    /**
     * 上传流程文件数组
     * @param key 流程定义key
     * @param files 文件数组
     * @return 流程文件存储id
     */
    List<String> uploadFile(String key, MultipartFile[] files);

    /**
     * 根据文件数据字符串创建 流程文件
     * @param flowFilesVo 包装对象
     * @return 流程文件id
     */
    String createFile(FlowFilesVo flowFilesVo);

    /**
     * 批量更新flowkey
     * @param key key
     * @param ids id集合
     */
    void updateFlowkey(String key, List<String> ids);

    /**
     *  查询全部流程文件
     * @return 流程文件数据集
     */
    List<FlowFiles> queryAll();

    /**
     * 根据id查询
     * @param id id
     * @return 流程文件记录
     */
    FlowFiles findById(String id);

    /**
     * 根据id查询流程文件集
     * @param id id
     * @return 流程文件集
     */
    List<FlowFiles> findList(String id);

    /**
     * 根据id 更新文件状态  0 未部署 1 部署
     * @param id id
     * @param type 状态
     * @return 操作状态
     */
    Integer updateTypeById(String id,int type);
}
