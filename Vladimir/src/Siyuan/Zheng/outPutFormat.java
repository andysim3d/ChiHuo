package Siyuan.Zheng;

public class outPutFormat {

	public static String outPutFormats(String src, int length){
		if(src.length() == length){
			return src;
		}
		else if(src.length() > length){
			return src.substring(0,length - 1);
		}
		else{
			while(src.length() < length){
				src += " ";
			}
			return src;
		}
	}
	

	public static String outPutFormats(double src, int lenth){
		String temp = String.valueOf(src);
		//temp = temp.substring(0,lenth -2);
		//temp = temp.substring(0,4);
		if(temp.length() > lenth){
			temp = temp.substring(0, lenth -2);
			return (temp+" ");
		}
		else{
			while(temp.length() < lenth - 1){
				temp = " "+ temp;
			}
			return (temp+" ");
		}
	}
	
	public static String outPutFormats(int src, int lenth){
		String temp = String.valueOf(src);
		if(temp.length() > lenth){
			temp = temp.substring(0, lenth -1);
			return temp;
		}
		else{
			while(temp.length() < lenth){
				temp = " "+ temp;
			}
			return temp;
		}
	}
	

}
