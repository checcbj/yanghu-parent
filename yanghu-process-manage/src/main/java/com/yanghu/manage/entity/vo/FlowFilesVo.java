package com.yanghu.manage.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/8
 */
@Data
public class FlowFilesVo implements Serializable {
    private String key;
    private String fileName;
    private String fileDataStr;
}
