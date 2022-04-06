package zeee.blog.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.demo.dao.UserOperation;
import zeee.blog.demo.entity.User;
import zeee.blog.demo.handler.HelloBlogHandler;

import javax.annotation.Resource;

/**
 * @author zeee
 * 2022/3/27
 */
@RestController
@RequestMapping("/blog/test/demo")
public class HelloBlogController {

    private static Logger log = LoggerFactory.getLogger(HelloBlogController.class);

    @Resource
    private HelloBlogHandler hbh;

    @Resource
    private UserOperation userOperation;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String helloBlog(){
        log.error("log test");
        return hbh.helloBlog();
    }

    @RequestMapping(value = "insert", method = RequestMethod.GET)
    public String insertAndShow(){
        User user = new User();
        user.setUsername("wang22");
        user.setLevel(0);
        user.setPhoneNumber("1111111113");
        // 这里不应该直接调用的。应该按层次依次调用。这里为了快速测试
//        userOperation.insert(user.getUsername(),user.getLevel(), user.getPhonenumber());
        userOperation.insert2(user);
        User user2 = userOperation.findByName("wang22");
        return user2.toString();
    }


}
