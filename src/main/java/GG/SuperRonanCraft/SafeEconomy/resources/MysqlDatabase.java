package GG.SuperRonanCraft.SafeEconomy.resources;

import GG.SuperRonanCraft.SafeEconomy.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MysqlDatabase {
    //Database
    private String host, port, database, username, password, table = "Players";
    private Connection con;
    private Statement statement;
    //Information
    private List<String> uuids = new ArrayList<String>();
    private HashMap<String, Integer> id = new HashMap<String, Integer>();
    private HashMap<String, Integer> balance = new HashMap<String, Integer>();

    public void load() {
        FileConfiguration config = Main.getInstance().getConfig();
        String pre = "Mysql.";
        this.host = config.getString(pre + "host");
        this.port = config.getString(pre + "port");
        this.database = config.getString(pre + "database");
        this.username = config.getString(pre + "username");
        this.password = config.getString(pre + "password");
        setupSql();
    }

    private void setupSql() {
        try {
            openConnection();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (ID INT, Uuid VARCHAR(36) UNIQUE, " +
                    "Balance INT UNSIGNED, PRIMARY KEY (ID)");
            sqlLoadAll();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(Main.getInstance().getMsg().colorPre("%prefix%&cFailed to bind to "
                    + "MySQLDatabase Database! Make sure your information is correct!"));
        }
    }

    public void addBalance(Player p, int amount) {
        String uuid = p.getUniqueId().toString();
        if (!uuids.contains(p.getUniqueId().toString()))
            createPlayer(uuid);
        else if (!balance.containsKey(uuid))
            balance.put(uuid, 0);
        int newBal = balance.get(uuid) + amount;
        int id = this.id.get(uuid);
        balance.put(uuid, newBal);
        try {
            statement.executeUpdate("UPDATE " + table + " SET Balance = " + newBal + " WHERE ID = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getBalance(Player p) {
        String uuid = p.getUniqueId().toString();
        if (!uuids.contains(p.getUniqueId().toString()))
            createPlayer(uuid);
        else if (!balance.containsKey(uuid))
            balance.put(uuid, 0);
        return balance.get(uuid);
    }

    private void createPlayer(String uuid) {
        int newId = this.id.get(uuid) + 1;
        try {
            statement.executeUpdate("INSERT INTO " + table + " (ID , Uuid, Balance) VALUES" + " (" + newId + ", " +
                    "'" + uuid + "', " + 0 + ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadPlayer(String uuid) {
        if (!uuids.contains(uuid))
            createPlayer(uuid);
        else
            sqlUploadPlayer(uuid);
    }

    public void downloadPlayer(String uuid) {
        if (!uuids.contains(uuid))
            createPlayer(uuid);
        else
            sqlLoadPlayer(uuid);
    }

    private void sqlLoadAll() {
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM " + table);
            while (result.next()) {
                String id = result.getString("Uuid");
                this.uuids.add(id);
                this.id.put(id, result.getInt("ID"));
                try {
                    balance.put(id, result.getInt("Balance"));
                } catch (NullPointerException e) {
                    // balance.put(ID, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sqlLoadPlayer(String uuid) {
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM " + table + " WHERE Uuid = " + uuid);
            if (result.next()) {
                String id = result.getString("Uuid");
                this.uuids.add(id);
                this.id.put(id, result.getInt("ID"));
                try {
                    balance.put(id, result.getInt("Balance"));
                } catch (NullPointerException e) {
                    // balance.put(ID, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sqlUploadPlayer(String uuid) {
        try {
            statement.executeUpdate("UPDATE " + table + " SET Balance = " + balance.get(uuid) + " WHERE Uuid = " +
                    uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openConnection() throws SQLException, ClassNotFoundException {
        if (con != null && !con.isClosed())
            return;
        synchronized (this) {
            if (con != null && !con.isClosed())
                return;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                    "?autoReconnect=true&useSSL=false", this.username, this.password);
            statement = con.createStatement();
        }
    }

    public void disconnect() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
