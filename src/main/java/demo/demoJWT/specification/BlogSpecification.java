package demo.demoJWT.specification;

import demo.demoJWT.model.Blog;
import org.springframework.data.jpa.domain.Specification;

public class BlogSpecification {
    public static Specification<Blog> hasTitle(String tile) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + tile + "%"));
    }

    public static Specification<Blog> hasType(String type) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("type"), "%" + type + "%"));
    }
}
