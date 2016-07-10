package com.keti.tas.soft;

public class ByteUtil {

	public static short byteToShort(byte b) {
		return (short)( b & 0xff );
	}
	public static int byteToInt(byte b) {
		return ( b & 0xff );
	}
	
	public static int byte2ToInt(byte[] b) {
		return (b[1]&0xff)<<8 | (b[0]&0xff);
	}
	public static int byte2ToInt(byte[] b, int nStart) {
		return (b[nStart+1]&0xff)<<8 | (b[nStart]&0xff);
	}
	public static byte[] intToByte2(int i) {
		return new byte[] { (byte)i, (byte)(i>>8) };
	}
	public static void intToByte2(int i, byte[] dest, int nStart) {
		dest[nStart+1] = (byte)(i>>8);
		dest[nStart] = (byte)(i);
	}
	public static short byte2ToShort(byte[] b) {
		return (short)((b[1]&0xff)<<8 | (b[0]&0xff));
	}
	public static short byte2ToShort(byte[] b, int nStart) {
		return (short)((b[nStart+1]&0xff)<<8 | (b[nStart]&0xff));
	}
	
	public static long byte3ToLong(byte[] b) {
		return 0x00<<24 | (b[0]&0xff)<<16 | (b[1]&0xff)<<8 | (b[2]&0xff);
	}
	public static long byte3ToLong(byte[] b, int nStart) {
		return 0x00<<24 | (b[nStart]&0xff)<<16 | (b[nStart+1]&0xff)<<8 | (b[nStart+2]&0xff);
	}
	public static byte[] longToByte3(long i) {
		return new byte[] { (byte)(i>>24), (byte)(i>>16), (byte)(i>>8), (byte)(i) };
	}
	public static void longToByte3(long i, byte[] dest, int nStart) {
		dest[nStart] = (byte)(i>>24);
		dest[nStart+1] = (byte)(i>>16);
		dest[nStart+2] = (byte)(i>>8);
		dest[nStart+3] = (byte)i;
	}

	public static long byte4ToLong(byte[] b) {
		return b[0]<<24 | (b[1]&0xff)<<16 | (b[2]&0xff)<<8 | (b[3]&0xff);
	}
	public static long byte4ToLong(byte[] b, int nStart) {
		return b[nStart]<<24 | (b[nStart+1]&0xff)<<16 | (b[nStart+2]&0xff)<<8 | (b[nStart+3]&0xff);
	}
	public static byte[] longToByte4(long i) {
		return new byte[] { (byte)(i>>24), (byte)(i>>16), (byte)(i>>8), (byte)(i) };
	}
	public static void longToByte4(long i, byte[] dest, int nStart) {
		dest[nStart] = (byte)(i>>24);
		dest[nStart+1] = (byte)(i>>16);
		dest[nStart+2] = (byte)(i>>8);
		dest[nStart+3] = (byte)i;
	}
	
	public static String toHexString(byte[] aryByte)
	{
		if( aryByte == null )
			return "";
		
		StringBuffer sb = new StringBuffer();
		for( int i=0; i < aryByte.length; i ++ )
		{
			String sValue = Integer.toHexString( (int) (aryByte[i] & 0xff ) );
			if( sValue.length() == 1 )
				sb.append( "0" ).append( sValue );
			else
				sb.append( sValue.substring( sValue.length() - 2 ) );
			
			sb.append( " " );
		}
		
		return sb.toString();
	}
	
	public static byte[] toByteArray(String hexString) {
		String[] aryStr = hexString.split(" ");
		
		byte[] result = new byte[aryStr.length];
		
		for(int i = 0; i < aryStr.length; i++) {
			result[i] = (byte)Integer.parseInt(aryStr[i],16);
		}
		
		return result;
	}
	
	public static String toHexString(byte[] aryByte, int nStart, int nLength)
	{
		int nMinLength = Math.min( aryByte.length, nStart + nLength );
		
		StringBuffer sb = new StringBuffer();
		for( int i=nStart; i < nMinLength; i ++ )
		{
			String sValue = Integer.toHexString( (int) (aryByte[i] & 0xff ) );
			if( sValue.length() == 1 )
				sb.append( "0" ).append( sValue );
			else
				sb.append( sValue.substring( sValue.length() - 2 ) );
			
			sb.append( " " );
		}
		
		return sb.toString();
	}

	public static String toHexString(byte[] aryByte, int nStart)
	{
		StringBuffer sb = new StringBuffer();
		for( int i=nStart; i < aryByte.length; i ++ )
		{
			String sValue = Integer.toHexString( (int) (aryByte[i] & 0xff ) );
			if( sValue.length() == 1 )
				sb.append( "0" ).append( sValue );
			else
				sb.append( sValue.substring( sValue.length() - 2 ) );
			
			sb.append( " " );
		}
		
		return sb.toString();
	}

	public static byte[] stuffBytes(byte src[])
	{
		byte encode[] = new byte[ src.length * 2 + 3 ];
		int len = 0;
		
		encode[ len ++ ] = 0x7e;
		encode[ len ++ ] = 0x42;
		
		for( int i=0; i < src.length; i ++ )
		{
			if( src[i] == 0x7e )
			{
				encode[ len++ ] = 0x7d;
				encode[ len++ ] = 0x5e;
			}
			else if( src[i] == 0x7d )
			{
				encode[ len++ ] = 0x7d;
				encode[ len++ ] = 0x5d;
			}
			else
			{
				encode[ len++ ] = src[i];
			}
		}
		
		encode[ len ++ ] = 0x7e;
		
		byte result[] = new byte[ len ];
		System.arraycopy(encode, 0, result, 0, len);
		
		return result;
	}
	
	public static String reverseHexString(String str) {
		byte[] arr = toByteArray(str);
		
		byte[] reverseArr = new byte[arr.length];
		
		for(int i = arr.length - 1; i >= 0; i--) {
			reverseArr[i] = arr[arr.length - (i + 1)];
		}
		
		return toHexString(reverseArr);
	}
}
