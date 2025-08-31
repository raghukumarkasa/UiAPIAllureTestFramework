package utilities.restAssuredExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

//import lombok.Getter;

public class SpecificationLogger {


    //@Getter
    private String requestData = "";

    //@Getter
    private String responseData = "";

    public String getRequestData() {
        return requestData;
    }

    public String getResponseData() {
        return responseData;
    }
    protected PrintStream getStream(String specificationName) {
        OutputStream outputstream = new OutputStream() {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }

            @Override
            public void flush() {
                if (!this.baos.toString().trim().isEmpty()) {
                    if (specificationName.toLowerCase().contains("request"))
                        requestData = requestData + this.baos.toString().trim();
                    else
                        responseData = responseData + this.baos.toString().trim();
                }
            }
        };
        return new PrintStream(outputstream,true);
    }
}
