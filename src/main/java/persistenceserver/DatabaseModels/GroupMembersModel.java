package persistenceserver.DatabaseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "groupmembers", schema = "notelender")
public class GroupMembersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel groupModel;

    public GroupMembersModel() {

    }


    public GroupMembersModel(UserModel userModel, GroupModel groupModel) {
        this.userModel = userModel;
        this.groupModel = groupModel;
    }

}
