import org.apache.log4j.Logger;

import com.dahuangit.seobi.proxy.controller.ProxyControllerTest;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HostInfo;
import com.dahuangit.util.net.http.HttpKit;

public class HttpKitTest {
	private static final Logger log = Log4jUtils.getLogger(
			"E:\\dahuang-workspace\\dahuangit\\seobi\\seobi-webapp\\src\\test\\resources\\log4j.properties",
			HttpKitTest.class);

	public static void main(String[] args) {
//		String url = "http://localhost:8080/seobi/spring/proxy/doGetMethodTest";
//		String url = "http://localhost:8080/seobi/spring/proxy/doPostMethodTest";
		String url = "http://myip.kkcha.com/s/5/index.php";

		HostInfo proxyHost = new HostInfo();
		proxyHost.setAddr("202.103.150.70");
		proxyHost.setPort(8088);

//		boolean is = HttpKit.isProxyServerHttpPostAvailable(proxyHost, url);
		boolean is = HttpKit.isProxyServerHttpGetAvailable(proxyHost, url);
		
		if(is) {
			log.debug("代理服务器get可用");
		} else {
			log.debug("代理服务器get不可用");
		}
//	   if(is) {
//		   log.debug("代理服务器post可用");
//	   } else {
//		   log.debug("代理服务器post不可用");
//	   }
	}

}
