package persistenceserver.jparepositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistenceserver.DatabaseModels.GroupMembersModel;
import persistenceserver.Model.User;

import java.util.List;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembersModel, Long> {

    @Override
    List<GroupMembersModel> findAll();

    List<GroupMembersModel> findByGroupId(int groupModelId);
}
