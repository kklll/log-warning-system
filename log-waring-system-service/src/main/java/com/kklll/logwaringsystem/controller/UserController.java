package com.kklll.logwaringsystem.controller;

import com.kklll.logwaringsystem.exception.CommonEnum;
import com.kklll.logwaringsystem.pojo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/6 15:35
 * @Version 1.0
 **/
@RestController
@Api("用户相关API")
public class UserController {
    @GetMapping
    @ApiOperation("/根据学生id获取学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, paramType = "query", dataType = "int")
    })
    public ResultBody test() {
        return new ResultBody(CommonEnum.INTERNAL_SERVER_ERROR);
    }

}
