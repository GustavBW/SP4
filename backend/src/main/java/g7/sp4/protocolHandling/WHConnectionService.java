package g7.sp4.protocolHandling;

import g7.sp4.common.models.WHItem;

public interface WHConnectionService {


   public WHItem[] getInventory();
   Flag prepareItem();
}
