package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerPrinter {
    private static final Logger logger = LogManager.getLogger(LoggerPrinter.class);

    public static void print(String message, OutputMode mode){
        switch(mode) {
            case LOG:       logger.info(message);
                            break;

            case CONSOLE:   System.out.println(message);
                            break;

            case BOTH:      logger.info(message);
                            System.out.println(message);
                            break;

            case DEBUG:     logger.debug(message);
                            break;

            default:        logger.info(message);

        }
    }
}
