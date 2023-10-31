import java.sql.*;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
        	int choice;
        	connection = DriverManager.getConnection("jdbc:mysql://192.168.56.105:4567/madang", "jong", "dl994550");

            while (true) {
                System.out.println("1. 데이터 삽입");
                System.out.println("2. 데이터 검색");
                System.out.println("3. 데이터 삭제");
                System.out.println("4. 종료");
                System.out.print("원하는 작업을 선택하세요: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("번호, 책이름, 출판사, 가격을 차례대로 입력하세요: ");
                        int bookid = scanner.nextInt();
                        String bookname = scanner.next();
                        String publisher = scanner.next();
                        int price = scanner.nextInt();
                        insertData(connection, bookid,bookname,publisher,price);
                        break;
                    case 2:
                        searchData(connection);
                        break;
                    case 3:
                        System.out.print("삭제할 책번호를 입력하세요: ");
                        int i = scanner.nextInt();
                        deleteData(connection, i);
                        break;
                    case 4:
                        System.out.println("프로그램을 종료합니다.");
                        break;
                    default:
                        System.out.println("올바른 선택이 아닙니다. 다시 시도하세요.");
                        break;
                }

                if (choice == 4) {
                    break; // 4를 선택하면 반복 루프를 종료합니다.
                }
            }
            // 데이터베이스에 연결
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 연결 닫기
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 데이터 삽입
    private static void insertData(Connection connection, int bookid, String bookname, String publisher, int price) throws SQLException {
        String insertSQL = "INSERT INTO Book (bookid, bookname, publisher, price) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setInt(1, bookid);
            statement.setString(2, bookname);
            statement.setString(3, publisher);
            statement.setInt(4, price);
            statement.executeUpdate();
            System.out.println("데이터 삽입 완료");
        }
    }

    // 데이터 검색
    private static void searchData(Connection connection) throws SQLException {
        String selectSQL = "SELECT * FROM Book";

        try (PreparedStatement statement = connection.prepareStatement(selectSQL);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
            	System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getInt(4));
            }
        }
    }

    // 데이터 삭제
    private static void deleteData(Connection connection, int bookid) throws SQLException {
        String deleteSQL = "DELETE FROM Book WHERE bookid = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, bookid);
            statement.executeUpdate();
            System.out.println("데이터 삭제 완료");
        }
    }
}
