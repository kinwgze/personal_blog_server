package zeee.blog.wechat.service;

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
import zeee.blog.wechat.entity.baidu.BaiduMapEntity;
import zeee.blog.wechat.util.BaiduSnCalUtil;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/12/13
 */
@Service
public class WeChatService {

    private static final Logger log = LoggerFactory.getLogger(WeChatService.class);

    public static final String BINJIANG_LOCATION_CODE = "330108";

    @Value("${baidu.mapAK}")
    private String baiduAk;

    @Value("${baidu.mapSK}")
    private String baiduSk;

    @Resource
    private HttpRestTemplate restTemplate;

    public String queryWeatherFromBaidu() {
        return null;
    }

    public void getMessage() {
        // 获取各种信息

        // 组装

    }

    public BaiduMapEntity getBaiduData() {
        String url = getBaiduUrl(BINJIANG_LOCATION_CODE);
        System.out.println(restTemplate.getForEntity(url, new ParameterizedTypeReference<BaiduMapEntity>() {}));
        ResponseEntity<BaiduMapEntity> resp =
                restTemplate.getForEntity(url, new ParameterizedTypeReference<BaiduMapEntity>() {});
        if (resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null) {
            log.error(resp.toString());
            throw new AppException(ErrorCodes.GET_BAIDU_DATA_ERROR);
        }
        return resp.getBody();
    }

    /**
     * 百度地图拿取天气的api
     */
    private String getBaiduUrl(String addressCode) {
        String url = null;
        try {
            String sn = BaiduSnCalUtil.getSn(addressCode, baiduAk, baiduSk);
            url = String.format("https://api.map.baidu.com/weather/v1/?district_id=%s&data_type=all&output=xml&ak=%s&sn=%s",
                    addressCode, baiduAk, sn);
        } catch (Exception e) {
            log.error("get baidu api error", e);
            throw new AppException(ErrorCodes.API_ERROR);
        }
        return url;
    }


}
