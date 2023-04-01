package sp4.protocolHandling;


import sp4.common.WHItem;

public class WHConnector implements WHConnectionService {

    /*
    Array WarehouseItems[];
    Connection con;
    String selectStatement = "SELECT * FROM Components";
    try(
    Statement stmt = con.createStatement())

    {
        ResultSet rs = stmt.executeQuery(selectStatement);
        while (rs.next()) {
            WarehouseItems.add();
        }
    }
    */
    @Override
    public WHItem[] getInventory() {
        return new WHItem[0];
    }

    @Override
    public Flag prepareItem() {
        return null;
    }


}
