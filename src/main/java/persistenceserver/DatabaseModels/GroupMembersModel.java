package persistenceserver.DatabaseModels;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "groupmembers", schema = "notelender")
public class GroupMembersModel {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @Column(name = "group_id")
    private int groupId;

    public GroupMembersModel() {

    }


    public GroupMembersModel(UserModel userModel, int groupId) {
        this.userModel = userModel;
        this.groupId = groupId;
    }

}
