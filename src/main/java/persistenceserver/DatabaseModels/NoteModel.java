package persistenceserver.DatabaseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "notes", schema = "notelender")
public class NoteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long user_id;
    @ManyToOne()
    @JoinColumn(name = "group_id")
    private GroupModel groupModel;
    @Column(name = "week")
    private long week;
    @Column(name = "year")
    private long year;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "text")
    private String text;

    public NoteModel() {

    }

    public NoteModel(long user_id, GroupModel groupModel, long week, long year, String name, String status, String text) {
        this.user_id = user_id;
        this.groupModel = groupModel;
        this.week = week;
        this.year = year;
        this.name = name;
        this.status = status;
        this.text = text;
    }



}
