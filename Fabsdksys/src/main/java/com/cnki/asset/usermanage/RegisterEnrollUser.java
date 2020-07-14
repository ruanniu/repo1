
package com.cnki.asset.usermanage;

import org.app.client.CAClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;

//注册认证普通用户
public class RegisterEnrollUser {

	public static void main(String args[]) {
		try {
			//构建CA客户端CAClient
			String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			
			//CAClient客户端需要引导节点（管理员）证书：从本地读取管理员证书
			UserContext adminUserContext = Util.readUserContext(Config.ORG1, Config.ADMIN);
			//CAClient客户端设置管理员用户证书：注册和认证其他用户需要引导节点证书		
			caClient.setAdminUserContext(adminUserContext);
			
			// Register and Enroll user to Org1MSP
			UserContext userContext = new UserContext();
			String name = "user2000";
			userContext.setName(name);
			userContext.setAffiliation(Config.ORG1);
			userContext.setMspId(Config.ORG1_MSP);
            
			//通过CAClient注册新用户，并返回认证需要用的密码
			String eSecret = caClient.registerUser(name, Config.ORG1);
			
			//通过CAClient客户端和返回的密码认证用户，并保存用户上下文在本地
			userContext = caClient.enrollUser(userContext, eSecret);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
