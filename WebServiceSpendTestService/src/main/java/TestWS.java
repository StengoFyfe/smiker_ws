
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import de.pta.WebServiceSpendTest.services.interfaces.SpendTestService;


public class TestWS {

    public static void main(String args[]) throws Exception {

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(SpendTestService.class);
        factory.setAddress("http://localhost:9580/WebServiceSpendTest/SpendTestServiceWS");
        SpendTestService client = (SpendTestService) factory.create();

        System.out.println("Server said: " + client.getAllStandorte());

    }

}