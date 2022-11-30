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
import zeee.blog.guardsystem.entity.GuestResponseVO;
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
    public RpcResult<GuestResponseVO> requestForPermit(@RequestBody GuestRequestInfo guestRequestInfo) {
        RpcResult<GuestResponseVO> res = new RpcResult<>();
        try {
            GuestResponseVO guestResponseVO = guardSystemHandler.addGuestVisitInfo(guestRequestInfo);
            res.setData(guestResponseVO);
            res.setState(StateResult.SUCCESS);
        } catch (AppException ae) {
            res.setData(null);
            res.setState(StateResult.FAILURE);
            res.setFailureMessage(ae.getMessage());
        }
        return res;
    }

    @LogHttp
    @ApiOperation(value = "查询申请信息")
    @ApiImplicitParam(name = "checkCode", value = "校验码", required = true, dataType = "string")
    @RequestMapping(value = "queryRequestInfo", method = RequestMethod.GET)
    public RpcResult<Boolean> queryRequestInfo(String checkCode) {
        RpcResult<Boolean> res = new RpcResult<>();

        return res;
    }

}
