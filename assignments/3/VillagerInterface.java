public interface VillagerInterface {
  public int getId();
  public String getType();
  public boolean isDead();
  public void kill();
  public void clearVote();
  public void voteUp();
  public int getVote();
  public int getHP();
}
