package persistenceserver.Model;

import lombok.Data;

@Data
public class Membership {
    private long id, userId, groupId;
    private String username;

    public Membership(long id, long userId, long groupId, String username) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.username = username;
    }
}
