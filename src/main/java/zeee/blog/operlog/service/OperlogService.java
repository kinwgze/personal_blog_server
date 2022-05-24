package zeee.blog.operlog.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface OperlogService {


    @Transactional(rollbackFor = Exception.class)
    void addLog(String loginName, String userName, Date date, String address,
                Integer category, String description, Integer result, String failureReason);

}
