package org.app.user;


import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.app.chaincode.invocation.QueryChaincode;
import org.app.client.ChannelClient;
import org.app.client.FabricClient;
import org.app.config.Config;
import org.app.util.Util;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;

import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;


public class testGetUser {

	public static void main(String[] args) throws Exception {
		UserContext userContext = new UserContext();
		String name = "user2000";
		userContext.setName(name);
		userContext.setAffiliation(Config.ORG1);
		userContext.setMspId(Config.ORG1_MSP);
		userContext = Util.readUserContext(userContext.getAffiliation(), userContext.getName());
					
		FabricClient fabClient = new FabricClient(userContext);
		
		ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
		Channel channel = channelClient.getChannel();
		Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
		EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://"+Config.baseUrl+":7053");
		Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
		channel.addPeer(peer);
		//channel.addEventHub(eventHub);
		channel.addOrderer(orderer);
		channel.initialize();

		//***第六步：通过通道客户端实现链码查询
		Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode("mycc", "getUser", new String[] {""});
				
		for (ProposalResponse pres : responsesQuery) {
			//获取响应的负载：链码中放入到success中的内容：shim.Success(nil)
			Status status = pres.getStatus();
			if(status.getStatus() == 500){
				System.out.println("查询失败："+pres.getMessage());
				return ;
			}
			
			String stringResponse = new String(pres.getChaincodeActionResponsePayload());
			Logger.getLogger(QueryChaincode.class.getName()).log(Level.INFO, stringResponse);
			
	}				 

}
}
