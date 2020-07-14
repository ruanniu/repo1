package com.cnki.asset.usermanage;

import org.app.client.CAClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;

//登记认证管理员用户（引导节点用户）
public class EnrollAdminUser {
	public static void main(String args[]) {
		try {
			Util.cleanUp();
			String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
