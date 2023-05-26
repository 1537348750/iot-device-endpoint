package org.lgq.iot.codec.utils;

public class HexEncodeUtil {

    // 3C3E3C3E "<><>"的十六进制，分隔符 用于不同属性之间的分割
    // 注意：分隔符必须确定为上报数据中不会出现的字符串
    public static final String Delimiter = "3C3E3C3E";


    /**
     * 获取ServiceId=LgqService的上报数据编码
     * @param id   属性1
     * @param id_d 属性2
     * @param id_s 属性3
     * @return 上报数据的十六进制编码
     */
    public static String encodeLgqService(int id, double id_d, String id_s) {
        // 3C3E3C3E(原为<><>)是数据分割标识，再解码的时候根据分割标识符分割各个数据
        // AA720000 {id} 3C3E3C3E {id_d} 3C3E3C3E {id_s}

        String head = "AA720000"; // LgqService的标识头
        StringBuilder template = new StringBuilder();
        template.append(head)
                .append("{id}").append(Delimiter)
                .append("{id_d}").append(Delimiter)
                .append("{id_s}");

        // 属性1: id
        String id_int = String.valueOf(id);
        String id_int_hex = Utilty.bytes2HexStr(id_int.getBytes());
        // 属性2: id_d
        String id_double = String.valueOf(id_d);
        String id_double_hex = Utilty.bytes2HexStr(id_double.getBytes());
        // 属性3: id_s
        String id_s_hex = Utilty.bytes2HexStr(id_s.getBytes());

        return template.toString()
                .replace("{id}", id_int_hex)
                .replace("{id_d}", id_double_hex)
                .replace("{id_s}", id_s_hex)
                .toUpperCase();
    }
}
