package g7.sp4.protocolHandling;

import g7.sp4.common.models.WHItem;
import org.springframework.stereotype.Service;

@Service
public interface WHConnectionService {


   public WHItem[] getInventory();
   Flag prepareItem();
}
