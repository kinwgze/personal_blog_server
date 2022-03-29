package zeee.blog.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String helloBlog(){
        log.error("log test");
        return hbh.helloBlog();
    }
}
