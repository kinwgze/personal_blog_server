package zeee.blog.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.demo.handler.HelloBlogHandler;

import javax.annotation.Resource;

/**
 * @author zeee
 * 2022/3/27
 */
@RestController
public class HelloBlogController {

    @Resource
    private HelloBlogHandler hbh;

    @RequestMapping(value = "hello")
    public String helloBlog(){
        return hbh.helloBlog();
    }
}
