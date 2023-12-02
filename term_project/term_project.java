package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * term_project
 */
public class term_project {
    static Connection connection;
    public static void main(String[] args) throws SQLException {
        //DB연결부 코드(git 업로드 생략)
        Scanner menu = new Scanner(System.in);
		Scanner id_string = new Scanner(System.in);
		int number = 0;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
            e.printStackTrace();
            return;
        }
		while(true)
		{
			System.out.println("                                                            ");
			System.out.println("                                                            ");
			System.out.println("------------------------------------------------------------");
			System.out.println("              DBMS Control Panel using JDBC                 ");
			System.out.println("------------------------------------------------------------");
			System.out.println("  1. select                       2. insert                 ");
			System.out.println("  3. delete                       4.                     ");
			System.out.println("  5.                              6.                    ");
			System.out.println("                                 99. quit                   ");
			System.out.println("------------------------------------------------------------");
            System.out.print("Enter an integer: ");

            if(menu.hasNextInt()){
				number = menu.nextInt();
            }
            else {
                System.out.println("concentration!");
                break;
            }
			String tempString = "";

            switch(number) {
                case 1: 
                    selectData();
                    break;
                    
                case 2: 
                    insertData();
                    break;
            
                case 3: 
                    System.out.print("Delete key: ");
                    if(id_string.hasNext())
                        tempString = id_string.nextLine();
                        int intNumber=0;
                    try {
                        intNumber = Integer.parseInt(tempString);
                    } catch (NumberFormatException e) {
                        System.out.println("유효한 정수 형식이 아닙니다.");
                        break;
                    }
                    if(!tempString.isBlank()) 
                        deleteData(intNumber);
                    break;

                case 99: 
                    System.out.println("bye!");
                    menu.close();
                    id_string.close();
                    connection.close();
                    return;

                default: System.out.println("concentration!");
            }
                    
        }
    }
    public static void insertData() {
        try {
            String insertSQL = "INSERT INTO dept (dno, dname) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setInt(1, 4);
            statement.setString(2, "yes");
            statement.executeUpdate();
            System.out.println("데이터 삽입 완료");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void selectData() {
        String selectSQL = "SELECT * FROM dept";
        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteData(int key) {
        String deleteSQL = "DELETE FROM dept WHERE dno = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setInt(1, key);
            statement.executeUpdate();
            System.out.println("데이터 삭제 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}