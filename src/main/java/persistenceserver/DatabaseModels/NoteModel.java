package persistenceserver.DatabaseModels;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notes", schema = "notelender")
public class NoteModel {
    @Id
    private Long id;
    @Column(name = "user_id")
    private int user_id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel groupModel;
    @Column(name = "week")
    private int week;
    @Column(name = "year")
    private int year;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "text")
    private String text;

    public NoteModel() {

    }

    public NoteModel(int user_id, GroupModel groupModel, int week, int year, String name, String status, String text) {
        this.user_id = user_id;
        this.groupModel = groupModel;
        this.week = week;
        this.year = year;
        this.name = name;
        this.status = status;
        this.text = text;
    }

}
