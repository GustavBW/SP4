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
   Flag autoStore(Part part) ;

   Flag autoStore(Component component);

   public List<WHItem> getInventory();
   WHStatus getStatus();
   Flag prepareItem(Part part);
   Flag prepareItem(Component component);

   Flag prepareComponent(long id);
   Flag preparePart(long id);

   void loadComponents(List<Component> components);

   //Flag stashItem(Part part, int id);



}
