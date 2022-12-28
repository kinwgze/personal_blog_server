package zeee.blog.wechat.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.common.resttemplate.HttpRestTemplate;
import zeee.blog.utils.JsonUtil;
import zeee.blog.wechat.entity.baidu.BaiduMapEntity;
import zeee.blog.wechat.entity.baidu.BaiduResponse;
import zeee.blog.wechat.entity.tian.TianData;
import zeee.blog.wechat.entity.tian.TianResponse;
import zeee.blog.wechat.entity.tian.TianResult;
import zeee.blog.wechat.util.BaiduSnCalUtil;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wz
 * @date 2022/12/13
 */
@Service
public class WeChatService {

    private static final Logger log = LoggerFactory.getLogger(WeChatService.class);

    public static final String BINJIANG_LOCATION_CODE = "330108";

    @Value("${baidu.mapAk}")
    private String baiduAk;

    @Value("${baidu.mapSk}")
    private String baiduSk;

    @Value(("${tianapi.apiKey}"))
    private String tianApiSecret;

    @Resource
    private HttpRestTemplate restTemplate;

    public String queryWeatherFromBaidu() {
        return null;
    }

    public void getMessage() {
        // 获取各种信息
        BaiduMapEntity map = getBaiduData();
        TianData tianData = getTianResult();
        // 组装

    }

    public BaiduMapEntity getBaiduData() {
        try {
            String url = getBaiduUrl(BINJIANG_LOCATION_CODE);
            String s = HttpUtil.get(url);
            BaiduResponse res = JSONUtil.toBean(s,BaiduResponse.class);
            return res.getResult();
        } catch (Exception e) {
            throw new AppException(ErrorCodes.GET_BAIDU_DATA_ERROR);
        }
    }

    /**
     * 百度地图拿取天气的api
     */
    private String getBaiduUrl(String addressCode) {
        String url;
        try {
            String sn = BaiduSnCalUtil.getSn(addressCode, baiduAk, baiduSk);
            url = String.format("https://api.map.baidu.com/weather/v1/?district_id=%s&data_type=all&output=json&ak=%s&sn=%s",
                    addressCode, baiduAk, sn);
        } catch (Exception e) {
            log.error("get baidu api error", e);
            throw new AppException(ErrorCodes.API_ERROR);
        }
        return url;
    }


    private TianData getTianResult() {
        try {
            String url1 = "https://apis.tianapi.com/caihongpi/index?key=" + tianApiSecret;
            String url2 = "https://apis.tianapi.com/everyday/index?key=" + tianApiSecret;
            String rainBow = HttpUtil.get(url1);
            String everyDay = HttpUtil.get(url2);
            TianResponse rainBowResponse = JSONUtil.toBean(rainBow, TianResponse.class);
            TianResponse everyDayResponse = JSONUtil.toBean(everyDay, TianResponse.class);

            TianData tianData = new TianData();
            if (Objects.nonNull(rainBowResponse) && Objects.nonNull(rainBowResponse.getResult())) {
                tianData.setRainBow(rainBowResponse.getResult().getContent());
            }
            if (Objects.nonNull(everyDayResponse) && Objects.nonNull(everyDayResponse.getResult())){
                tianData.setEveryDay(everyDayResponse.getResult().getContent());
            }
            return tianData;
        } catch (Exception e) {
            throw new AppException(ErrorCodes.GET_TIAN_DATA_ERROR);
        }
    }


}
