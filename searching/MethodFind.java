package searching;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MethodFind {
    private static final String METHOD_PATTERN = "(public|void|protected|private|static|final|public static|private static|protected static|public final|private final|protective final)+\\s*(\\<.*\\>)*\\s*[a-zA-Z]*\\s*\\b([_$a-zA-Z1-9]+)\\b\\s*\\(.*\\)\\s*[^;].*?$";
    private static final String CLASS_PATTERN = "class\\s+([a-zA-Z]+).*";
    public void getMethod(String filename, String fileContent, String path, String processfilePath) throws FileNotFoundException, IOException {
        pracessContent(filename, fileContent, path, processfilePath, METHOD_PATTERN, 3);
    }

    public void getConstructor(String filename, String fileContent, String path, String processfilePath) throws FileNotFoundException, IOException {
        Pattern classPattern = Pattern.compile(CLASS_PATTERN);
        Matcher classMatcher = classPattern.matcher(fileContent);
        while (classMatcher.find()) {
            String className = classMatcher.group(1);
            String classContent = new GrepContent().findBetweenBraces(classMatcher.start(), fileContent);
            String constructorPattern = "(\\b" + className + "\\b)\\s*\\(.*\\)\\s*[^;].*$";
            pracessContent(filename, classContent, path, processfilePath, constructorPattern, 1);
        }
    }
    private void pracessContent(String filename, String content, String path, String processfilePath, String pattern, int groupIndex) throws FileNotFoundException, IOException {
        Matcher matcher = Pattern.compile(pattern, Pattern.MULTILINE).matcher(content);
        while (matcher.find()) {
            String extractedContent = new GrepContent().findBetweenBraces(matcher.start(), content);
            String name = matcher.group(groupIndex).replaceAll("\\{", "").replaceAll("[\r\n]+", " ").trim();
            int lineNumber = new GrepContent().getLineNumber(name, path, 0);
            String processFilename = matcher.group(groupIndex) + "-" + lineNumber + "-" + filename;
            if (lineNumber != 0) {
                new ProcessSearchFile().processMethod(processFilename, extractedContent, path, processfilePath);
            }
        }
    }
}
