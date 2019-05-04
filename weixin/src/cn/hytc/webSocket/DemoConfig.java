package cn.hytc.webSocket;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class DemoConfig implements ServerApplicationConfig{

	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scan) {
		return scan;
	}

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(
			Set<Class<? extends Endpoint>> scanned) {
		return null;
	}

}
