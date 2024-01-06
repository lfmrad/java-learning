import java.io.File;

public class SimpleDirExplorer {
    private int subdirDepth = 0;

    public static void main (String[] args) {
        File exampleFile;
        if (args.length > 0) {
            exampleFile = new File(args[0]);
            System.out.println("Exploring directory: " + exampleFile.getAbsolutePath());
            SimpleDirExplorer fileExplorer = new SimpleDirExplorer();
            fileExplorer.exploreFolder(exampleFile);
        } else {
            System.out.println("PATH NOT PROVIDED!");
        }
    }
    
    public void exploreFolder(File file) {
        try {
            for (File subFile : file.listFiles()) {
                System.out.println(addDepth() + subFile.getName());
                if (subFile.isDirectory()) {
                    this.subdirDepth++;
                    exploreFolder(subFile);
                    this.subdirDepth--;
                }
            }
        } catch (Exception e) {
            System.out.println("The provided path does not exist or is not a directory.");
        } 
    }

    public String addDepth() {
        StringBuilder sb = new StringBuilder();
        for (int level = 0; level < subdirDepth; level++) {
            sb.append("    ");
        }
        return sb.toString();
    }
}