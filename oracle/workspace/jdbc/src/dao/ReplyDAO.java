package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.ReplyDTO;
import domain.ReplyVO;

public class ReplyDAO {
   public Connection connection;
   public PreparedStatement preparedStatement;
   public ResultSet resultSet;
   
//   대댓글 추가
   public void insert(ReplyVO replyVO, Long target) { //타겟된것이 REPLY_GROUP으로 된다.	
      String query = "INSERT INTO TBL_REPLY"
            + "(REPLY_ID, REPLY_CONTENT, USER_ID, BOARD_ID, REPLY_GROUP, REPLY_DEPTH) "
            + "VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?, ?, (SELECT REPLY_DEPTH + 1 FROM TBL_REPLY WHERE REPLY_ID = ?))"; //대댓글을 작성할 때마다 하나씩 증가 초기 DEPTH값은 0이다.
      
      connection = DBConnecter.getConnection();
      try {
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, replyVO.getReplyContent());
         preparedStatement.setLong(2, UserDAO.userId);
         preparedStatement.setLong(3, replyVO.getBoardId());
         preparedStatement.setLong(4, target);
         preparedStatement.setLong(5, target);
         
         preparedStatement.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
      
   }
   
//   댓글 추가
   public void insert(ReplyVO replyVO) {
      String query = "INSERT INTO TBL_REPLY"
            + "(REPLY_ID, REPLY_CONTENT, USER_ID, BOARD_ID, REPLY_GROUP, REPLY_DEPTH) "
            + "VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?, SEQ_REPLY.CURRVAL, 0)"; //SEQ_REPLY.CURRVAL = 시퀀스가 증가된 값을 여기로 가져오기.
      
      connection = DBConnecter.getConnection();
      try {
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, replyVO.getReplyContent());
         preparedStatement.setLong(2, UserDAO.userId);
         preparedStatement.setLong(3, replyVO.getBoardId());
         
         preparedStatement.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
   }
   
//   댓글 전체 조회
   public ArrayList<ReplyDTO> selectAll(){
	   					// NVL쓰는 이유 댓글과 대댓글을 구분하기 위해서 사용한다.
      String query = "SELECT NVL(REPLY_COUNT, 0) REPLY_COUNT, REPLY_ID, REPLY_CONTENT, R2.USER_ID, BOARD_ID, REPLY_REGISTER_DATE, REPLY_UPDATE_DATE, " 
            + "R2.REPLY_GROUP, REPLY_DEPTH, " 
            + "USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, " 
            + "USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, " 
            + "USER_GENDER, USER_RECOMMENDER_ID " 
            	// 댓글, 대댓글 수( 대댓글 수는 댓글을 빼줘야 하기 때문에 -1을 해준다)
            + "FROM (SELECT REPLY_GROUP, COUNT(REPLY_ID) - 1 REPLY_COUNT FROM TBL_REPLY GROUP BY REPLY_GROUP) R1 RIGHT OUTER JOIN VIEW_REPLY_USER R2 "
            + "ON R1.REPLY_GROUP = R2.REPLY_GROUP AND R1.REPLY_GROUP = R2.REPLY_ID";
      
      ReplyDTO replyDTO = null;
      int index = 0;
      ArrayList<ReplyDTO> replies = new ArrayList<ReplyDTO>();
      
      connection = DBConnecter.getConnection();
      try {
         preparedStatement = connection.prepareStatement(query);
         resultSet = preparedStatement.executeQuery();
         
         while(resultSet.next()) {
            index = 0;
            replyDTO = new ReplyDTO();
            replyDTO.setReplyCount(resultSet.getLong(++index));
            replyDTO.setReplyId(resultSet.getLong(++index));
            replyDTO.setReplyContent(resultSet.getString(++index));
            replyDTO.setUserId(resultSet.getLong(++index));
            replyDTO.setBoardId(resultSet.getLong(++index));
            replyDTO.setReplyRegisterDate(resultSet.getString(++index));
            replyDTO.setReplyUpdateDate(resultSet.getString(++index));
            replyDTO.setReplyGroup(resultSet.getLong(++index));
            replyDTO.setReplyDepth(resultSet.getLong(++index));
            replyDTO.setUserIdentification(resultSet.getString(++index));
            replyDTO.setUserName(resultSet.getString(++index));
            replyDTO.setUserPassword(resultSet.getString(++index));
            replyDTO.setUserPhone(resultSet.getString(++index));
            replyDTO.setUserNickname(resultSet.getString(++index));
            replyDTO.setUserEmail(resultSet.getString(++index));
            replyDTO.setUserAddress(resultSet.getString(++index));
            replyDTO.setUserBirth(resultSet.getString(++index));
            replyDTO.setUserGender(resultSet.getString(++index));
            replyDTO.setUserRecommenderId(resultSet.getString(++index));
            replies.add(replyDTO);
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
      return replies;
   }
   
//   댓글 삭제
//   댓글 수정
}
