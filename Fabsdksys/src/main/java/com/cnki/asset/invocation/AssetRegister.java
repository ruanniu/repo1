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

import com.cnki.asset.domain.Assigner;
import com.cnki.asset.domain.Constraint;
import com.cnki.asset.domain.Ownerrights;
import com.cnki.asset.domain.Pkr;
import com.cnki.asset.domain.Rightitem;
import com.cnki.asset.domain.Rights;
import com.cnki.asset.proposal.RegisterProposal;

import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

//资产登记
public class AssetRegister {
	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	public static void main(String args[]) {
		try {
			//先清理缓存到当前工程目录下users目录中的用户本地序列化数据
           //Util.cleanUp();
				
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
			Peer peer2 = fabClient.getInstance().newPeer(Config.ORG2_PEER_0, Config.ORG2_PEER_0_URL);			
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer); //添加背书节点,此处添加了2个背书节点,背书节点要同创建
			channel.addPeer(peer2);			
			channel.addOrderer(orderer); //添加排序服务器
			channel.initialize();

			TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
			request.setChaincodeID(ccid);
									
			request.setFcn("assetRegister");
									
			//构建资产登记议案
			RegisterProposal proposal = createRegisterPraposal();
								  
			JsonConfig jsonConfig = new JsonConfig();
			//指定哪些属性不需要转json：排除哪些属性
			jsonConfig.setExcludes(new String[]{""});
			//java对象转换为json对象在转变成json字符串
			String json = JSONObject.fromObject(proposal,jsonConfig).toString();
						
			
			String[] arguments = new String[1];
			arguments[0] = 	json;		
			request.setArgs(arguments);
			request.setProposalWaitTime(100000);

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
				if(status.getStatus() == 500){
					System.out.println("操作失败："+res.getMessage());
					return ;
				}
				
				Logger.getLogger(InvokeChaincode.class.getName()).log(Level.INFO,"Invoked assetRegister on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			    System.out.println("返回负载Payload："+new String(res.getChaincodeActionResponsePayload()));
			}
									
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("响应异常："+e.getMessage());
		}
	}

	private static RegisterProposal createRegisterPraposal() {
		//构建拥有者权利对象
		Ownerrights ownerrights = new Ownerrights();
		
		//1、设置基本信息:指明知产标识等
		ownerrights.setPkrai("pkrai101");//设置资产标识
		ownerrights.setVersion("1.0");//设置版本
		ownerrights.setTimeStamp("2019-1-10");//设置时间戳
		ownerrights.setSignature("signature101");//设置数字签名
		
		//2、设置专业知识资源对象：说明资源名称和id
		Pkr pkr = new Pkr();
		pkr.setId("pkr101");//设置资源的id
		pkr.setTitle("封神演义"); //设置资源标题
		pkr.setFingerPrint("zhiwen");//设置资源指纹
		ownerrights.setPkr(pkr);
				
		//权利项的约束为2019年有效，地区不限制
		Constraint constraint = new Constraint();
		constraint.setStart("2019-01-01");
		constraint.setEnd("2019-12-30");
		constraint.setRegion("*");
			
		List<Rightitem> rightitems = new ArrayList<>();
		//原创资产登记13项固定权利添加进来
		for(int i=0;i<13;i++){
			Rightitem item = new Rightitem();
			item.setRightType(i);
			rightitems.add(item);
		}
						
		//3、设置权利对象：指明如上资源有哪些权利		
		Rights rights = new Rights();
		rights.setRightitems(rightitems);		
		rights.setConstraint(constraint);
		ownerrights.setRights(rights);
		
		//4、设置拥有者对象
		Assigner owner = new Assigner("cnkiid","知网", "lily", "12345678", "东升科技园");
		ownerrights.setOwner(owner);
		
		//交易类型（登记）11、确权标识12、资产标识13、拥有者权利描述14、[证明文件15]
		RegisterProposal proposal = new RegisterProposal();
		proposal.setTrtype("登记"); //设置交易类型为：登记
		proposal.setPkri("pkri101"); //设置确权标识：即资产确定权利的唯一身份标记
		proposal.setPkrai(ownerrights.getPkrai());//设置资产标识
		proposal.setOwnerrights(ownerrights);//设置拥有者权利描述对象
		proposal.setCertificate("certificate101");//设置证明文件
		
		return proposal;
	}

}
