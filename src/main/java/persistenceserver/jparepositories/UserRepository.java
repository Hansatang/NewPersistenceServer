package persistenceserver.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistenceserver.DatabaseModels.GroupModel;
import persistenceserver.DatabaseModels.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Override
    List<UserModel> findAll();

    Optional<UserModel> findByUsername(String username);
}
