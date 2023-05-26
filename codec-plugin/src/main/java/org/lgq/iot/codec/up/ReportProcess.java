package org.lgq.iot.codec.up;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.lgq.iot.codec.utils.HexEncodeUtil;
import org.lgq.iot.codec.utils.Utilty;

public class ReportProcess {
    //private String identifier;

    private String msgType = "deviceReq";
    private int hasMore = 0;
    private int errcode = 0;
    private byte bDeviceReq = 0x00;
    private byte bDeviceRsp = 0x01;


    // serviceId = LgqService
    private int id = 0;
    private double id_d;
    private String id_s;


    private byte noMid = 0x00;
    private byte hasMid = 0x01;
    private boolean isContainMid = false;
    private int mid = 0;

    /**
     * @param binaryData 设备发送给平台coap报文的payload部分
     *                   本例入参：AA 72 00 00 32 08 8D 03 20 62 33 99
     *                   byte[0]--byte[1]:  AA 72 命令头
     *                   byte[2]:   00 mstType 00表示设备上报数据deviceReq
     *                   byte[3]:   00 hasMore  0表示没有后续数据，1表示有后续数据，不带按照0处理
     *                   byte[4]--byte[11]:服务数据，根据需要解析//如果是deviceRsp,byte[4]表示是否携带mid, byte[5]--byte[6]表示短命令Id
     * @return
     */
    public ReportProcess(byte[] binaryData) {
        // identifier参数可以根据入参的码流获得，本例指定默认值123
        // identifier = "123";
        /*
        如果是设备上报数据，返回格式为
        {
            "identifier":"123",
            "msgType":"deviceReq",
            "hasMore":0,
            "data":[{"serviceId":"Brightness",
                      "serviceData":{"brightness":50},
                      {
                      "serviceId":"Electricity",
                      "serviceData":{"voltage":218.9,"current":800,"frequency":50.1,"powerfactor":0.98},
                      {
                      "serviceId":"Temperature",
                      "serviceData":{"temperature":25},
                      ]
	    }
	    */
        if (binaryData[2] == bDeviceReq) {
            msgType = "deviceReq";
            hasMore = binaryData[3];

            // 获取数据正文内容，需要在编码的时候按顺序编写每个属性的值
            byte[] payload = new byte[binaryData.length - 4];
            System.arraycopy(binaryData, 4, payload, 0, binaryData.length - 4);
            String hexStr = Utilty.bytes2HexStr(payload).toUpperCase();
            String[] rawData = hexStr.split(HexEncodeUtil.Delimiter);

            // serviceId=LgqService 数据解析
            // hexString -> int
            String id = new String(Utilty.hexStrToBytes(rawData[0]));
            this.id = Integer.parseInt(id);
            // hexString -> double
            String id_d = new String(Utilty.hexStrToBytes(rawData[1]));
            this.id_d = Double.parseDouble(id_d);
            // hexString -> String
            this.id_s = new String(Utilty.hexStrToBytes(rawData[2]));

        }
        /*
        如果是设备对平台命令的应答，返回格式为：
       {
            "identifier":"123",
            "msgType":"deviceRsp",
            "errcode":0,
            "body" :{****} 特别注意该body体为一层json结构。
        }
	    */
        else if (binaryData[2] == bDeviceRsp) {
            msgType = "deviceRsp";
            errcode = binaryData[3];
            //此处需要考虑兼容性，如果没有传mId，则不对其进行解码
            if (binaryData[4] == hasMid) {
                mid = Utilty.getInstance().bytes2Int(binaryData, 5, 2);
                if (Utilty.getInstance().isValidofMid(mid)) {
                    isContainMid = true;
                }

            }
        } else {
            return;
        }
    }

    public ObjectNode toJsonNode() {
        try {
            //组装body体
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.createObjectNode();

            // root.put("identifier", this.identifier);
            root.put("msgType", this.msgType);

            //根据msgType字段组装消息体
            if (this.msgType.equals("deviceReq")) {
                root.put("hasMore", this.hasMore);
                ArrayNode arrynode = mapper.createArrayNode();

                // serviceId=LgqService 数据
                ObjectNode lgqServiceNode = mapper.createObjectNode();
                lgqServiceNode.put("serviceId", "LgqService");
                ObjectNode lgqServiceDate = mapper.createObjectNode();
                lgqServiceDate.put("id", this.id);
                lgqServiceDate.put("id_d", this.id_d);
                lgqServiceDate.put("id_s", this.id_s);
                lgqServiceNode.put("serviceData", lgqServiceDate);
                arrynode.add(lgqServiceNode);


                root.put("data", arrynode);
            } else {
                root.put("errcode", this.errcode);
                //此处需要考虑兼容性，如果没有传mid，则不对其进行解码
                if (isContainMid) {
                    root.put("mid", this.mid);//mid
                }
                //组装body体，只能为ObjectNode对象
                ObjectNode body = mapper.createObjectNode();
                body.put("result", 0);
                root.put("body", body);
            }
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}