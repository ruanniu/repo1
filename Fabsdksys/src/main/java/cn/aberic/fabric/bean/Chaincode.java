package cn.aberic.fabric.bean;


//Fabric创建的chaincode信息，涵盖所属channel等信息
//智能合约同通道是一对一的，智能合约要指定通道
public class Chaincode {

    /** 当前将要访问的智能合约所属频道名称 */
    private String channelName; // ffetest
    /** 智能合约名称 */
    private String chaincodeName; // ffetestcc
    /** 智能合约安装路径 */
    private String chaincodePath; // github.com/hyperledger/fabric/xxx/chaincode/go/example/test
    /** 智能合约版本号 */
    private String chaincodeVersion; // 1.0
    /** 执行智能合约操作等待时间 */
    private int invokeWatiTime = 100000;
    /** 执行智能合约实例等待时间 */
    private int deployWatiTime = 120000;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChaincodeName() {
        return chaincodeName;
    }

    public void setChaincodeName(String chaincodeName) {
        this.chaincodeName = chaincodeName;
    }

    public String getChaincodePath() {
        return chaincodePath;
    }

    public void setChaincodePath(String chaincodePath) {
        this.chaincodePath = chaincodePath;
    }

    public String getChaincodeVersion() {
        return chaincodeVersion;
    }

    public void setChaincodeVersion(String chaincodeVersion) {
        this.chaincodeVersion = chaincodeVersion;
    }

    public int getInvokeWatiTime() {
        return invokeWatiTime;
    }

    public void setInvokeWatiTime(int invokeWatiTime) {
        this.invokeWatiTime = invokeWatiTime;
    }

    public int getDeployWatiTime() {
        return deployWatiTime;
    }

    public void setDeployWatiTime(int deployWatiTime) {
        this.deployWatiTime = deployWatiTime;
    }

}