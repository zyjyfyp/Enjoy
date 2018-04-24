package com.hengyushop.db;

/**
 *
 * @author luotao
 * @version 1.0 2013-04-25
 */

public class Base64 {
	public static final String encode(byte[] d) {
		if (d == null) {
			return null;
		}
		byte[] data = new byte[d.length + 2];
		System.arraycopy(d, 0, data, 0, d.length);
		byte[] dest = new byte[data.length / 3 * 4];

		int sidx = 0;
		for (int didx = 0; sidx < d.length; didx += 4) {
			dest[didx] = (byte) (data[sidx] >>> 2 & 0x3F);
			dest[(didx + 1)] = (byte) (data[(sidx + 1)] >>> 4 & 0xF | data[sidx] << 4 & 0x3F);
			dest[(didx + 2)] = (byte) (data[(sidx + 2)] >>> 6 & 0x3 | data[(sidx + 1)] << 2 & 0x3F);
			dest[(didx + 3)] = (byte) (data[(sidx + 2)] & 0x3F);

			sidx += 3;
		}

		for (int idx = 0; idx < dest.length; ++idx) {
			if (dest[idx] < 26)
				dest[idx] = (byte) (dest[idx] + 65);
			else if (dest[idx] < 52)
				dest[idx] = (byte) (dest[idx] + 97 - 26);
			else if (dest[idx] < 62)
				dest[idx] = (byte) (dest[idx] + 48 - 52);
			else if (dest[idx] < 63)
				dest[idx] = 43;
			else {
				dest[idx] = 47;
			}

		}

		for (int idx = dest.length - 1; idx > d.length * 4 / 3; --idx) {
			dest[idx] = 61;
		}
		return new String(dest);
	}

	public static final byte[] decode(String str) {
		if (str == null) {
			return null;
		}
		return decode(str.getBytes());
	}

	public static final byte[] decode(byte[] data) {
		int tail = data.length;
		while (data[(tail - 1)] == 61) {
			--tail;
		}
		byte[] dest = new byte[tail - (data.length / 4)];

		for (int idx = 0; idx < data.length; ++idx) {
			if (data[idx] == 61)
				data[idx] = 0;
			else if (data[idx] == 47)
				data[idx] = 63;
			else if (data[idx] == 43)
				data[idx] = 62;
			else if ((data[idx] >= 48) && (data[idx] <= 57))
				data[idx] = (byte) (data[idx] - -4);
			else if ((data[idx] >= 97) && (data[idx] <= 122))
				data[idx] = (byte) (data[idx] - 71);
			else if ((data[idx] >= 65) && (data[idx] <= 90)) {
				data[idx] = (byte) (data[idx] - 65);
			}

		}

		int sidx = 0;
		int didx = 0;
		for (didx = 0; didx < dest.length - 2; didx += 3) {
			dest[didx] = (byte) (data[sidx] << 2 & 0xFF | data[(sidx + 1)] >>> 4 & 0x3);
			dest[(didx + 1)] = (byte) (data[(sidx + 1)] << 4 & 0xFF | data[(sidx + 2)] >>> 2 & 0xF);
			dest[(didx + 2)] = (byte) (data[(sidx + 2)] << 6 & 0xFF | data[(sidx + 3)] & 0x3F);

			sidx += 4;
		}

		if (didx < dest.length) {
			dest[didx] = (byte) (data[sidx] << 2 & 0xFF | data[(sidx + 1)] >>> 4 & 0x3);
		}
		if (++didx < dest.length) {
			dest[didx] = (byte) (data[(sidx + 1)] << 4 & 0xFF | data[(sidx + 2)] >>> 2 & 0xF);
		}
		return dest;
	}

	public static void main(String[] args) throws Exception {
		// String str = "閿燂拷;
		// byte[] buf = str.getBytes();
		// System.out.println("SRC=" + ToolBox.b2HEX(buf));
		//
		// String enc = encode(buf);
		// System.out.println(enc);
		// enc = "1qe4tr3wtu4yLjAw1Kqho8i3yM/Wp7i2x+u72Li0yta7+seusPzD3MLr";
		// byte[] dec = decode(enc);
		// System.out.println("DEC=" + ToolBox.b2HEX(dec));
		// System.out.println(new String(dec));

		// byte[] bb = decode(str);
		// String ss = new String(bb);
		// System.out.println("ss = " + ss);
		//
		// String
		// str1="instId=20000001&trace=1306052002172353&payDate=20130605&amount=1&payState=0&stlDate=20130605&bankId=WY111111";
		//
		// Sign.verifyCRTSign(str1,str);
	}
}
