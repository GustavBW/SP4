package g7.sp4.protocolHandling;

import g7.sp4.common.models.WHItem;
import g7.sp4.common.models.WHStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WHConnectionService {


   public List<WHItem> getInventory();
   WHStatus getStatus();
   Flag prepareItem();
}
