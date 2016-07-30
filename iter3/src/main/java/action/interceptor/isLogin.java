package action.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class isLogin extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String,Object> dataMap = new HashMap<String, Object>();
	
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		Map session = ai.getInvocationContext().getSession();
		if(session.containsKey("user"))
			return ai.invoke();
		dataMap.put("error", "login");
		return Action.LOGIN;
	}
	
}
