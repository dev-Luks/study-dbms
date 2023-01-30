package domain;

public class FollowVO {
   private Long followId;
   private Long followingId;
   private Long followerId;
   
   public FollowVO() {;}

   public Long getFollowId() {
      return followId;
   }

   public void setFollowId(Long followId) {
      this.followId = followId;
   }

   public Long getFollowingId() {
      return followingId;
   }

   public void setFollowingId(Long followingId) {
      this.followingId = followingId;
   }

   public Long getFollowerId() {
      return followerId;
   }

   public void setFollowerId(Long followerId) {
      this.followerId = followerId;
   }

   @Override
   public String toString() {
      return "FollowVO [followId=" + followId + ", followingId=" + followingId + ", followerId=" + followerId + "]";
   }
   
   
   
}