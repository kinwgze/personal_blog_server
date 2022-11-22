package zeee.blog.guardsystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.loghttp.LogHttp;
import zeee.blog.common.rpc.RpcResult;
import zeee.blog.common.rpc.StateResult;
import zeee.blog.guardsystem.entity.GuestRequestInfo;
import zeee.blog.guardsystem.handler.GuardSystemHandler;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/11/21
 */
@Api(tags = "门禁申请系统")
@RestController
@RequestMapping("/guard/system")
public class GuardSystemController {

    @Resource
    private GuardSystemHandler guardSystemHandler;

    @LogHttp
    @ApiOperation(value = "访客申请入口")
    @ApiImplicitParam(name = "guestRequestInfo", value = "访客申请信息", required = true, dataType = "GuestRequestInfo")
    @RequestMapping(value = "requestForPermit", method = RequestMethod.POST)
    public RpcResult<Boolean> requestForPermit(@RequestBody GuestRequestInfo guestRequestInfo) {
        RpcResult<Boolean> res = new RpcResult<>();
        // 处理方法
        try {
            guardSystemHandler.addGuestRequest(guestRequestInfo);
            res.setData(true);
            res.setState(StateResult.SUCCESS);
        } catch (AppException ae) {
            res.setData(false);
            res.setState(StateResult.FAILURE);
            res.setFailureMessage(ae.getMessage());
        }
        return res;
    }

}
