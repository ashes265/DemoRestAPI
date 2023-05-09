package demo.demoJWT.controller;

import demo.demoJWT.model.Blog;
import demo.demoJWT.service.BlogService;
import demo.demoJWT.specification.BlogSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private static Logger logger=Logger.getLogger(BlogController.class);
    private final BlogService blogService;
    private final RedisTemplate blogTemplate;


    private List<Long> listIdDelete;
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws IOException {
        File file = ResourceUtils.getFile("classpath:PracticeKey.txt");
        System.out.println(file.getName());
//        InputStream in = new FileInputStream(file);
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null)

            // Print the string
            System.out.println(st);

        if(blogTemplate.opsForHash().entries("blogs")!=null){
            //in case list already exist in cache redis
            return ResponseEntity.ok().body(blogTemplate.opsForHash().entries("blog"));
        }else{
            List<Blog> blogs=blogService.findAll();
            return ResponseEntity.ok().body(blogs);
        }
//        logger.info("Find all data blog from database");
//        List<Blog> blogs=blogService.findAll();
//        return ResponseEntity.ok().body(blogs);
    }

    @GetMapping("/listFilter")
    public ResponseEntity<?> getAll(@PageableDefault(size = 4, sort = "id") Pageable pageable,
                                    @RequestParam(required = false) String title,
                                    @RequestParam(required = false) String type,
                                    @RequestParam(required = false) String direction) {
        if (title == null) {
            title = "";
        }
        if (type == null) {
            type = "";
        }
        //get default sorting with page
        if (direction.equals("asc")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().ascending());
        } else if (direction.equals("desc")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().descending());
        }
        //for filter
        Specification<Blog> condition = BlogSpecification.hasTitle(title).and(BlogSpecification.hasType(type));
        //combine condition and page
        Page<Blog> pages = blogService.findAll(condition, pageable);
        return ResponseEntity.ok().body(pages);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getBlogDetailById(@RequestParam Long id) {
        if(blogTemplate.opsForHash().get("blog",id.toString())!=null){
            //in case list already exist in cache redis
            System.out.println("Truong hop getDetailById Blog!");
            return ResponseEntity.ok().body(blogTemplate.opsForHash().get("blog",id.toString()));
        }else{
            Blog blog = blogService.findById(id).get();
            if (blog != null) {
                return ResponseEntity.ok().body(blog);
            } else {
                return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
            }
        }

    }

    @PostMapping("/create")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        blogTemplate.opsForHash().put("newBlog", (blogTemplate.opsForHash().size("blog")+1)+"", blog);
        return ResponseEntity.ok().body(blogService.save(blog));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBlogById(@RequestParam Long id, @RequestBody Blog blog) {
        if(blogTemplate.opsForHash().get("blog",id.toString())!=null){
            //in case list already exist in cache redis
            System.out.println("Truong hop update khi blog ton tai trong redis");
            blogTemplate.opsForHash().put("blog",id.toString(),blog);
            blogTemplate.opsForHash().put("updateBlogs",id.toString(),blog);
            return ResponseEntity.ok().body(blogTemplate.opsForHash().get("blog",id.toString()));
        }else{
        }
        blogService.updateBlog(id, blog);
        return new ResponseEntity<>("Update successfull", HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateBlogByIdWithFields(@RequestParam Long id, @RequestBody Map<Object, Object> fields) {
        Blog result = blogService.findById(id).get();
        if (result != null) {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(Blog.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, result, v);
            });
            blogService.updateBlog(result);
            return new ResponseEntity<>("Update successfull", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlogById(@PathVariable Long id) {
        if(blogTemplate.opsForHash().get("blog",id.toString())!=null){
            System.out.println("Truong hop delete khi blog nam trong Redis");
            blogTemplate.opsForHash().delete("blog",id.toString());//delete
            return new ResponseEntity<>("Delete successfull", HttpStatus.ACCEPTED);
        }else{
            if (blogService.existsById(id)) {
                try {
                    blogService.deleteById(id);
                    return new ResponseEntity<>("Delete successfull", HttpStatus.ACCEPTED);
                } catch (Exception e) {
                    return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Not found blog", HttpStatus.NOT_FOUND);
            }
        }

    }
}
