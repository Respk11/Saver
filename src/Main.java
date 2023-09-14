import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class Main {
    private static String fileList = "";

    public static void main(String[] args) {
        GameProgress gProgress1 = new GameProgress(100, 200, 10, 3.4);
        GameProgress gProgress2 = new GameProgress(50, 0, 2, 0.5);
        GameProgress gProgress3 = new GameProgress(74, 112, 4, 2.8);
        List<String> namesList = Arrays.asList("C://Games", "C://Games/savegames",
                "C://Games/savegames/save1.dat", "C://Games/savegames/save2.dat",
                "C://Games/savegames/save3.dat");
        for (int i = 0; i < namesList.size(); i++) {
            String name = namesList.get(i);
            Create(name);
        }

        saveGame("C://Games/savegames/save1.dat", gProgress1);
        saveGame("C://Games/savegames/save2.dat", gProgress2);
        saveGame("C://Games/savegames/save3.dat", gProgress3);
        getListFiles("C://Games/savegames", "C://Games/savegames/fileList.txt");

    }

    public static void Create(String nameFile) {
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

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void getListFiles(String dirPath, String filePath) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) fileList += dirPath + "/" + item.getName() + "\n";
        }
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] bytes = fileList.getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String filePath, String fileList) {

    }
}