package todolist.models;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import todolist.entities.UserEntity;

public class UserModel {
    private DataSource dataSource;
    public UserModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void CreateUser (UserEntity user) {
        String sql = "INSERT INTO user VALUES (?, ?, now())";
        
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement stat = connection.prepareStatement(sql)
        ) {
            stat.setString(1, user.getUserid());
            stat.setString(2, user.getPassword());
            stat.executeUpdate();
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void ChangePassword (UserEntity user) {
        String sql = "UPDATE user SET password = ? WHERE userid = ? ";

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, user.getConnection());
            statement.setString(2, user.getUserid());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public UserEntity [] FindAllUser () {
        String sql = "SELECT = FROM USER";

        try(
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
            ) {
                List<UserEntity> list = new ArrayList<>();
                while (ResultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setUserid(resultSet.getString("userid"));
                    list.add(user);
                    }
            return list.toArray(new UserEntity[]{});
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
