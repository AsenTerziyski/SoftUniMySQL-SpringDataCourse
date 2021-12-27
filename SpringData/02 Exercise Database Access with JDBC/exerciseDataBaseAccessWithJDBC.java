import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class exerciseDataBaseAccessWithJDBC {

    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "minions_db";
    public static final String FULL_CONNECTION_STRING = CONNECTION_STRING + DB_NAME;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        connection = getConnection();
        System.out.println("Enter problem number [2,3,4,5,6,7,8 or 9]");
        int problemNumber = Integer.parseInt(reader.readLine());

        switch (problemNumber) {
            case 2:
                solveProblemTwo();
                connection.close();
                break;
            case 3:
                solveProblemThree();
                connection.close();
                break;
            case 4:
                solveProblemFour();
                connection.close();
                break;
            case 5:
                solveProblemFive();
                connection.close();
                break;
            case 6:
                solveProblemSix();
                connection.close();
                break;
            case 7:
                solveProblemSeven();
                connection.close();
                break;
            case 8:
                solveProblemEight();
                connection.close();
                break;
            case 9:
                solveProblemNine();
                connection.close();
                break;
            default:
                connection.close();
                break;
        }

    }

    private static void solveProblemNine() throws IOException, SQLException {
        System.out.println("Enter minion ID:");
        int minionID = Integer.parseInt(reader.readLine());
        CallableStatement callableStatement = connection.prepareCall("call usp_get_older(?)");
        callableStatement.setInt(1, minionID);
        int affectedRows = callableStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new IllegalArgumentException("Minion ID is not existing!");
        }
        PreparedStatement prepStMinionInfoByID =
                connection.prepareStatement("select name, age from minions where id = ?;");
        prepStMinionInfoByID.setInt(1, minionID);
        ResultSet resultSet = prepStMinionInfoByID.executeQuery();
        resultSet.next();
        String output = String.format("Minion name is %s, and its age after update is: %d ",
                resultSet.getString("name"),
                resultSet.getInt("age"));
        System.out.println(output);
    }

    private static void solveProblemEight() throws IOException, SQLException {
        System.out.println("Enter minions IDs:");
        String[] inputMinionsIDs = reader.readLine().split("\\s+");
        for (int i = 0; i < inputMinionsIDs.length; i++) {
            int currentMinionID = Integer.parseInt(inputMinionsIDs[i]);
            updateAgeByMinionID(currentMinionID);
        }
        PreparedStatement preparedStatement = connection
                .prepareStatement("select name, age from minions;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String output = String.format("%s %d", resultSet.getString(1), resultSet.getInt(2));
            System.out.println(output);
        }
    }

    private static void updateAgeByMinionID(int minionID) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("update minions set age = age+1 where id = ?;");
        preparedStatement.setInt(1, minionID);
        int i = preparedStatement.executeUpdate();
        if (i == 0) {
            String output = String.format("No entity with ID%d affected, because it doesnt exists!", minionID);
            System.out.println(output);
        }
    }

    private static void solveProblemSeven() throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("select name from minions");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> allMinionsNames = new ArrayList<>();
        while (resultSet.next()) {
            allMinionsNames.add(resultSet.getString(1));
        }
        int countOne = 0;
        int countTwo = allMinionsNames.size() - 1;
        for (int i = 0; i < allMinionsNames.size(); i++) {
            if (i % 2 == 0) {
                System.out.println(allMinionsNames.get(countOne));
                countOne++;
            } else {
                System.out.println(allMinionsNames.get(countTwo));
                countTwo--;
            }
        }
    }

    private static void solveProblemSix() throws IOException, SQLException {

        System.out.println("Enter villain ID: ");
        int villainID = Integer.parseInt(reader.readLine());

        int affectedEntities = deleteMinionsByVillainID(villainID);
        String villainName = findEntityNameById("villains", villainID);
        if (villainName !=null) {
            String output = String.format("%s was deleted%n%d minions released%n", villainName, affectedEntities);
            System.out.println(output);
        } else {
            System.out.println("No such villain was found");
        }
        deleteVillainByID(villainID);

    }

    private static boolean deleteVillainByID(int villainID) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("delete from villains where id=?");
        preparedStatement.setInt(1, villainID);
        boolean execute = preparedStatement.execute();
        return execute;
    }

    private static int deleteMinionsByVillainID(int villainID) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("delete from minions_villains where villain_id = ?");
        preparedStatement.setInt(1, villainID);
        int affected = preparedStatement.executeUpdate();
        return affected;
    }

    private static void solveProblemFive() throws IOException, SQLException {
        System.out.println("Enter country name:");
        String countryName = reader.readLine();

        PreparedStatement preparedStatement = connection
                .prepareStatement("update towns set name = upper(name) where country = ?;");
        preparedStatement.setString(1, countryName);

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        PreparedStatement preparedStatement1 = connection
                .prepareStatement("select name from towns where country = ?;");
        preparedStatement1.setString(1, countryName);
        ResultSet resultSet = preparedStatement1.executeQuery();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (resultSet.next()) {
            sb.append(resultSet.getString("name"));
            sb.append(", ");
        }
        String output = sb.substring(0, sb.length() - 2);
        String format = String.format("%d town names were affected. ", affectedRows);
        System.out.println(format);
        System.out.println(output + "]");
    }

    private static void solveProblemFour() throws IOException, SQLException {
        System.out.println("Enter INPUT info:");
        String[] minionInfo = reader.readLine().split(": ")[1].split("\\s+");
        String minionName= minionInfo[0];
        int minionAge = Integer.parseInt(minionInfo[1]);
        String townName = minionInfo[2];
        String villainName = reader.readLine().split(": ")[1];

        int townID = getEntityIdByName(townName, "towns");
        if (townID < 0) {
            insertTown(townName);
            System.out.println(String.format("Town %s was added to the database.", townName));
        }
        townID = getEntityIdByName(townName, "towns");

        int villainID = getEntityIdByName(villainName, "villains");
        if (villainID < 0) {
            insertVillainIntoVillains(villainName);
            System.out.println(String.format("Villain %s was added to the database.", villainName));
        }
        villainID = getEntityIdByName(villainName, "villains");

        int minionID = getEntityIdByName(minionName, "minions");
        if (minionID < 0) {
            insertMinionIntoMinions(minionName, minionAge, townID);
            minionID = getEntityIdByName(minionName, "minions");
            setMinionToBeServant(minionID, villainID);
            System.out.println(String.format("Successfully added %s to be minion of %s.", minionName, villainName));
        }

    }

    private static void setMinionToBeServant(int minionID, int villainID) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("insert into minions_villains (minion_id, villain_id)\n" +
                        "values\n" +
                        "(?,?);");
        preparedStatement.setInt(1,minionID);
        preparedStatement.setInt(2,villainID);
        preparedStatement.execute();

    }

    private static void insertMinionIntoMinions(String minionName, int minionAge, int townID) throws SQLException {
        PreparedStatement preparedStatement =
                connection
                        .prepareStatement("insert into minions (name, age, town_id)\n" +
                "values\n" + "(?, ?, ?);");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2,minionAge);
        preparedStatement.setInt(3,townID);
        preparedStatement.execute();
    }

    private static void insertVillainIntoVillains(String villainName) throws SQLException {
        PreparedStatement preparedStatement =
                connection
                        .prepareStatement("insert into villains (name, evilness_factor)\n" +
                "values\n" + "(?, 'evil');");
        preparedStatement.setString(1, villainName);
        preparedStatement.execute();
    }

    private static void insertTown(String townName) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("insert into towns (name)\n" +
                        "values\n" +
                        "(?);");
        preparedStatement.setString(1, townName);
        preparedStatement.execute();
    }

    private static int getEntityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format("select id from %s where name = ?", tableName);
        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setString(1, entityName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    private static void solveProblemThree() throws IOException, SQLException {
        System.out.println("Enter villain id:");
        int villainID = Integer.parseInt(reader.readLine());
        String outputVillainName = getVillainName(villainID);
        if (outputVillainName == null) {
            System.out.println(String.format("No villain with ID %d exists in the database.", villainID));
            return;
        }

        PreparedStatement preparedStatement = connection
                .prepareStatement(
                "select m.name, m.age from minions as m\n" +
                        "join minions_villains mv on m.id = mv.minion_id\n" +
                        "join villains v on v.id = mv.villain_id\n" +
                        "where v.id = ?;");
        preparedStatement.setInt(1, villainID);
        ResultSet resultSet = preparedStatement.executeQuery();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append(outputVillainName).append(System.lineSeparator());
        while (resultSet.next()) {
            count++;
            String currentMinion = String.format("%d. %s %d", count, resultSet.getString(1), resultSet.getInt(2));
            sb.append(currentMinion).append(System.lineSeparator());
        }
        System.out.println(sb.toString().trim());
    }

    private static String getVillainName(int villainID) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("select name from villains as v where v.id = ?;");
        preparedStatement.setInt(1, villainID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return String.format("Villain %s", resultSet.getString(1));
        } else {
            return null;
        }
    }

    private static void solveProblemTwo() throws SQLException {
        PreparedStatement preparedStatement
                = connection.prepareStatement("select v.name, count(distinct mv.minion_id) as minionsCount" +
                " from villains as v " +
                "join minions_villains mv on v.id = mv.villain_id " +
                "group by v.name " +
                "having minionsCount > ?; ");

        preparedStatement.setInt(1, 15);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String output = String
                    .format("%s %d",
                            resultSet.getString(1),
                            resultSet.getInt(2));
            System.out.println(output);
        }
    }

    private static Connection getConnection() throws IOException, SQLException {
        System.out.println("Enter USER (not required) or just hit ENTER:");
        String userName = reader.readLine();
        if (userName == null || userName.trim().isEmpty()) {
            userName = "root";
        }
        System.out.println("Enter PASSWORD (required):");
        String passWord = reader.readLine();
        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", passWord);
        return DriverManager.getConnection(FULL_CONNECTION_STRING, properties);
    }

    private static String findEntityNameById(String tableName, int entityID) throws SQLException {
        String query = String.format("select name from %s where id = ?;", tableName);
        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setInt(1, entityID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return String.format("%s", resultSet.getString("name"));
        }
        return null;
    }
}
