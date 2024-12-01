package code_clone;

import IO.ProjectReader;
import console.Command;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class CloneCheck {

    static ArrayList<String> ProjectFileName1 = new ArrayList<>();
    static ArrayList<String> ProjectFileName2 = new ArrayList<>();
    public static String path1;
    public static String path2;

    public String pathGenerate(String projectName) {
        String currentpath = Command.currentPath;
        currentpath = new Command().pathGenerate(currentpath);
        String current = currentpath.replaceAll("\\\\", "-").replace(":", "");//location of current file
        String Pathname = "H:\\2-1\\project\\ProcessAllFiles" + "\\ProcessFile$" + current + "-" + projectName;

        return Pathname;
    }

    public void getFileListforProject1(String projectOne) throws IOException {
        String Pathname1 = pathGenerate(projectOne);
        ProjectReader.getFileList(projectOne, Pathname1, ProjectFileName1);

    }

    public void getFileListforProject2(String projectTwo) throws IOException {
        String Pathname2 = pathGenerate(projectTwo);
        ProjectReader.getFileList(projectTwo, Pathname2, ProjectFileName2);

    }
    public void Code_clone(String project1, String project2) throws IOException {
        String Pathname1 = pathGenerate(project1);  //H:\2-1\project\ProcessAllFiles\ProcessFile$H-new
        String Pathname2 = pathGenerate(project2);

        File f1 = new File(Pathname1);
        File f2 = new File(Pathname2);
        if (!f1.exists()) {
            ProjectReader.fileRead(Command.currentPath + "//" + project1, 0);
          
            Path p1 = Paths.get(Pathname1);
            Files.createDirectories(p1);

            for (HashMap.Entry<String, String> entry : ProjectReader.projectOne.entrySet()) {
                new PreProcessing().ProcessFile(entry.getKey(), entry.getValue(), Pathname1); //entry.getKey()-filename with package
            }
        }
        getFileListforProject1(project1);
        path1 = Pathname1;
        if (!f2.exists()) {
            ProjectReader.fileRead(Command.currentPath + "//" + project2, 1);
            //  System.out.println("p2=" + Command.currentPath);
            Path p2 = Paths.get(Pathname2);
            Files.createDirectories(p2);

            for (HashMap.Entry<String, String> entry : ProjectReader.projectTwo.entrySet()) {
                //System.out.println(entry.getKey() + "   " + entry.getValue());
                new PreProcessing().ProcessFile(entry.getKey(), entry.getValue(), Pathname2);
            }

        }
        getFileListforProject2(project2);
        path2 = Pathname2;
        TfIdfCalculate ob = new TfIdfCalculate();
        ob.getUniqueWordProject1(path1);
        ob.getUniqueWordProject2(path2);
        ob.IdfCal();
        ob.tfIdfVectorProject1();
        ob.tfIdfVectorProject2();
        CosineSimilarity sim = new CosineSimilarity();
        sim.getCosinesimilarity();
       // sim.getAverage();
        // BoxAndWhiskerChart.BoxWhisker();
        new BoxAndWhiskerChart().display();
        CosineSimilarity.similarArray.clear();
        //  BoxAndWhiskerChart.list.clear();
        ProjectReader.projectOne.clear();
        ProjectReader.projectTwo.clear();

        TfIdfCalculate.tfidfvectorProject1.clear();

        TfIdfCalculate.tfidfvectorProject2.clear();
        ProjectFileName1.clear();
        ProjectFileName2.clear();

    }
}
