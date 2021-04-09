package com.kklll.logwarningsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Alert
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/7 16:08
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "alert")
public class Alert {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "serverName")
    private String servername;

    @TableField(value = "alertName")
    private String alertname;

    @TableField(value = "`level`")
    private Integer level;

    @TableField(value = "alertDate")
    private Date alertdate;

    @TableField(value = "handleDate")
    private Date handledate;

    @TableField(value = "`handler`")
    private String handler;

    @TableField(value = "content")
    private String content;

    @TableField(value = "`state`")
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