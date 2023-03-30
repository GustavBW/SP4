import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface WarehouseConnector {
    Array WarehouseItems[];
    Connection con;
    String selectStatement = "SELECT * FROM Components";
    try(Statement stmt = con.createStatement())

    {
        ResultSet rs = stmt.executeQuery(selectStatement);
        while (rs.next()) {
            WarehouseItems.add();
        }
    }

   public WarehouseItems[] getInventory();


    Flag prepareItem();
}
