package com.ipaylinks.test.mock.server.api.ae;

import org.jpos.iso.IFE_LLLCHAR;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

public class IFE_LLLCHAR_AE extends IFE_LLLCHAR {

    public IFE_LLLCHAR_AE() {
        super();
    }

    /**
     * @param len         - field len
     * @param description symbolic descrption
     */
    public IFE_LLLCHAR_AE(int len, String description) {
        checkLength(len, 999);
    }

    public void setLength(int len) {
        checkLength(len, 999);
        super.setLength(len);
    }

    @Override
    public byte[] pack(ISOComponent c) throws ISOException {
        try {
            String data;
            if (c.getValue() instanceof byte[])
                data = new String(c.getBytes(), ISOUtil.CHARSET); // transparent handling of complex fields
            else
                data = (String) c.getValue();

            if (data.length() > getLength()) {
                throw new ISOException("Field length " + data.length() + " too long. Max: " + getLength());
            }
            String paddedData = String.format("%03d",data.length()) + data;
            String bitMap = data.substring(5, 9);
            byte[] pre = ISOUtil.asciiToEbcdic(paddedData.substring(0,8));
            byte[] after = ISOUtil.asciiToEbcdic(paddedData.substring(12));
            byte[] bitMapByte = bitMap.getBytes();
            byte[] empty = new byte[pre.length + bitMapByte.length + after.length];
            int i = 0;
            for( i = 0;i < pre.length;i++){
                empty[i] = pre[i];
            }
            for( ;i < pre.length + bitMapByte.length;i++){
                empty[i] = bitMapByte[i -  pre.length];
            }
            for( ;i < empty.length ;i++){
                empty[i] = after[i -  pre.length - bitMapByte.length];
            }
            return empty;
        } catch (Exception e) {
            throw new ISOException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        System.out.println(String.format("%03d",12));
    }
}
