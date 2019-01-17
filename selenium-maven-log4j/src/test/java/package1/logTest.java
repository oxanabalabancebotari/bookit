package package1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class logTest {

	public static void main(String[] args) {
		Logger log= LogManager.getLogger(logTest.class.getName());
		log.debug("I am logging"); //When each selenium action is performed like click, send keys
		//we can use debug
		log.info("object is pressent");//when operation is succesfully completed
		//ex:After loading page
		log.debug("Starting"); 
		if(0>2) {
			log.info("object is present");
		}else {
			log.error("object is not present");
		}
		
		

	}

}
