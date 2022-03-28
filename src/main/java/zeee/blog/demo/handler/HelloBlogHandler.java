package zeee.blog.demo.handler;

import org.springframework.stereotype.Service;
import zeee.blog.demo.service.HelloBlogService;

import javax.annotation.Resource;

/**
 * @author zeee
 * 2022/3/27
 */
@Service("HelloBlogHandler")
public class HelloBlogHandler {

    @Resource
    private HelloBlogService hbs;

    public String helloBlog(){
        return hbs.helloBlog();
    }
}
