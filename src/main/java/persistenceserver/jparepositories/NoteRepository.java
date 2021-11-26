package persistenceserver.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistenceserver.DatabaseModels.NoteModel;
import persistenceserver.DatabaseModels.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteModel, Long> {

    @Override
    List<NoteModel> findAll();

    List<NoteModel>  findByGroupModel_Id(Long group_id);
}
