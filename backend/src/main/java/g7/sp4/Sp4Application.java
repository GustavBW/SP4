package g7.sp4;

import g7.sp4.batchProcessing.ProcessController;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.repositories.*;
import g7.sp4.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Sp4Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Sp4Application.class, args);

		ProcessController controller = new ProcessController(
				context.getBean(AGVConnectionService.class),
				context.getBean(AssmConnectionService.class),
				context.getBean(WHConnectionService.class),
				context.getBean(IIngestService.class),
				context.getBean(IEventLoggingService.class)
				);
		controller.start();

		new DBLoader(
				context.getBean(IComponentService.class),
				context.getBean(IRecipeService.class),
				context.getBean(IPartService.class)
		).run();

		System.out.println("I'm ALIVE yall");
	}

}
