package persistenceserver.DatabaseModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "groups", schema = "notelender")
public class GroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "groupname")
    private String name;

    public GroupModel() {

    }

    public GroupModel(String name) {
        this.name = name;
    }

}
