package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.entities.User;

import java.util.List;
import java.util.Optional;

//ToDo
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

//=> if fetch type eager
//    @Query("select distinct u from User u order by u.posts.size desc , u.id")
    @Query("select distinct u from User u join fetch u.posts p order by size(p) desc , u.id ")
    List<User> findAllUsersByPostCountDescThenByUserId();
}
