package demo.demoJWT.repository;

import demo.demoJWT.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends SolrCrudRepository<Product, String> {
    public Optional<Product> findById(String id);

    public List<Product> findByName(String name);

//    @Query("id:*?0* OR name:*?0*")
//    public Page<Product> findByCustomQuery(String searchTerm, Pageable pageable);
//
//    @Query(name = "Product.findByNamedQuery")
//    public Page<Product> findByNamedQuery(String searchTerm, Pageable pageable);

}
