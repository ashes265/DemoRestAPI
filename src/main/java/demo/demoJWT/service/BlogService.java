package demo.demoJWT.service;

import demo.demoJWT.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<Blog> findAll();

    void updateBlog(Blog blog);

    void updateBlog(Long id,Blog blog);

    Page<Blog> findAll(Pageable pageable);

    <S extends Blog> S save(S entity);

    Optional<Blog> findById(Long aLong);

    boolean existsById(Long aLong);

    void deleteById(Long aLong);

    Page<Blog> findAll(Specification<Blog> spec, Pageable pageable);
}
