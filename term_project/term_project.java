package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * term_project
 */
public class term_project {
    public static void main(String[] args) throws SQLException {
            //DB연결부 코드(git 업로드 생략)


        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            String insertSQL = "INSERT INTO dept (dno, dname) VALUES (?,?)";
            System.out.println("데이터베이스에 연결되었습니다!");
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setInt(1, 3);
            statement.setString(2, "hi");
            statement.executeUpdate();
            System.out.println("데이터 삽입 완료");
            connection.close(); // 작업이 완료되면 연결을 닫습니다
            // 여기서 데이터베이스 작업 수행
        } catch (SQLException e) {
            // SQLException을 처리합니다. 현재는 스택 트레이스를 출력합니다
            e.printStackTrace();
        }
    }
}