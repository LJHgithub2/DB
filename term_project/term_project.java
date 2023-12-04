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
    static int user_info=-1;
    public static void main(String[] args) throws SQLException {
        //DB연결부 코드(git 업로드 생략)
        Scanner menu = new Scanner(System.in);
		Scanner id_string = new Scanner(System.in);
		int number = 0;
		while(true)
		{
			System.out.println("                                                            ");
			System.out.println("                                                            ");
			System.out.println("------------------------------------------------------------");
			System.out.println("              DBMS Control Panel using JDBC  "+user_info+"               ");
			System.out.println("------------------------------------------------------------");
			System.out.println("  1. db연결                      2. 회원관리               ");
			System.out.println("  3. 단어장 관리                 4. 단어 보기                   ");
			System.out.println("  5. 게임방 보기                                    ");
			System.out.println("                                99. quit                   ");
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
                    try {
                        connection = DriverManager.getConnection(jdbcUrl, username, password);
                        System.out.println("DB 연결 완료");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return;
                    }
                    break;

                case 2: 
                    System.out.print("번호를 선택하세요(1:회원조회, 2:회원생성,3:회원삭제, 4:로그인) :");
                    if(id_string.hasNext())
                        tempString = id_string.nextLine();
                        int intNumber2=0;
                    try {
                        intNumber2 = Integer.parseInt(tempString);
                        switch (intNumber2) {
                            case 1:
                                selectUSERData();
                                break;
                            case 2:
                                System.out.print("회원 정보를 입력하세요(회원 별명, 성별{F,M}, 전화번호, 회원 이름) :");
                                tempString = id_string.nextLine();
                                String[] userData = tempString.split(",");
                                insertUserData(userData[0].trim(),userData[1].trim(),userData[2].trim(),userData[3].trim());
                                break;
                            case 3:
                                System.out.print("삭제할 회원 번호를 입력하세요 :");
                                tempString = id_string.nextLine();
                                int intNumber = Integer.parseInt(tempString);
                                deleteUserData(intNumber);
                                break;
                            case 4:
                                System.out.print("로그인할 회원 번호를 입력하세요 :");
                                tempString = id_string.nextLine();
                                int intNumber3 = Integer.parseInt(tempString);
                                login_user(intNumber3);
                                break;

                            default:
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("유효한 정수 형식이 아닙니다.");
                        break;
                    }
                    if(!tempString.isBlank()) 
                    break;

                    
                case 3: 
                    if(user_info==-1) break;
                    System.out.print("번호를 선택하세요(1:단어장조회, 2:단어장생성,3:단어장삭제) :");
                    if(id_string.hasNext())
                        tempString = id_string.nextLine();
                        int intNumber3=0;
                    try {
                        intNumber3 = Integer.parseInt(tempString);
                        switch (intNumber3) {
                            case 1:
                                selectWORDBOOKData();
                                break;
                            case 2:
                                System.out.print("단어장 이름을 입력하세요 :");
                                tempString = id_string.nextLine();
                                insertWORDBOOKData(tempString.trim());
                                break;
                            case 3:
                                System.out.print("삭제할 단어장 이름을 입력하세요 :");
                                tempString = id_string.nextLine();
                                deleteWORDBOOKData(tempString);
                                break;

                            default:
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("유효한 정수 형식이 아닙니다.");
                        break;
                    }
                    if(!tempString.isBlank()) 
                    break;

                case 4: 
                    selectWORDData();
                    break;

                case 5: 
                    selectGAMEROOMData();
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
    public static void insertUserData(String Nick_name, String Sex,String Phone_num,String name) {
        try {
            String insertSQL = "INSERT INTO USER (Nick_name,"+
            "Sex,Phone_num,name) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            
            statement.setString(1, Nick_name);
            statement.setString(2, Sex);
            statement.setString(3, Phone_num);
            statement.setString(4, name);
            //statement.setInt(1, Phone_num);
            statement.executeUpdate();
            System.out.println("회원 가입 완료");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void insertWORDBOOKData(String Name) {
        try {
            String insertSQL = "INSERT INTO WORDBOOK (Name, User_num) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            
            statement.setString(1, Name);
            statement.setInt(2, user_info);
            //statement.setInt(1, Phone_num);
            statement.executeUpdate();
            System.out.println("단어장 생성 완료");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void login_user(int key) {
        String selectSQL = "SELECT * FROM USER where User_num = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user_info=key;
            }
            else System.out.println("회원 정보가 없습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void selectGAMEROOMData() {
        String selectSQL = "SELECT * FROM GAMEROOM";
        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)+"번방 "+resultSet.getString(6)+": "+resultSet.getInt(2)
                +"호스트: "+resultSet.getInt(3)+",2번유저 "+resultSet.getInt(4)
                +",3번 유저 "+resultSet.getInt(5)+",4번 유저 ");
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void selectWORDBOOKData() {
        String selectSQL = "SELECT * FROM WORDBOOK where User_num="+user_info;
        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1)+" "+resultSet.getInt(2)+
                " "+resultSet.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void selectWORDData() {
        String selectSQL = "SELECT User_num,Name, Word_spel,Word_mean FROM WORD";
        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2)+
                " {" + resultSet.getString(3)+" : "+resultSet.getString(4)+"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void selectUSERData() {
        String selectSQL = "SELECT * FROM USER";
        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2)+
                " "+resultSet.getString(3)
                +" "+resultSet.getInt(4)+" "+resultSet.getString(5)+
                " "+resultSet.getString(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteUserData(int key) {
        String deleteSQL = "DELETE FROM USER WHERE User_num = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setInt(1, key);
            statement.executeUpdate();
            System.out.println(key+"번 회원 삭제 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWORDBOOKData(String Name) {
        String deleteSQL = "DELETE FROM WORDBOOK WHERE Name = ? and User_num =?" ;
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setString(1, Name);
            statement.setInt(2, user_info);
            statement.executeUpdate();
            System.out.println(Name+"단어장 삭제 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}