package action.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import entity.User;

public class isAdmin extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		Map session = ai.getInvocationContext().getSession();
		System.out.println("auth check");
		if(session.containsKey("user"))
		{
			User user = (User) session.get("user");
			if(user.getRole().getName().equals("admin"))
				return ai.invoke();
			else
			{
				System.out.println("403");
				return "403";
			}
		}
		return Action.LOGIN;
	}
	
}
