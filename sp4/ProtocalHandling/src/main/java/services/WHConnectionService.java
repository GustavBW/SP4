package services;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface WHConnectionService {
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
