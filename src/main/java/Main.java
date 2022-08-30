import Service.GetService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j2
public class Main {

    public static void main(String[] args) {

        GetService getService = new GetService();
        getService.getCreditorBank();

    }
}
