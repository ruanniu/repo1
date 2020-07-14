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
import com.cnki.asset.domain.Party;
import com.cnki.asset.domain.Pkr;
import com.cnki.asset.domain.Rightitem;
import com.cnki.asset.domain.Rights;
import com.cnki.asset.domain.TransactionRights;
import com.cnki.asset.proposal.PermitProposal;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

//权利许可交易
public class PermitTransact {

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
									
			request.setFcn("permitTransact");
			
			//构建资产登记议案
			PermitProposal proposal = createPermitProposal();
								  
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
				
				Logger.getLogger(InvokeChaincode.class.getName()).log(Level.INFO,"Invoked permitTransact on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			    System.out.println("返回负载Payload："+new String(res.getChaincodeActionResponsePayload()));
			}
									
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static PermitProposal createPermitProposal() {
		
		//构建交易权利描述对象
		TransactionRights transactionRights = new TransactionRights();
		
		//1、先设置交易权利描述：基本信息
		transactionRights.setPkri("Pkri1001");//专业知识资源确权标识
		transactionRights.setVersion("1.0");//设置版本
		transactionRights.setTransactionType("许可"); //设置交易类型
		transactionRights.setTimeStamp("2019-03-30");//设置时间戳
		transactionRights.setSignature("Signature65765");//设置签名
		
		//2、交易权利描述：资源对象
		Pkr pkr = new Pkr();
		pkr.setId("pkr101");//设置资源的id
		pkr.setTitle("封神演义"); //设置资源标题
		pkr.setFingerPrint("zhiwen");//设置资源指纹
		transactionRights.setPkr(pkr);
		
		//3、交易权利描述：交易主体
		Party party =new Party();
		Assigner assignor = new Assigner("cnkiid","知网", "lily", "12345678", "东升科技园");//权利发布者实体		
		Assigner assignee = new Assigner("tongfangid","同方", "david", "8888", "同方科技园"); //权利获取者
		party.setAssignor(assignor);
		party.setAssignee(assignee);
		transactionRights.setParty(party);
		
		//3、交易权利描述：交易的权利对象
		Rights rights = new Rights();
		//权利项的约束为2019年有效，地区不限制
		Constraint constraint = new Constraint();
		constraint.setStart("2019-01-01");
		constraint.setEnd("2019-12-30");
		constraint.setRegion("*");
		rights.setConstraint(constraint);
		
		List<Rightitem> rightItems = new ArrayList<>();
		//构建复制权权利项
		Rightitem item = new Rightitem();
		item.setRightType(0);
		item.setConstraint(constraint);		
		rightItems.add(item);
		
		//构建发行权权利项
		Rightitem item1 = new Rightitem();
		item1.setRightType(1);
		item1.setConstraint(constraint);		
		rightItems.add(item1);
		rights.setRightitems(rightItems);				
		transactionRights.setRights(rights);
		
		//构建许可议案：交易类型（许可）21、确权标识22、发布资产标识23、获取资产标识24、交易权利描述25、[证明文件26]
		PermitProposal proposal = new PermitProposal();
		proposal.setTrtype(transactionRights.getTransactionType());//设置交易类型为：许可
		proposal.setPkri(transactionRights.getPkri()); //设置确权标识
		proposal.setFpkrai("pkrai101"); // 设置发布资产标识：此处为资产登记的资产标识pkrai101
		proposal.setTpkrai("tpkrai101"); //获取资产标识
		proposal.setPkri("pkri201");//确权标识
		proposal.setTransactionRights(transactionRights);//设置交易权利描述对象
		proposal.setCertificate("certificate100"); //设置证明文件
		
		return proposal;
	}


}
