package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.UserVO;

public class FollowDAO {
   public Connection connection; //연결 객체
   public PreparedStatement preparedStatement; //쿼리 관리 객체
   public ResultSet resultSet; //결과 테이블 객체
   
//   내가 팔로워한 사람들 조회
   public ArrayList<UserVO> selectMyFollower() {
      String query = "SELECT USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, "
            + "USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, "
            + "USER_GENDER, USER_RECOMMENDER_ID "
            + "FROM TBL_USER "
            + "WHERE USER_ID IN "
            + "(SELECT FOLLOWER_ID "
            + "   FROM TBL_FOLLOW "
            + "   WHERE FOLLOWING_ID = ?)";
      ArrayList<UserVO> users = new ArrayList<UserVO>();
      UserVO userVO = null;
      connection = DBConnecter.getConnection();
      
      try {
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setLong(1, UserDAO.userId);
         
         resultSet = preparedStatement.executeQuery();
         
         while(resultSet.next()) {
            userVO = new UserVO();
            userVO.setUserId(resultSet.getLong(1));
            userVO.setUserIdentification(resultSet.getString(2));
            userVO.setUserName(resultSet.getString(3));
            userVO.setUserPassword(resultSet.getString(4));
            userVO.setUserPhone(resultSet.getString(5));
            userVO.setUserNickname(resultSet.getString(6));
            userVO.setUserEmail(resultSet.getString(7));
            userVO.setUserAddress(resultSet.getString(8));
            userVO.setUserBirth(resultSet.getString(9));
            userVO.setUserGender(resultSet.getString(10));
            userVO.setUserRecommenderId(resultSet.getString(11));
            
            users.add(userVO);
         }
         
      } catch (SQLException e) {
         System.out.println("selectMyFollower() SQL문 오류");
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if(resultSet != null) {
               resultSet.close();
            }
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
      
      
      
      
      return users;
      
   }
   
//   다른 사람이 팔로워한 사람 조회
   public ArrayList<UserVO> selectGetFollower(Long followingId) {
      String query = "SELECT USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, "
            + "USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, "
            + "USER_GENDER, USER_RECOMMENDER_ID "
            + "FROM TBL_USER "
            + "WHERE USER_ID IN "
            + "(SELECT FOLLOWER_ID "
            + "   FROM TBL_FOLLOW "
            + "   WHERE FOLLOWING_ID = ?)";
      ArrayList<UserVO> users = new ArrayList<UserVO>();
      UserVO userVO = null;
      connection = DBConnecter.getConnection();
      
      try {
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setLong(1, followingId);
         
         resultSet = preparedStatement.executeQuery();
         
         while(resultSet.next()) {
            userVO = new UserVO();
            userVO.setUserId(resultSet.getLong(1));
            userVO.setUserIdentification(resultSet.getString(2));
            userVO.setUserName(resultSet.getString(3));
            userVO.setUserPassword(resultSet.getString(4));
            userVO.setUserPhone(resultSet.getString(5));
            userVO.setUserNickname(resultSet.getString(6));
            userVO.setUserEmail(resultSet.getString(7));
            userVO.setUserAddress(resultSet.getString(8));
            userVO.setUserBirth(resultSet.getString(9));
            userVO.setUserGender(resultSet.getString(10));
            userVO.setUserRecommenderId(resultSet.getString(11));
            
            users.add(userVO);
         }
         
      } catch (SQLException e) {
         System.out.println("selectMyFollower() SQL문 오류");
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if(resultSet != null) {
               resultSet.close();
            }
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
      
      
      
      
      return users;
   }
   
   
}