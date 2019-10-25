package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Potato;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Potato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PotatoRepository extends JpaRepository<Potato, Long> {

}
