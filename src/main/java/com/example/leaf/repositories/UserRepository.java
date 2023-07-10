package com.example.leaf.repositories;

import com.example.leaf.entities.ModelAI;
import com.example.leaf.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    Optional<User> findUserByVerificationCodeAndEmail(String verificationCode, String email);
    Optional<User> findUserByUsername(String username);
    @Query(nativeQuery = true, value ="SELECT * FROM user WHERE name like '%' :name '%' and role_id = 'CUSTOMER'")
    List<User> searchByName(@Param("name") String text);
    @Query(nativeQuery = true, value = "SELECT * FROM \n" +
            "(\n" +
            "\tSELECT M.user_id, M.item_id, (SUM(rating)/700) AS rating FROM\n" +
            "\t(\n" +
            "\t\t(\n" +
            "\t\t\tSELECT post.user AS user_id, reaction_post.user item_id, (COUNT(reaction_post.user)*5) AS rating\n" +
            "\t\t\tFROM ( leaf_db.reaction_post LEFT JOIN leaf_db.post ON reaction_post.post = post.id ) \n" +
            "\t\t\tWHERE reaction_post.user != post.user AND post.status = 'ENABLE' AND reaction_post.status='ENABLE'\n" +
            "\t\t\tGROUP BY reaction_post.user, post.user\n" +
            "\t\t) \n" +
            "\t\tUNION\n" +
            "\t\t(\n" +
            "\t\t\tSELECT post.user AS user_id, comment.user AS item_id, (COUNT(comment.user)*10) AS rating\n" +
            "\t\t\tFROM (leaf_db.comment LEFT JOIN leaf_db.post ON comment.post = post.id ) \n" +
            "\t\t\tWHERE comment.user != post.user AND post.status = 'ENABLE' AND comment.status='ENABLE'\n" +
            "\t\t\tGROUP BY comment.user, post.user\n" +
            "\t\t) \n" +
            "\t\tUNION\n" +
            "\t\t(\n" +
            "\t\t\tSELECT M1.user as user_id, M2.user as item_id, (COUNT(*)*20) as rating FROM \n" +
            "\t\t\t(\n" +
            "\t\t\t\t(SELECT T1.user , P1.friend FROM\n" +
            "\t\t\t\t\t(SELECT DISTINCT user_to as user FROM leaf_db.relationship) AS T1\n" +
            "\t\t\t\t\tLEFT JOIN\n" +
            "\t\t\t\t\t(SELECT user_to, user_from as friend FROM leaf_db.relationship WHERE status = 'FRIEND') AS P1\n" +
            "\t\t\t\t\tON T1.user = P1.user_to\n" +
            "\t\t\t\t)\n" +
            "\t\t\t\tUNION\n" +
            "\t\t\t\t(SELECT T2.user , P2.friend FROM\n" +
            "\t\t\t\t\t(SELECT DISTINCT user_from as user FROM leaf_db.relationship) AS T2\n" +
            "\t\t\t\t\tLEFT JOIN\n" +
            "\t\t\t\t\t(SELECT user_from, user_to as friend FROM leaf_db.relationship WHERE status = 'FRIEND') AS P2\n" +
            "\t\t\t\t\tON T2.user = P2.user_from\n" +
            "\t\t\t\t)\n" +
            "\t\t\t) AS M1\n" +
            "\t\t\tLEFT JOIN\n" +
            "\t\t\t(\n" +
            "\t\t\t\t(SELECT T1.user , P1.friend FROM\n" +
            "\t\t\t\t\t(SELECT DISTINCT user_to as user FROM leaf_db.relationship) AS T1\n" +
            "\t\t\t\t\tLEFT JOIN\n" +
            "\t\t\t\t\t(SELECT user_to, user_from as friend FROM leaf_db.relationship WHERE status = 'FRIEND') AS P1\n" +
            "\t\t\t\t\tON T1.user = P1.user_to\n" +
            "\t\t\t\t)\n" +
            "\t\t\t\tUNION\n" +
            "\t\t\t\t(SELECT T2.user , P2.friend FROM\n" +
            "\t\t\t\t\t(SELECT DISTINCT user_from as user FROM leaf_db.relationship) AS T2\n" +
            "\t\t\t\t\tLEFT JOIN\n" +
            "\t\t\t\t\t(SELECT user_from, user_to as friend FROM leaf_db.relationship WHERE status = 'FRIEND') AS P2\n" +
            "\t\t\t\t\tON T2.user = P2.user_from\n" +
            "\t\t\t\t)\n" +
            "\t\t\t) AS M2\n" +
            "\t\t\tON M1.user != M2.user AND M1.friend = M2.friend\n" +
            "\t\t\tGROUP BY M1.user , M2.user\n" +
            "\t\t)\n" +
            "\t) AS M\n" +
            "\tGROUP BY user_id, item_id\n" +
            ") AS MM\n" +
            "WHERE rating > (300/700)\n" +
            "ORDER BY user_id ASC, item_id ASC")
    List<ModelAI> getDataSourceForAI();
}
