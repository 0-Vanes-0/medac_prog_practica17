package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadManager {

    private static final String FILE_NAME = "data.txt";

    public static List<Meme> loadData() {
        List<Meme> memes = new ArrayList<>();
        String filePath = "./" + FILE_NAME;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while((line = br.readLine()) != null) {
                    Meme meme = getMeme(line);
                    memes.add(meme);
                }
                fr.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("File is missing! Creating an empty one.");
            saveData();
        }
        return memes;
    }

    public static void saveData(List<Meme> memes) {
        String filePath = "./" + FILE_NAME;
        File file = new File(filePath);
        try {
            FileWriter fw = new FileWriter(file);
            if (memes.isEmpty()) {
                fw.write("");
            } else {
                for(Meme meme : memes) {
                    String line = meme.getNombre() +
                            "|" +
                            meme.getAnyoOrigen() +
                            "|" +
                            meme.getPopularidad() +
                            "|" +
                            meme.getUrl() +
                            "|" +
                            meme.isEsImagen() +
                            "\n";
                    fw.write(line);
                }
            }
            fw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void saveData() {
        saveData(new ArrayList<>());
    }

    private static Meme getMeme(String line) {
        String[] lineElements = line.split("\\|");
        String nombre = lineElements[0];
        int anyoOrigen = 0;
        try {
            anyoOrigen = Integer.parseInt(lineElements[1]);
        } catch (NumberFormatException ignored) { }
        int popularidad = 0;
        try {
            popularidad = Integer.parseInt(lineElements[2]);
        } catch (NumberFormatException ignored) { }
        String url = lineElements[3];
        boolean esImagen = Boolean.parseBoolean(lineElements[4]);

        return new Meme(nombre, anyoOrigen, popularidad, url, esImagen);
    }
}
