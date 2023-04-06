package g7.sp4;

import g7.sp4.batchProcessing.ProcessController;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Sp4Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Sp4Application.class, args);

		new ProcessController(
				context.getBean(AGVConnectionService.class),
				context.getBean(AssmConnectionService.class),
				context.getBean(WHConnectionService.class)
				);

		System.out.println("I'm ALIVE yall");
	}

}