package com.yanghu.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@TableName("tb_flow_files")
public class FlowFiles {

    @TableField("id")
    private String id;

    @TableField("file_name")
    private String fileName;

    @TableField("server_file_name")
    private String serverFileName;

    @TableField("flowkey")
    private String flowkey;

    @TableField("path")
    private String path;

    @TableField("type")
    private Integer type;

    @TableField("suffix")
    private String suffix;

    @TableField("content")
    private String content;

    @TableField("create_time")
    private Date createTime;

    @TableField("status")
    private int status;

    @TableField("create_by")
    private String createBy;


}
