package org.flywind.widgets.test.utils;


/**
 * 编、解码字符串的工具类.
 * 
 */
public class Base64 {
	/**
	 * No options specified. Value is zero.
	 */
	public final static int NO_OPTIONS = 0;

	/**
	 * Specify encoding.
	 */
	public final static int ENCODE = 1;

	/**
	 * Specify decoding.
	 */
	public final static int DECODE = 0;

	/**
	 * Specify that data should be gzip-compressed.
	 */
	public final static int GZIP = 2;

	/**
	 * Don't break lines when encoding (violates strict Base64 specification)
	 */
	public final static int DONT_BREAK_LINES = 8;

	/**
	 * Maximum line length (76) of Base64 output.
	 */
	private final static int MAX_LINE_LENGTH = 76;

	/**
	 * The equals sign (=) as a byte.
	 */
	private final static byte EQUALS_SIGN = (byte) '=';

	/**
	 * The new line character (\n) as a byte.
	 */
	private final static byte NEW_LINE = (byte) '\n';

	/**
	 * 默认采用的编码为UTF-8
	 */
	private final static String PREFERRED_ENCODING = "UTF-8";

	/**
	 * The 64 valid Base64 values.
	 */
	private final static byte[] ALPHABET;
	private final static byte[] _NATIVE_ALPHABET = { (byte) 'A', (byte) 'B',
			(byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
			(byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L',
			(byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
			(byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V',
			(byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a',
			(byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
			(byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k',
			(byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p',
			(byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
			(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
			(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
			(byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
			(byte) '+', (byte) '/' };

	static {
		byte[] __bytes;
		try {
			__bytes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.getBytes(PREFERRED_ENCODING);
		} catch (java.io.UnsupportedEncodingException use) {
			__bytes = _NATIVE_ALPHABET;
		}
		ALPHABET = __bytes;
	}

	private final static byte[] DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9,
			-9, // Decimal 0 - 8
			-5, -5, // Whitespace: Tab and Linefeed
			-9, -9, // Decimal 11 - 12
			-5, // Whitespace: Carriage Return
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 -
																// 26
			-9, -9, -9, -9, -9, // Decimal 27 - 31
			-5, // Whitespace: Space
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
			62, // Plus sign at decimal 43
			-9, -9, -9, // Decimal 44 - 46
			63, // Slash at decimal 47
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
			-9, -9, -9, // Decimal 58 - 60
			-1, // Equals sign at decimal 61
			-9, -9, -9, // Decimal 62 - 64
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through
															// 'N'
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O'
															// through 'Z'
			-9, -9, -9, -9, -9, -9, // Decimal 91 - 96
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a'
																// through 'm'
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n'
																// through 'z'
			-9, -9, -9, -9 // Decimal 123 - 126
	};

	private final static byte WHITE_SPACE_ENC = -5;
	private final static byte EQUALS_SIGN_ENC = -1;

	private Base64() {
	}

	private static byte[] encode3to4(byte[] source, int srcOffset,
			int numSigBytes, byte[] destination, int destOffset) {
		int inBuff = (numSigBytes > 0 ? ((source[srcOffset] << 24) >>> 8) : 0)
				| (numSigBytes > 1 ? ((source[srcOffset + 1] << 24) >>> 16) : 0)
				| (numSigBytes > 2 ? ((source[srcOffset + 2] << 24) >>> 24) : 0);

		switch (numSigBytes) {
		case 3:
			destination[destOffset] = ALPHABET[(inBuff >>> 18)];
			destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
			destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 0x3f];
			destination[destOffset + 3] = ALPHABET[(inBuff) & 0x3f];
			return destination;

		case 2:
			destination[destOffset] = ALPHABET[(inBuff >>> 18)];
			destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
			destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 0x3f];
			destination[destOffset + 3] = EQUALS_SIGN;
			return destination;

		case 1:
			destination[destOffset] = ALPHABET[(inBuff >>> 18)];
			destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
			destination[destOffset + 2] = EQUALS_SIGN;
			destination[destOffset + 3] = EQUALS_SIGN;
			return destination;

		default:
			return destination;
		}
	}

	public static String encodeBytes(byte[] source) {
		return encodeBytes(source, 0, source.length, NO_OPTIONS);
	}

	public static String encodeBytes(byte[] source, int options) {
		return encodeBytes(source, 0, source.length, options);
	}

	public static String encodeBytes(byte[] source, int off, int len) {
		return encodeBytes(source, off, len, NO_OPTIONS);
	}

	public static String encodeBytes(byte[] source, int off, int len,
			int options) {
		int dontBreakLines = (options & DONT_BREAK_LINES);
		boolean breakLines = dontBreakLines == 0;
		int len43 = len * 4 / 3;
		byte[] outBuff = new byte[(len43) + ((len % 3) > 0 ? 4 : 0)
				+ (breakLines ? (len43 / MAX_LINE_LENGTH) : 0)];
		int d = 0;
		int e = 0;
		int len2 = len - 2;
		int lineLength = 0;
		for (; d < len2; d += 3, e += 4) {
			encode3to4(source, d + off, 3, outBuff, e);
			lineLength += 4;
			if (breakLines && lineLength == MAX_LINE_LENGTH) {
				outBuff[e + 4] = NEW_LINE;
				e++;
				lineLength = 0;
			}
		}

		if (d < len) {
			encode3to4(source, d + off, len - d, outBuff, e);
			e += 4;
		}
		try {
			return new String(outBuff, 0, e, PREFERRED_ENCODING);
		} catch (java.io.UnsupportedEncodingException uue) {
			return new String(outBuff, 0, e);
		}

	}

	private static int decode4to3(byte[] source, int srcOffset,
			byte[] destination, int destOffset) {
		if (source[srcOffset + 2] == EQUALS_SIGN) {
			int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
					| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12);

			destination[destOffset] = (byte) (outBuff >>> 16);
			return 1;
		} else if (source[srcOffset + 3] == EQUALS_SIGN) {
			int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
					| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
					| ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6);

			destination[destOffset] = (byte) (outBuff >>> 16);
			destination[destOffset + 1] = (byte) (outBuff >>> 8);
			return 2;
		} else {
			try {
				int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
						| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
						| ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6)
						| ((DECODABET[source[srcOffset + 3]] & 0xFF));

				destination[destOffset] = (byte) (outBuff >> 16);
				destination[destOffset + 1] = (byte) (outBuff >> 8);
				destination[destOffset + 2] = (byte) (outBuff);

				return 3;
			} catch (Exception e) {
				System.out.println("" + source[srcOffset] + ": "
						+ (DECODABET[source[srcOffset]]));
				System.out.println("" + source[srcOffset + 1] + ": "
						+ (DECODABET[source[srcOffset + 1]]));
				System.out.println("" + source[srcOffset + 2] + ": "
						+ (DECODABET[source[srcOffset + 2]]));
				System.out.println("" + source[srcOffset + 3] + ": "
						+ (DECODABET[source[srcOffset + 3]]));
				return -1;
			}
		}
	}

	public static byte[] decode(byte[] source, int off, int len) {
		int len34 = len * 3 / 4;
		byte[] outBuff = new byte[len34];
		int outBuffPosn = 0;

		byte[] b4 = new byte[4];
		int b4Posn = 0;
		int i = 0;
		byte sbiCrop = 0;
		byte sbiDecode = 0;
		for (i = off; i < off + len; i++) {
			sbiCrop = (byte) (source[i] & 0x7f);
			sbiDecode = DECODABET[sbiCrop];

			if (sbiDecode >= WHITE_SPACE_ENC) {
				if (sbiDecode >= EQUALS_SIGN_ENC) {
					b4[b4Posn++] = sbiCrop;
					if (b4Posn > 3) {
						outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
						b4Posn = 0;
						if (sbiCrop == EQUALS_SIGN)
							break;
					}
				}
			} else {
				System.err.println("Bad Base64 input character at " + i + ": "
						+ source[i] + "(decimal)");
				return null;
			}
		}

		byte[] out = new byte[outBuffPosn];
		System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
		return out;
	}

	public static byte[] decode(String s) {
		byte[] bytes;
		try {
			bytes = s.getBytes(PREFERRED_ENCODING);
		} catch (java.io.UnsupportedEncodingException uee) {
			bytes = s.getBytes();
		}

		bytes = decode(bytes, 0, bytes.length);
		if (bytes != null && bytes.length >= 4) {
			int head = (bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00);
			if (java.util.zip.GZIPInputStream.GZIP_MAGIC == head) {
				java.io.ByteArrayInputStream bais = null;
				java.util.zip.GZIPInputStream gzis = null;
				java.io.ByteArrayOutputStream baos = null;
				byte[] buffer = new byte[2048];
				int length = 0;

				try {
					baos = new java.io.ByteArrayOutputStream();
					bais = new java.io.ByteArrayInputStream(bytes);
					gzis = new java.util.zip.GZIPInputStream(bais);

					while ((length = gzis.read(buffer)) >= 0) {
						baos.write(buffer, 0, length);
					}
					bytes = baos.toByteArray();
				} catch (java.io.IOException e) {
				} finally {
					try {
						baos.close();
					} catch (Exception e) {
					}
					try {
						gzis.close();
					} catch (Exception e) {
					}
					try {
						bais.close();
					} catch (Exception e) {
					}
				}
			}
		}

		return bytes;
	}
	
	
}
