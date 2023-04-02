package g7.sp4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Sp4Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Sp4Application.class, args);
		System.out.println("I'm ALIVE yall");
	}

}
