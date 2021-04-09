package com.kklll.logwarningsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Alert
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/7 16:05
 * @Version 1.0
 **/

@ApiModel(value = "com-kklll-logwaringsystem-pojo-Alert")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "alert")
public class Alert {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    @TableField(value = "serverName")
    @ApiModelProperty(value = "")
    private String servername;

    @TableField(value = "alertName")
    @ApiModelProperty(value = "")
    private String alertname;

    @TableField(value = "`level`")
    @ApiModelProperty(value = "")
    private Integer level;

    @TableField(value = "alertDate")
    @ApiModelProperty(value = "")
    private Date alertdate;

    @TableField(value = "handleDate")
    @ApiModelProperty(value = "")
    private Date handledate;

    @TableField(value = "`handler`")
    @ApiModelProperty(value = "")
    private String handler;

    @TableField(value = "content")
    @ApiModelProperty(value = "")
    private String content;

    @TableField(value = "`state`")
    @ApiModelProperty(value = "")
    private Integer state;

    public static final String COL_ID = "id";

    public static final String COL_SERVERNAME = "serverName";

    public static final String COL_ALERTNAME = "alertName";

    public static final String COL_LEVEL = "level";

    public static final String COL_ALERTDATE = "alertDate";

    public static final String COL_HANDLEDATE = "handleDate";

    public static final String COL_HANDLER = "handler";

    public static final String COL_CONTENT = "content";

    public static final String COL_STATE = "state";
}