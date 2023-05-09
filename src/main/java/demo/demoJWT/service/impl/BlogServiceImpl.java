package demo.demoJWT.service.impl;

import demo.demoJWT.model.Blog;
import demo.demoJWT.repository.BlogRepository;
import demo.demoJWT.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    @Autowired
    private final RedisTemplate blogTemplate;

    @Override
    @Cacheable(value = "blogs")
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
//    @CachePut(value = "blog", key = "#blogId")
    public void updateBlog(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
//    @CachePut(value = "blog", key = "#blogId")
    public void updateBlog(Long id, Blog blog) {
        Blog forResult = blogRepository.findById(id).get();
        if (forResult != null) {
            forResult.setTitle(blog.getTitle());
            forResult.setContent(blog.getContent());
            forResult.setType(blog.getType());
            blogRepository.save(forResult);
        }
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public <S extends Blog> S save(S entity) {
        blogTemplate.opsForHash().put("detailBlog", blogTemplate.opsForHash().size("blog"), entity);
        return blogRepository.save(entity);
    }

    @Override
//    @Cacheable(value = "blog", key = "#blogId")
    public Optional<Blog> findById(Long aLong) {
        Blog blog = blogRepository.findById(aLong).get();
        System.out.println(blog.getId());
        blogTemplate.opsForHash().put("blog", blog.getId() + "", blog);
        return blogRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return blogRepository.existsById(aLong);
    }

    @Override
//    @CacheEvict(value = "blog", key = "#blogId")
    public void deleteById(Long aLong) {
        blogRepository.deleteById(aLong);
    }

    @Override
    public Page<Blog> findAll(Specification<Blog> spec, Pageable pageable) {
        return blogRepository.findAll(spec, pageable);
    }


//    @Scheduled(fixedRate = 4000)
//    public void checkSizeBlogWithFixRate() {
//        System.out.println("Size hien tai: " + blogTemplate.opsForHash().keys("blog").size());
//    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateListBlogAfterDelete() {
        //update list blog if some blog was delete
        for (Blog blog : blogRepository.findAll()) {
            if (blogTemplate.opsForHash().get("blog", blog.getId().toString()) == null) {
                blogRepository.deleteById(blog.getId());
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateListBlogAfterInsert() {
        //update list blog if some blog was insert
        for (Object hashKey : blogTemplate.opsForHash().keys("newBlog")) {//Object is hashKey
            //find ib database with hashkey(Id)
            Blog blog = blogRepository.findById(Long.parseLong((String) hashKey)).get();
            if (blog == null) {
                //save new Blog to database
                blogRepository.save((Blog) blogTemplate.opsForHash().get("blog", hashKey));
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateListBlogAfterUpdate() {
        //update list blog if some blog was insert
        for (Object hashKey : blogTemplate.opsForHash().keys("updateBlog")) {//Object is hashKey
            //find ib database with hashkey(Id)
            Blog blog = blogRepository.findById(Long.parseLong((String) hashKey)).get();
            if (blog != null) {
                //update Blog to database
                blogRepository.save(blog);
            }
        }
    }
}
