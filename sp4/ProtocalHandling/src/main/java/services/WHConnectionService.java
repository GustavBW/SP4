package services;

import models.WHItem;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface WHConnectionService {


   public WHItem[] getInventory();
   Flag prepareItem();
}
