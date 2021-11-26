package persistenceserver.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistenceserver.DatabaseModels.GroupMembersModel;
import persistenceserver.DatabaseModels.InvitationModel;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationModel, Long> {

    @Override
    List<InvitationModel> findAll();

    List<InvitationModel> findByInvitee_Id(long inveteeId);
}
