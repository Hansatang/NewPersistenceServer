package persistenceserver.DatabaseModels;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "invitations", schema = "notelender")
public class InvitationModel {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "invitor_id")
    private UserModel invitor;
    @ManyToOne
    @JoinColumn(name = "invitee_id")
    private UserModel invitee;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel groupModel;

    public InvitationModel() {

    }


    public InvitationModel(UserModel invitor, UserModel invitee, GroupModel groupModel) {
        this.invitor = invitor;
        this.invitee = invitee;
        this.groupModel = groupModel;
    }


}
