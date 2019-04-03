package patternSearchUsingJava.launcher;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class NamesOfFolder {
	
	
	public static void main(String[] args) {
		File file = new File("E:\\NRI-NAM\\NAMRUICodebase\\NAMRUI\\devel\\server");
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		System.out.println(Arrays.asList(directories));
	}

}
