package searching;

import IO.Filewriter;
import code_clone.Porter_stemmer;
import code_clone.PreProcessing;
import console.Command;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProcessSearchFile {

    Public void processMethod(String filename, String fileContent, String filepath, String processfilePath) throws IOException {
        String preprocessedContent = preprocessContent(fileContent);
        String stemmedContent = stemWords(preprocessedContent);
        writeToFile(filename, stemmedContent, processfilePath);
    }
    private String preprocessContent(String content) {
        PreProcessing preProcessor = new PreProcessing();
        String removePuncuation = preProcessor.removePunctuation(content);
        String methodWithoutKey = preProcessor.removeKeyword(removePuncuation);
        String methodWithoutSpace = preProcessor.removeSpace(methodWithoutKey);
        return breakWord(methodWithoutSpace);
    }
    public String queryProcess(String query) throws IOException {
        String stemWord = "";
        PreProcessing ob = new PreProcessing();
        String removePuncuation = ob.removePunctuation(query);
        String methodWithoutKey = ob.removeKeyword(removePuncuation);
        String methodWithoutSpace = ob.removeSpace(methodWithoutKey);
        String breakWork = breakWord(methodWithoutSpace);
        Porter_stemmer stemmer = new Porter_stemmer();
        String[] words = breakWork.split(" ");
        for (String word : words) {
            String stem = stemmer.stemWord(word);
            stemWord = stemWord + " " + stem;
        }

        return stemWord;
    }

    public String breakWord(String fileAsString) throws FileNotFoundException, IOException {
        String[] splitString = fileAsString.split(" ");
        String methods = "";
        String allmethod = "";
        //String s = "BByTTheWay";
        Pattern p = Pattern.compile("\\p{Lu}\\p{Ll}*");
        for (int i = 0; i < splitString.length; i++) {
            String nonbreak = "";
            String lower = splitString[i].toLowerCase();
            if (!splitString[i].equals(lower) && splitString[i].length()!=1 ) {
                methods = Arrays.stream(splitString[i].split("(?=\\p{Lu})"))
                        .collect(Collectors.joining(" ")) + " " + splitString[i];
                allmethod = allmethod + " " + methods;
            } else {
                allmethod = allmethod + " " + splitString[i];
            }

        }
        return allmethod;
    }

}
