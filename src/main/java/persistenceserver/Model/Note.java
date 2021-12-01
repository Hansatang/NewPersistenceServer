package persistenceserver.Model;

public class Note {

   private long id,userId, groupId,week,year;
   private  String name, status, text;

   public Note(){

   }

    public Note(long id, long userId, long groupId, long week, long year, String name, String status, String text) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.week = week;
        this.year = year;
        this.name = name;
        this.status = status;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public long getUserId() {return userId;}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public long getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public long getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
