package net.lax1dude.eaglercraft.zip_generator;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZipGenerator {

    public static void main(String[] args) throws IOException {

        System.out.println();
        System.out.println("Notes:");
        System.out.println();
        System.out.println(" - Run \"gradlew teavmc\" on the client and integrated server");
        System.out.println();

        try {
            if (!(boolean) Class.forName("net.lax1dude.eaglercraft.v1_8.buildtools.Java11Check").getMethod("classLoadCheck").invoke(null)) {
                throw new RuntimeException("wtf?");
            }
        } catch (Throwable t) {
            System.err.println("ERROR: A minimum of Java 11 is required to run this tool!");
            System.err.println();
            System.err.println("You are using Java " + System.getProperty("java.version"));
            System.err.println();
            return;
        }

        String date = (new SimpleDateFormat("MM/dd/yyyy")).format(new Date());
        System.out.println("Using date: " + date);

        System.out.println();

        System.out.println("Running closure compiler on 'eagswebrtc.js'...");
        JARSubprocess.runJava(new File("."), new String[]{"-jar", "zip-generator/google-closure-compiler.jar", "--js", "compiled/web/eagswebrtc.js",
                "--js_output_file", "compiled/web/eagswebrtc.js", "-O", "SIMPLE"}, "[ClosureCompiler]");

        System.out.println("Loading 'compiled/web/classes.js'");
        String classesJs = FileUtils.readFileToString(new File("compiled/web/classes.js"), "UTF-8");

        File f = new File("crash_report_override.txt");
        if (f.isFile()) {
            classesJs = classesJs.replace("If this has happened more than once then please copy the text on this screen and publish"
                    + " it in the issues feed of this fork\\'s GitHub repository.", FileUtils.readFileToString(f, "UTF-8").trim());
        }

        System.out.println("Loading 'compiled/web/classes_server.js'");
        String classesServerJs = FileUtils.readFileToString(new File("compiled/web/classes_server.js"), "UTF-8").replaceFirst("\\/\\/# sourceMappingURL=.*(\\r\\n|\\r|\\n)*", "").trim();

        System.out.println("Loading texture packs");
        String defaultoldEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/defaultold.epk")));
        String defaultnewEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/defaultnew.epk")));
        String bombiesEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/bombies.epk")));
        String bonesEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/bones.epk")));
        String modifiednewEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/modifiednew.epk")));
        String miamiprivateEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/miamiprivate.epk")));
        String nebulaEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/nebula.epk")));
        String ricefaultEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/ricefault.epk")));
        String tightfaultEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/tightfault.epk")));
        String walifaultEpk = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File("compiled/web/packs/walifault.epk")));

        System.out.println("Loading 'compiled/web/eagswebrtc.js'");
        String classesWebRTCJs = FileUtils.readFileToString(new File("compiled/web/eagswebrtc.js"), "UTF-8").replaceFirst("[\\'\\\"]use strict[\\'\\\"]\\;(\\r\\n|\\r|\\n)*", "").trim();

        System.out.println("Loading 'zip-generator/precisionclientofflinetemp.html'");
        String offlineTemplate = FileUtils.readFileToString(new File("zip-generator/precisionclientofflinetemp.html"), "UTF-8");

        System.out.println("Writing 'compiled/offline/precisionclientoffline.html'");

        offlineTemplate = offlineTemplate.replace("${date}", date).replace("${defaultold_epk_base64}", defaultoldEpk).replace("${defaultnew_epk_base64}", defaultnewEpk).replace("${bombies_epk_base64}", bombiesEpk).replace("${barebones_epk_base64}", bonesEpk).replace("${modifiednew_epk_base64}", modifiednewEpk).replace("${miamiprivate_epk_base64}", miamiprivateEpk).replace("${nebula_epk_base64}", nebulaEpk).replace("${ricefault_epk_base64}", ricefaultEpk).replace("${tightfault_epk_base64}", tightfaultEpk).replace("${walifault_epk_base64}", walifaultEpk).replace("${classes_js}",
                classesJs.replaceFirst("\\/\\/# sourceMappingURL=.*(\\r\\n|\\r|\\n)*", "").trim());
        offlineTemplate = offlineTemplate.replace("${eagswebrtc_js}", classesWebRTCJs).replace("${classes_server_js}", classesServerJs);

        FileUtils.writeStringToFile(new File("compiled/offline/precisionclientoffline.html"), offlineTemplate, "UTF-8");

        System.out.println("Writing 'compiled/web/classes.js'");
        FileUtils.writeStringToFile(new File("compiled/web/classes.js"), classesJs, "UTF-8");

    }

}