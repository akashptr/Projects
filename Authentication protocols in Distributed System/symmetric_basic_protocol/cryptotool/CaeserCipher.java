package cryptotool;

public class CaeserCipher {
	public static String encrypt(String str, int key)
	{
		StringBuffer sb = new StringBuffer();
		int len = str.length();
		char startLetter;
		for(int i=0; i<len; i++)
		{
			char ch = str.charAt(i);
			if(Character.isAlphabetic(ch))
			{
				startLetter = (Character.isLowerCase(ch) ? 'a' : 'A');
				ch = (char)(((ch + key - startLetter) % 26) + startLetter);
			}
			else if(Character.isDigit(ch))
				ch = (char)(((ch + key - 48) % 10) + 48);
			sb.append(ch);
		}
		return sb.toString();
	}
	
	public static String decrypt(String str, int key)
	{
		StringBuffer sb = new StringBuffer();
		int len = str.length();
		char endLetter;
		for(int i=0; i<len; i++)
		{
			char ch = str.charAt(i);
			if(Character.isAlphabetic(ch))
			{
				endLetter = (Character.isLowerCase(ch) ? 'z' : 'Z');
				ch = (char)(endLetter - ((endLetter - ch + key) % 26));
			}
			else if(Character.isDigit(ch))
				ch = (char)('9' - (('9' - ch + key) % 10));
			sb.append(ch);
		}
		return sb.toString();
	}
}
