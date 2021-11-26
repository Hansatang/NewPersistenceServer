package persistenceserver.DatabaseModels;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "groups", schema = "notelender")
public class GroupModel {
    @Id
    private Long id;
    @Column(name = "groupname")
    private String name;

    public GroupModel() {

    }

    public GroupModel(String name) {

        this.name = name;
    }

}
