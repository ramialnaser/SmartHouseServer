package com.SmartHouse.Database;

import com.SmartHouse.Models.Room;
import com.SmartHouse.ProperityPath.FilePath;
import com.SmartHouse.Models.Device;
import com.SmartHouse.Models.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBConnection {


    private Connection connection = null;
    private String url;
    private String user;
    private String dbPassword;


    public DBConnection() {
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream("db.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = prop.getProperty("database");
        user = prop.getProperty("user");
        dbPassword = prop.getProperty("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    url, user, dbPassword);

            System.out.println("connected");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emailExists(String email) {

        String query = "SELECT count(*) FROM user WHERE emailAddress = ?";
        int count = dbValidation(query, email);

        return count > 0;
    }

    private int dbValidation(String query, String col) {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, col);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }


    public boolean validateLogin(String email, String password) {
        int count = 0;
        String query = "SELECT count(*) FROM user WHERE emailAddress = ? && password = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("validate login failed.");
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count == 1;
    }

    public String getPassword(String email) {
        String password = "";
        String query = "SELECT password from user where emailAddress = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    password = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("get password failed.");
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;
    }


    public String getFullName(String emailAddress) {
        String fullName = "";
        String query = "SELECT concat(firstname,' ',lastname) Fullname FROM user where emailAddress = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, emailAddress);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    fullName = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("get fullName by email failed.");
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return fullName;
    }


    public boolean addUser(User user, String userPassword) {
        boolean status = true;
        String query = "INSERT INTO user (firstname,lastname, emailAddress,password, house_idhouse) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmailAddress());
            ps.setString(4, userPassword);
            ps.setInt(5, 1);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add user failed.");
            status = false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }

        return status;
    }

    public List<User> getUsers() {
        List<User> user = new ArrayList<>();

        String query = "select firstname,lastname, emailAddress from user";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            }
        } catch (SQLException ex) {
            System.out.println("get all users failed");
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public boolean deleteUser(String email) {
        boolean status = true;
        String query = "DELETE FROM user WHERE emailAddress = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("delete user failed.");
            status = false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }
    public boolean updatePassword(String emailAddress, String newPassword) {
        boolean status = true;
        String query = "UPDATE user SET password = ? WHERE emailAddress = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, emailAddress);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update password failed.");
            status = false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean addRoom(Room room) {
        boolean status = true;
        String query = "INSERT INTO room (roomName,house_idhouse) VALUES (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, room.getRoomName());
            ps.setInt(2, 1);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add device failed.");
            status = false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
        return status;
    }

    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT * FROM room;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rooms.add(new Room(rs.getInt(1), rs.getString(2)));
                }
            }
        } catch (SQLException ex) {
            System.out.println("get all devices failed");
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    public boolean deleteRoom(int roomId) {
        boolean status = true;
        String query = "DELETE FROM room WHERE idroom = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, roomId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("delete room failed.");
            status = false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean addDevice(Device device) {
        boolean status = true;
        String query = "INSERT INTO devices (deviceName, deviceStatus,room_idroom) VALUES (?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, device.getDeviceName());
            ps.setInt(2, device.getDeviceStatus());
            ps.setInt(3, device.getRoomId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add device failed.");
            status = false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
        return status;
    }

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<>();

        String query = "SELECT * FROM devices";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    devices.add(new Device(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getInt(4)));
                }
            }
        } catch (SQLException ex) {
            System.out.println("get devices failed");
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return devices;
    }

    public List<Integer> getDevicesId(){
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT iddevices FROM devices";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    ids.add(resultSet.getInt(1));
                }
            }
        }catch (SQLException ex){
            System.out.println("get devices' id failed");
            ex.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ids;
    }



    public boolean updateDeviceStatus(Device device) {
        boolean status = true;
        String query = "UPDATE devices SET deviceStatus = ? WHERE iddevices = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, device.getDeviceStatus());
            ps.setInt(2, device.getDeviceId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update device status failed.");
            status = false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public void updateDevice(int id, int status){
        String query = "UPDATE devices SET deviceStatus = ? WHERE iddevices = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update device status failed.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteDevice(int deviceId) {
        boolean status = true;
        String query = "DELETE FROM devices WHERE iddevices = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, deviceId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("delete device failed.");
            status = false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean sendEmail(String email) {
        boolean emailSent = false;
        Properties prop = new Properties();
        try (FileReader reader = new FileReader(new FilePath().filePath())) {
            prop.load(reader);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("sendEmailUsername"), prop.getProperty("sendEmailPassword"));
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("sendEmailUsername")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("PASSWORD");
            message.setText("Hello, " + getFullName(email) + "\nHere's your current password: " + new DBConnection().getPassword(email));
            Transport.send(message);
            emailSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return emailSent;
    }


}
