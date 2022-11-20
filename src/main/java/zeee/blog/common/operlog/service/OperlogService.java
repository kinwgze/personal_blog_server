package zeee.blog.common.operlog.service;

import java.util.Date;

public interface OperlogService {

    void addLog(String loginName, String userName, Date date, String address,
                Integer category, String description, Integer result, String failureReason);

}
