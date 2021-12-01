package persistenceserver.Model;

public class Invitation
{
  private long id;
  private long invitorId;
  private long inviteeId;
  private long groupId;

  public long getId()
  {
    return id;
  }
  public void setId(int id)
  {
    this.id = id;
  }

  public long getInvitorId()
  {
    return invitorId;
  }

  public void setInvitorId(int invitorId)
  {
    this.invitorId = invitorId;
  }

  public long getInviteeId()
  {
    return inviteeId;
  }

  public void setInviteeId(int inviteeId)
  {
    this.inviteeId = inviteeId;
  }

  public long getGroupId()
  {
    return groupId;
  }

  public void setGroupId(int groupId)
  {
    this.groupId = groupId;
  }

  public Invitation(long id, long groupId, long inviteeId,long invitorId )
  {
    this.id = id;
    this.groupId = groupId;
    this.inviteeId = inviteeId;
    this.invitorId = invitorId;

  }
}
