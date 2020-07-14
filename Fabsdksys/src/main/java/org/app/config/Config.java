package org.app.config;

import java.io.File;

public class Config {
	public static final String baseUrl ="192.168.25.130";
	public static final String ORG1_MSP = "Org1MSP";

	public static final String ORG1 = "org1";

	public static final String ORG2_MSP = "Org2MSP";

	public static final String ORG2 = "org2";

	public static final String ADMIN = "admin";

	public static final String ADMIN_PASSWORD = "adminpw";
	
	public static final String CHANNEL_CONFIG_PATH = "E:"+File.separator+"data"+File.separator+ "config/channel.tx";
	
	public static final String ORG1_USR_BASE_PATH ="E:"+File.separator+"data"+File.separator+ "crypto-config" + File.separator + "peerOrganizations" + File.separator
			+ "org1.example.com" + File.separator + "users" + File.separator + "Admin@org1.example.com"
			+ File.separator + "msp";
	
	public static final String ORG2_USR_BASE_PATH ="E:"+File.separator+"data"+File.separator+ "crypto-config" + File.separator + "peerOrganizations" + File.separator
			+ "org2.example.com" + File.separator + "users" + File.separator + "Admin@org2.example.com"
			+ File.separator + "msp";
	
	public static final String ORG1_USR_ADMIN_PK = ORG1_USR_BASE_PATH + File.separator + "keystore";
	public static final String ORG1_USR_ADMIN_CERT = ORG1_USR_BASE_PATH + File.separator + "admincerts";

	public static final String ORG2_USR_ADMIN_PK = ORG2_USR_BASE_PATH + File.separator + "keystore";
	public static final String ORG2_USR_ADMIN_CERT = ORG2_USR_BASE_PATH + File.separator + "admincerts";
	
	public static final String CA_ORG1_URL = "http://" + baseUrl + ":7054";
	
	public static final String CA_ORG2_URL = "http://" + baseUrl + ":8054";
	
	public static final String ORDERER_URL = "grpc://" + baseUrl + ":7050";
	
	public static final String ORDERER_NAME = "orderer.example.com";
	
	public static final String CHANNEL_NAME = "mychannel";
	
	public static final String ORG1_PEER_0 = "peer0.org1.example.com";
	
	public static final String ORG1_PEER_0_URL = "grpc://" + baseUrl+ ":7051";
	
	public static final String ORG1_PEER_1 = "peer1.org1.example.com";
	
	public static final String ORG1_PEER_1_URL = "grpc://" + baseUrl+ ":8051";
	
    public static final String ORG2_PEER_0 = "peer0.org2.example.com";
	
	public static final String ORG2_PEER_0_URL = "grpc://" + baseUrl+ ":9051";
	
	public static final String ORG2_PEER_1 = "peer1.org2.example.com";
	
	public static final String ORG2_PEER_1_URL = "grpc://" + baseUrl+ ":10051";
	
	
	
	public static final String CHAINCODE_1_NAME = "mycc";
	
	//两个字段指明链码在本地的绝对位置，以便上传本地的链码安装到peer上
	//由于linux系统的缘故：连码绝对位置=CHAINCODE_ROOT_DIR + "/src/" + CHAINCODE_1_PATH
    public static final String CHAINCODE_ROOT_DIR = "E:"+File.separator+"data";//链码在本地的根目录
	public static final String CHAINCODE_1_PATH = "chaincode/asset_chaincode"; //链码的目录
	//public static final String CHAINCODE_ROOT_DIR = "/opt/gopath";
	//public static final String CHAINCODE_1_PATH = "github.com/chaincode/asset_chaincode";
	public static final String CHAINCODE_1_VERSION = "1";


}
