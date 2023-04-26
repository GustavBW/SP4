package g7.sp4.protocolHandling;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.WHItem;
import g7.sp4.common.models.WHStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WHConnectionService {


   //autoStore()
   void autoStore(Part part) throws Exception;

   void autoStore(Component component) throws Exception;

   public List<WHItem> getInventory() throws Exception;
   WHStatus getStatus() throws Exception;
   Flag prepareItem();
}
