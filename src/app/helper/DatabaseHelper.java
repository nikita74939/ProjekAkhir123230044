package app.helper;

import app.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:mysql://localhost/monstermates";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private Connection connection;
    private static DatabaseHelper instance;
    
    public DatabaseHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }
    
    // ================== PLAYER OPERATIONS ==================
    
    /**
     * Mendapatkan semua player (maksimal 4)
     */
    public ObservableList<Player> getAllPlayers() {
        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "SELECT * FROM players LIMIT 4";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Player player = new Player(
                    rs.getInt("id_player"),
                    rs.getString("name"),
                    rs.getInt("level"),
                    rs.getInt("point_to_level_up"),
                    rs.getInt("gold")
                );
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return players;
    }
    
    /**
     * Membuat player baru
     */
    public boolean createPlayer(String name) {
        // Cek apakah sudah ada 4 player
        if (getAllPlayers().size() >= 4) {
            return false;
        }
        
        String sql = "INSERT INTO players (name, level, point_to_level_up, gold) VALUES (?, 1, 0, 100)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Menghapus player dan semua data terkaitnya
     */
    public boolean deletePlayer(int playerId) {
        String sql = "DELETE FROM players WHERE id_player = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update gold player
     */
    public boolean updatePlayerGold(int playerId, int newGold) {
        String sql = "UPDATE players SET gold = ? WHERE id_player = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newGold);
            stmt.setInt(2, playerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update level dan exp player
     */
    public boolean updatePlayerLevel(int playerId, int level, int pointToLevelUp) {
        String sql = "UPDATE players SET level = ?, point_to_level_up = ? WHERE id_player = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, level);
            stmt.setInt(2, pointToLevelUp);
            stmt.setInt(3, playerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mendapatkan player berdasarkan ID
     */
    public Player getPlayerById(int playerId) {
        String sql = "SELECT * FROM players WHERE id_player = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Player(
                    rs.getInt("id_player"),
                    rs.getString("name"),
                    rs.getInt("level"),
                    rs.getInt("point_to_level_up"),
                    rs.getInt("gold")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // ================== MONSTER OPERATIONS ==================
    
    /**
     * Mendapatkan semua monster
     */
    public ObservableList<Monster> getAllMonsters() {
        ObservableList<Monster> monsters = FXCollections.observableArrayList();
        String sql = "SELECT * FROM monsters";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Monster monster = new Monster(
                    rs.getInt("id_monster"),
                    rs.getString("name"),
                    rs.getInt("happy"),
                    rs.getInt("food")
                );
                monsters.add(monster);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return monsters;
    }
    
    /**
     * Mendapatkan 3 monster acak untuk pick friend
     */
    public List<Monster> getRandomMonsters() {
        List<Monster> allMonsters = new ArrayList<>(getAllMonsters());
        Collections.shuffle(allMonsters, new Random());
        
        return allMonsters.subList(0, Math.min(3, allMonsters.size()));
    }
    
    // ================== OWNER OPERATIONS ==================
    
    /**
     * Menambahkan monster sebagai friend player
     */
    public boolean addPlayerFriend(int playerId, int monsterId) {
        String sql = "INSERT INTO owner (id_player, id_monster, point_food, point_happy, point_levelup, level) VALUES (?, ?, 0, 0, 0, 1)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, monsterId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mendapatkan semua friend milik player
     */
    public ObservableList<Owner> getPlayerFriends(int playerId) {
        ObservableList<Owner> friends = FXCollections.observableArrayList();
        String sql = "SELECT * FROM owner WHERE id_player = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Owner owner = new Owner(
                    rs.getInt("id_owner"),
                    rs.getInt("id_player"),
                    rs.getInt("id_monster"),
                    rs.getInt("point_food"),
                    rs.getInt("point_happy"),
                    rs.getInt("point_levelup"),
                    rs.getInt("level")
                );
                friends.add(owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return friends;
    }
    
    /**
     * Update stats friend setelah diberi makan/mainan
     */
    public boolean updateFriendStats(int ownerId, int pointFood, int pointHappy, int pointLevelup, int level) {
        String sql = "UPDATE owner SET point_food = ?, point_happy = ?, point_levelup = ?, level = ? WHERE id_owner = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pointFood);
            stmt.setInt(2, pointHappy);
            stmt.setInt(3, pointLevelup);
            stmt.setInt(4, level);
            stmt.setInt(5, ownerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ================== PROPERTY OPERATIONS ==================
    
    /**
     * Mendapatkan semua property (item di shop)
     */
    public ObservableList<Property> getAllProperties() {
        ObservableList<Property> properties = FXCollections.observableArrayList();
        String sql = "SELECT * FROM property ORDER BY type, price";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Property property = new Property(
                    rs.getInt("id_property"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("type"),
                    rs.getInt("point"),
                    rs.getInt("exp")
                );
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return properties;
    }
    
    /**
     * Mendapatkan property berdasarkan tipe (food/toy)
     */
    public ObservableList<Property> getPropertiesByType(String type) {
        ObservableList<Property> properties = FXCollections.observableArrayList();
        String sql = "SELECT * FROM property WHERE type = ? ORDER BY price";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Property property = new Property(
                    rs.getInt("id_property"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("type"),
                    rs.getInt("point"),
                    rs.getInt("exp")
                );
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return properties;
    }
    
    // ================== INVENTORY OPERATIONS ==================
    
    /**
     * Mendapatkan inventory player
     */
    public ObservableList<Inventory> getPlayerInventory(int playerId) {
        ObservableList<Inventory> inventory = FXCollections.observableArrayList();
        String sql = "SELECT * FROM inventory WHERE id_player = ? AND stock > 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Inventory inv = new Inventory(
                    rs.getInt("id_inventory"),
                    rs.getInt("id_player"),
                    rs.getInt("id_property"),
                    rs.getInt("stock")
                );
                inventory.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return inventory;
    }
    
    /**
     * Mendapatkan inventory berdasarkan tipe item
     */
    public ObservableList<Inventory> getPlayerInventoryByType(int playerId, String type) {
        ObservableList<Inventory> inventory = FXCollections.observableArrayList();
        String sql = "SELECT i.* FROM inventory i JOIN property p ON i.id_property = p.id_property WHERE i.id_player = ? AND p.type = ? AND i.stock > 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Inventory inv = new Inventory(
                    rs.getInt("id_inventory"),
                    rs.getInt("id_player"),
                    rs.getInt("id_property"),
                    rs.getInt("stock")
                );
                inventory.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return inventory;
    }
    
    /**
     * Membeli item dari shop
     */
    public boolean buyItem(int playerId, int propertyId, int quantity) {
        try {
            connection.setAutoCommit(false);
            
            // Cek apakah player punya cukup gold
            Player player = getPlayerById(playerId);
            Property property = getPropertyById(propertyId);
            
            if (player == null || property == null) {
                connection.rollback();
                return false;
            }
            
            int totalCost = property.getPrice() * quantity;
            if (player.getGold() < totalCost) {
                connection.rollback();
                return false;
            }
            
            // Kurangi gold player
            String updateGoldSql = "UPDATE players SET gold = gold - ? WHERE id_player = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateGoldSql)) {
                stmt.setInt(1, totalCost);
                stmt.setInt(2, playerId);
                stmt.executeUpdate();
            }
            
            // Tambah item ke inventory
            String checkInventorySql = "SELECT * FROM inventory WHERE id_player = ? AND id_property = ?";
            try (PreparedStatement stmt = connection.prepareStatement(checkInventorySql)) {
                stmt.setInt(1, playerId);
                stmt.setInt(2, propertyId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    // Update existing inventory
                    String updateInventorySql = "UPDATE inventory SET stock = stock + ? WHERE id_player = ? AND id_property = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateInventorySql)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, playerId);
                        updateStmt.setInt(3, propertyId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Insert new inventory
                    String insertInventorySql = "INSERT INTO inventory (id_player, id_property, stock) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertInventorySql)) {
                        insertStmt.setInt(1, playerId);
                        insertStmt.setInt(2, propertyId);
                        insertStmt.setInt(3, quantity);
                        insertStmt.executeUpdate();
                    }
                }
            }
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Menggunakan item dari inventory
     */
    public boolean useItem(int playerId, int propertyId) {
        String sql = "UPDATE inventory SET stock = stock - 1 WHERE id_player = ? AND id_property = ? AND stock > 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, propertyId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mendapatkan property berdasarkan ID
     */
    public Property getPropertyById(int propertyId) {
        String sql = "SELECT * FROM property WHERE id_property = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, propertyId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Property(
                    rs.getInt("id_property"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("type"),
                    rs.getInt("point"),
                    rs.getInt("exp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // ================== MISSION OPERATIONS ==================
    
    /**
     * Mendapatkan semua mission
     */
    public ObservableList<Mission> getAllMissions() {
        ObservableList<Mission> missions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM missions ORDER BY type, id_mission";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Mission mission = new Mission(
                    rs.getInt("id_mission"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("type"),
                    rs.getInt("gold_reward"),
                    rs.getInt("exp_reward")
                );
                missions.add(mission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return missions;
    }
    
    /**
     * Mendapatkan daily missions untuk player
     */
    public ObservableList<DailyAssignment> getPlayerDailyMissions(int playerId) {
        ObservableList<DailyAssignment> dailyMissions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM daily_assignments WHERE id_player = ? AND date_given = CURDATE()";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DailyAssignment assignment = new DailyAssignment(
                    rs.getInt("id_assignment"),
                    rs.getInt("id_player"),
                    rs.getInt("id_mission"),
                    rs.getDate("date_given").toLocalDate(),
                    rs.getBoolean("completed"),
                    rs.getBoolean("claimed")
                );
                dailyMissions.add(assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dailyMissions;
    }
    
    /**
     * Generate daily missions untuk player (jika belum ada untuk hari ini)
     */
    public boolean generateDailyMissions(int playerId) {
        try {
            // Cek apakah sudah ada daily mission untuk hari ini
            String checkSql = "SELECT COUNT(*) FROM daily_assignments WHERE id_player = ? AND date_given = CURDATE()";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, playerId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Sudah ada daily mission untuk hari ini
                }
            }
            
            // Generate daily missions (ambil semua daily mission)
            String getMissionsSql = "SELECT id_mission FROM missions WHERE type = 'daily'";
            List<Integer> dailyMissionIds = new ArrayList<>();
            
            try (PreparedStatement stmt = connection.prepareStatement(getMissionsSql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dailyMissionIds.add(rs.getInt("id_mission"));
                }
            }
            
            // Insert daily assignments
            String insertSql = "INSERT INTO daily_assignments (id_player, id_mission, date_given, completed, claimed) VALUES (?, ?, CURDATE(), 0, 0)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                for (int missionId : dailyMissionIds) {
                    insertStmt.setInt(1, playerId);
                    insertStmt.setInt(2, missionId);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mendapatkan long-term missions untuk player
     */
    public ObservableList<PlayerMission> getPlayerLongTermMissions(int playerId) {
        ObservableList<PlayerMission> longTermMissions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM player_missions WHERE id_player = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PlayerMission mission = new PlayerMission(
                    rs.getInt("id_player_mission"),
                    rs.getInt("id_player"),
                    rs.getInt("id_mission"),
                    rs.getBoolean("completed"),
                    rs.getBoolean("claimed"),
                    rs.getTimestamp("completed_at") != null ? 
                        rs.getTimestamp("completed_at").toLocalDateTime() : null
                );
                longTermMissions.add(mission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return longTermMissions;
    }
    
    /**
     * Initialize long-term missions untuk player baru
     */
    public boolean initializeLongTermMissions(int playerId) {
        try {
            String getMissionsSql = "SELECT id_mission FROM missions WHERE type = 'long_term'";
            List<Integer> longTermMissionIds = new ArrayList<>();
            
            try (PreparedStatement stmt = connection.prepareStatement(getMissionsSql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    longTermMissionIds.add(rs.getInt("id_mission"));
                }
            }
            
            String insertSql = "INSERT INTO player_missions (id_player, id_mission, completed, claimed) VALUES (?, ?, 0, 0)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                for (int missionId : longTermMissionIds) {
                    insertStmt.setInt(1, playerId);
                    insertStmt.setInt(2, missionId);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Complete daily mission
     */
    public boolean completeDailyMission(int assignmentId) {
        String sql = "UPDATE daily_assignments SET completed = 1 WHERE id_assignment = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, assignmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Complete long-term mission
     */
    public boolean completeLongTermMission(int playerMissionId) {
        String sql = "UPDATE player_missions SET completed = 1, completed_at = NOW() WHERE id_player_mission = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playerMissionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Claim daily mission reward
     */
    public boolean claimDailyMissionReward(int assignmentId, int playerId, int goldReward, int expReward) {
        try {
            connection.setAutoCommit(false);
            
            // Update claimed status
            String updateMissionSql = "UPDATE daily_assignments SET claimed = 1 WHERE id_assignment = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateMissionSql)) {
                stmt.setInt(1, assignmentId);
                stmt.executeUpdate();
            }
            
            // Update player gold and exp
            String updatePlayerSql = "UPDATE players SET gold = gold + ?, point_to_level_up = point_to_level_up + ? WHERE id_player = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updatePlayerSql)) {
                stmt.setInt(1, goldReward);
                stmt.setInt(2, expReward);
                stmt.setInt(3, playerId);
                stmt.executeUpdate();
            }
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Claim long-term mission reward
     */
    public boolean claimLongTermMissionReward(int playerMissionId, int playerId, int goldReward, int expReward) {
        try {
            connection.setAutoCommit(false);
            
            // Update claimed status
            String updateMissionSql = "UPDATE player_missions SET claimed = 1 WHERE id_player_mission = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateMissionSql)) {
                stmt.setInt(1, playerMissionId);
                stmt.executeUpdate();
            }
            
            // Update player gold and exp
            String updatePlayerSql = "UPDATE players SET gold = gold + ?, point_to_level_up = point_to_level_up + ? WHERE id_player = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updatePlayerSql)) {
                stmt.setInt(1, goldReward);
                stmt.setInt(2, expReward);
                stmt.setInt(3, playerId);
                stmt.executeUpdate();
            }
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ================== UTILITY METHODS ==================
    
    /**
     * Tutup koneksi database
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Check database connection
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}