package demo.demoJWT.service.schedule;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.demoJWT.model.Blog;
import demo.demoJWT.repository.BlogRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JobsSchedule {
    @Autowired
    private RedisTemplate blogTemplate;
    @Autowired
    private BlogRepository blogService;

//    @Scheduled(fixedRate = 3000)
//    public void checkSizeBlogWithFixRate() {
//        System.out.println("Size hien tai: " + blogTemplate.opsForHash().keys("blog").size());
//        for (Object hashKey : blogTemplate.opsForHash().keys("blog")) {//Object is hashKey
//            Blog blog = (Blog) blogTemplate.opsForHash().get("blog", hashKey);
//            System.out.println(blog);
//        }
//    }

//    public void insertAllBlogInQueueBlogSave() {
//        for (Object hashKey : blogTemplate.opsForHash().keys("blog")) {//Object is hashKey
//            Blog blog = (Blog) blogTemplate.opsForHash().get("blog", hashKey);
//            System.out.println(blog);
//        }
//    }
}
