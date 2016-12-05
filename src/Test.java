import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Lei on 2016/12/2.
 */
public class Test {

	File[] get_Files(String dir_Str, String file_Format){
		String[] strings= dir_Str.split("%d");
		String parent = strings[0];
		String dir_Format = strings[1];
		File[] dires = sortAndList(parent,dir_Format,true);

		if(dires==null){
			return null;
		}
		ArrayList<File> fileList = new ArrayList<>();
		for (File dir:dires
				) {
			Collections.addAll(fileList,sortAndList(dir.getAbsolutePath(),file_Format,false));
		}
		File[] files = (File[])fileList.toArray(new File[fileList.size()]);
		return fileList.toArray(new File[fileList.size()]);
	}

	//通过一个标记参数控制给出指定目录下目录的排序或者是文件的排序
	File[] sortAndList(String dir_Name, final String regex, final boolean is_Dir){
		File dir =  new File(dir_Name);
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

	void printObject(Object[] objects){
		for (Object object:objects
				) {
			System.out.println(object);
		}
	}

	public static void main(String[] args) {
		Test test = new Test();
		File[] files = test.get_Files("E:\\test\\test\\dirtest\\%d[0-9]{8}",".{4}");
		test.printObject(files);
	}
}
