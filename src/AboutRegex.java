import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Lei on 2016/12/1.
 */
public class AboutRegex {

	//用正则表达式对两层目录下的文件进行筛选和排序。假如第一层目录是"e:\\test\\dirtest"
	// 在第一层的目录下是以日期为格式的第二层目录比如：20161112
	//在第二层目录下存在不同命名格式的文件，我们要它们挑出来，并各组排序。
	//思路：写一个用正则表达式在第二层目录下挑选不同格式文件的方法

	String dir_Str;//双层目录结构,目录之间以%d隔开，后面跟着目录格式：比如"e:\\test\\dirtest\\%d[0-9]{6}"
	String dir;//第二层目录“[0-9]{6}” 比如：20161112
	String file_Format;//文件格式的正则表达式。
	File[] files;//得到所有满足条件的文件，并进行了升序排列。

	File[] get_Files(String dir_Str,String file_Format){
		String[] strings= dir_Str.split("%d");
		String parent = strings[0];
		System.out.println("parent: "+parent);
		String dir_Format = strings[1];
		System.out.println("dir_Format "+dir_Format);
		File[] dires = sortAndList(parent,dir_Format,true);

		if(dires==null){
			return null;
		}
		ArrayList<File> fileList = new ArrayList<>();
		for (File dir:dires
			 ) {
			Collections.addAll(fileList,sortAndList(dir.getName(),file_Format,false));
		}
		return (File[])fileList.toArray(new File[fileList.size()]);
	}

	File[] sortAndList(String dir_Name, final String regex, final boolean is_Dir){
		File dir =  new File(dir_Name);
		File[] file1 = dir.listFiles();
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(is_Dir==true){
					return pathname.getName().matches(regex)&&pathname.isDirectory();
				}else{
					return 	pathname.getName().matches(regex);
				}
			}
		});

		if(files==null){
			return null;
		}

		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.lastModified()>o2.lastModified()?1:(o1.lastModified()==o2.lastModified()?0:-1);
			}
		});
		return files;
	}

	int aboutThree(int a,int b){
		return a>b?1:(a==b?0:-1);
	}

	void printObject(Object[] objects){
		for (Object object:objects
			 ) {
			System.out.println(object);
		}
	}
	public static void main(String[] args) {
		AboutRegex aboutRegex = new AboutRegex();
		File[] files = aboutRegex.get_Files("E:\\test\\test\\dirtest\\%d[0-9]{6}",".{4}");
     	aboutRegex.printObject(files);

//		ArrayList<String> strList = new ArrayList<String>();
//		strList.add("aa");
//		strList.add("bb");
//		Object[] objs = strList.toArray();
//		//		如果要变成String数组，需要强转类型。
//		String[] strs = (String[]) strList.toArray(new String[0]);
//		//		也可以指定大小：
//		String[] strs1 = strList.toArray(new String[strList.size()]);
//		aboutRegex.printObject(strs);
//		aboutRegex.printObject(strs);

	}
}
