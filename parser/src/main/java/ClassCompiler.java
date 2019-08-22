import org.openmuc.jasn1.compiler.Compiler;

import java.io.File;

public class ClassCompiler {
    public static void main(String[] args) {
        System.out.println(new File(".").getAbsolutePath());

        String[] cmd = new String[]{
                "-f", "src/main/resources/CDR-HLR-Versions3.asn.txt", "src/main/resources/DataTypes.txt",
                "-o", "src/main/java/model/"};
        try {
            Compiler.main(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
