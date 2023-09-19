import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress gProgress1 = new GameProgress(100, 200, 10, 3.4);
        GameProgress gProgress2 = new GameProgress(50, 0, 2, 0.5);
        GameProgress gProgress3 = new GameProgress(74, 112, 4, 2.8);
        List<String> namesList = Arrays.asList("D://Games", "D://Games/savegames",
                "D://Games/savegames/save1.dat", "D://Games/savegames/save2.dat",
                "D://Games/savegames/save3.dat");
        for (int i = 0; i < namesList.size(); i++) {
            String name = namesList.get(i);
            Create(name);
        }

        saveGame("D://Games/savegames/save1.dat", gProgress1);
        saveGame("D://Games/savegames/save2.dat", gProgress2);
        saveGame("D://Games/savegames/save3.dat", gProgress3);
        zipFiles("D://Games/savegames/zip.zip", "D://Games/savegames");

    }

    private static void Create(String nameFile) {
        int index = nameFile.indexOf(".");
        if (index == -1) {
            File dir = new File(nameFile);
            boolean created = dir.mkdir();
        } else {
            File file = new File(nameFile);
            try {
                boolean created = file.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void saveGame(String filePath, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String filePath, String folderPath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(filePath))) {
            File folder = new File(folderPath);
            for (File file : folder.listFiles()) {
                if (file.getName().contains(".dat")) {
                    FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    fis.close();
                    file.delete();
                }
            }

        } catch (
                Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

