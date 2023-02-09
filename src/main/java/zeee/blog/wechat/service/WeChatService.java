package zeee.blog.wechat.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.common.resttemplate.HttpRestTemplate;
import zeee.blog.utils.TimeUtil;
import zeee.blog.wechat.entity.baidu.BaiduMapEntity;
import zeee.blog.wechat.entity.baidu.BaiduResponse;
import zeee.blog.wechat.entity.baidu.ForecastData;
import zeee.blog.wechat.entity.tian.DailySentenceData;
import zeee.blog.wechat.entity.tian.DailySentenceResponse;
import zeee.blog.wechat.util.BaiduSnCalUtil;
import zeee.blog.wechat.util.WeChatHttpUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
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

    @Value("${weChat.openIds}")
    private String openIds;

    @Value("${weChat.templateId}")
    private String templateId;

    @Value("${weChat.id}")
    private String weChatId;

    @Value("${weChat.secret}")
    private String weChatSecret;

    @Resource
    private HttpRestTemplate restTemplate;

    public String queryWeatherFromBaidu() {
        return null;
    }

    public void pushMessage() {
        // 获取各种信息
        BaiduMapEntity map = getBaiduData();
        DailySentenceData dailySentenceData = getTianResult();
        // 组装
        String[] openIdArr = openIds.split(",");
        for (String openId : openIdArr) {
            com.alibaba.fastjson.JSONObject templateMsg = new JSONObject(new LinkedHashMap<>());
            templateMsg.put("touser", openId);
            templateMsg.put("template_id", templateId);

            JSONObject now = new JSONObject();
            String date = DateUtil.today();
            String week = TimeUtil.getWeekOfDate(new Date());
            String day = date + " " + week;
            now.put("value", day);
            now.put("color", "#EED016");

            JSONObject city = new JSONObject();
            city.put("value", map.getLocation().getCity() + map.getLocation().getName());
            city.put("color", "#60AEF2");

            ForecastData forecastData = map.getForecasts().get(0);
            JSONObject text = new JSONObject();
            text.put("value", forecastData.getTextDay() + "，" + forecastData.getWdDay());
            text.put("color", "#44B549");

            JSONObject low = new JSONObject();
            low.put("value", forecastData.getLow());
            low.put("color", "#44B549");

            JSONObject high = new JSONObject();
            high.put("value", forecastData.getHigh());
            high.put("color", "#44B549");

            JSONObject scqDay = new JSONObject();
            scqDay.put("value", TimeUtil.getBetweenDay());
            scqDay.put("color", "#44B549");

            JSONObject birDay = new JSONObject();
            birDay.put("value", TimeUtil.getBrithDay());
            birDay.put("color", "#44B549");

            JSONObject dailyEnglishCN = new JSONObject();
            dailyEnglishCN.put("value", dailySentenceData.getRainBow());
            dailyEnglishCN.put("color", "#44B549");

            JSONObject dailyEnglishEN = new JSONObject();
            dailyEnglishEN.put("value", dailySentenceData.getEveryDay());
            dailyEnglishEN.put("color", "#44B549");

            JSONObject data = new JSONObject(new LinkedHashMap<>());
            data.put("now", now);
            data.put("city", city);
            data.put("text", text);
            data.put("low", low);
            data.put("high", high);
            data.put("scq_day", scqDay);
            data.put("bir_day", birDay);
            data.put("daily_english_cn", dailyEnglishCN);
            data.put("daily_english_en", dailyEnglishEN);

            templateMsg.put("data", data);

            String accessToken = getAccessToken();
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

            String sendPost = WeChatHttpUtil.sendPost(url, templateMsg.toJSONString());
            JSONObject WeChatMsgResult = JSONObject.parseObject(sendPost);
            if (!"0".equals(WeChatMsgResult.getString("errcode"))) {
                log.error(WeChatMsgResult.getString("errmsg"));
                throw new AppException(ErrorCodes.PUSH_WECHAT_MESSAGE_ERROR);
            }

        }

    }

    public BaiduMapEntity getBaiduData() {
        try {
            String url = getBaiduUrl(BINJIANG_LOCATION_CODE);
            String s = HttpUtil.get(url);
            BaiduResponse res = JSONUtil.toBean(s, BaiduResponse.class);
            return res.getResult();
        } catch (Exception e) {
            throw new AppException(ErrorCodes.GET_BAIDU_DATA_ERROR);
        }
    }

    /**
     * 百度地图拿取天气的api
     */
    public String getBaiduUrl(String addressCode) {
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


    public DailySentenceData getTianResult() {
        try {
            String url1 = "https://apis.tianapi.com/caihongpi/index?key=" + tianApiSecret;
            String url2 = "https://apis.tianapi.com/everyday/index?key=" + tianApiSecret;
            String rainBow = HttpUtil.get(url1);
            String everyDay = HttpUtil.get(url2);
            DailySentenceResponse rainBowResponse = JSONUtil.toBean(rainBow, DailySentenceResponse.class);
            DailySentenceResponse everyDayResponse = JSONUtil.toBean(everyDay, DailySentenceResponse.class);

            DailySentenceData dailySentenceData = new DailySentenceData();
            if (Objects.nonNull(rainBowResponse) && Objects.nonNull(rainBowResponse.getResult())) {
                dailySentenceData.setRainBow(rainBowResponse.getResult().getContent());
            }
            if (Objects.nonNull(everyDayResponse) && Objects.nonNull(everyDayResponse.getResult())) {
                dailySentenceData.setEveryDay(everyDayResponse.getResult().getContent());
            }
            return dailySentenceData;
        } catch (Exception e) {
            throw new AppException(ErrorCodes.GET_TIAN_DATA_ERROR);
        }
    }

    private String getAccessToken() {
        //这里直接写死就可以，不用改，用法可以去看api
        String grant_type = "client_credential";
        //封装请求数据
        String params = "grant_type=" + grant_type + "&secret=" + weChatSecret + "&appid=" + weChatId;
        //发送GET请求
        String sendGet = WeChatHttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        // 解析相应内容（转换成json对象）
        com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(sendGet);
        log.info("微信token响应结果=" + jsonObject1);
        //拿到accesstoken
        return (String) jsonObject1.get("access_token");
    }

}
