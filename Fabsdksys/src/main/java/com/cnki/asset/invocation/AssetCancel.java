package com.cnki.asset.invocation;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.app.chaincode.invocation.InvokeChaincode;
import org.app.client.CAClient;
import org.app.client.ChannelClient;
import org.app.client.FabricClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;

import com.cnki.asset.domain.Assigner;
import com.cnki.asset.domain.Constraint;
import com.cnki.asset.domain.Ownerrights;
import com.cnki.asset.domain.Pkr;
import com.cnki.asset.domain.Rightitem;
import com.cnki.asset.domain.Rights;
import com.cnki.asset.proposal.CancelProposal;
import com.cnki.asset.proposal.RegisterProposal;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

//资产登记撤销
public class AssetCancel {
	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	public static void main(String args[]) {
		try {
			//先清理缓存到当前工程目录下users目录中的用户本地序列化数据
            Util.cleanUp();
			String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			
			//向组织org1的ca节点注册一个用户，成功后，并把这个用户序列化到当前工程的users目录中
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
			
			FabricClient fabClient = new FabricClient(adminUserContext);
			
			ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			Peer peer2 = fabClient.getInstance().newPeer(Config.ORG2_PEER_0, Config.ORG2_PEER_0_URL);			
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addPeer(peer2);			
			channel.addOrderer(orderer);
			channel.initialize();

			TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
			request.setChaincodeID(ccid);
									
			request.setFcn("assetCancel");
									
			//构建资产登记议案
			CancelProposal proposal = new CancelProposal("pkrai101", "登记撤销", "pkri100", "certificate100");
								  
			JsonConfig jsonConfig = new JsonConfig();
			//指定哪些属性不需要转json：排除哪些属性
			jsonConfig.setExcludes(new String[]{""});
			//java对象转换为json对象在转变成json字符串
			String json = JSONObject.fromObject(proposal,jsonConfig).toString();
						
			
			String[] arguments = new String[1];
			arguments[0] = 	json;		
			request.setArgs(arguments);
			request.setProposalWaitTime(5000);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); 																								
			tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); 
			tm2.put("result", ":)".getBytes(UTF_8));
			tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); 
			request.setTransientMap(tm2);
			
			//应该是几个背书节点返回几个response:查询一定不要用invoke，会产生数据集
			//所以此处返回的是response集合
			Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
			System.out.println(responses.size());
						
			for (ProposalResponse res: responses) {
				Status status = res.getStatus();
				
				Logger.getLogger(InvokeChaincode.class.getName()).log(Level.INFO,"Invoked createCar on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			    System.out.println("返回负载Payload："+new String(res.getChaincodeActionResponsePayload()));
			}
									
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("响应异常发送");
		}
	}	
}
