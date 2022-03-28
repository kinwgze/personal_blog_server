package zeee.blog.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author zeee
 * 2022/3/27
 */
@Service("HelloBlogService")
public class HelloBlogServiceImpl implements HelloBlogService {

    @Override
    public String helloBlog(){
        return "HELLO BLOG";
    }
}
