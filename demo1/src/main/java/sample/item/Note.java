package sample.item;

import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Note {

    private static FileWriter file;
    protected String description;
    protected String convertNewLine;
    protected String saveDate;


    public Note() {
        this.description = "";
        this.saveDate = String.valueOf(LocalDate.now());
        setConvertNewLine(description);
    }

    public Note(String description) {
        this.description = description;
        this.saveDate = String.valueOf(LocalDate.now());
        setConvertNewLine(description);
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConvertNewLine() {
        return convertNewLine;
    }

    //buff this method *********
    public void setConvertNewLine(String text) {
        String pureText = text;
        String[] newText = text.split(" ");
        List<String> textList = new ArrayList<>();
        text = "";
        int numOfOneLine = 13;
        for (String a : newText) {
            if (a.length() > numOfOneLine) {
                while (a.length() >= 13) {
                    textList.add(a.substring(0, 13));
                    a = a.substring(13);
                }
                textList.add(a);
            } else {
                textList.add(a);
            }
        }
        System.out.println(textList);
        for (String a : textList) {
            text = text + a + " ";

            if (text.length() > numOfOneLine || (text + a).length() > numOfOneLine) {
                text += "\n";
                numOfOneLine += 13;
            }


        }
        //System.out.println(text);
        this.convertNewLine = text;
    }

    private static void parseNoteObject(JSONObject noteObject) {
        //Get employee first name
        String description = (String) noteObject.get("Description");
        System.out.println(description);

        //Get employee last name
        String day = (String) noteObject.get("Day");
        System.out.println(day);
    }

    public JSONArray openJSON() {
        JSONParser parser = new JSONParser();
        JSONArray noteArray = null;
        try {
            Object object = parser.parse(new FileReader("FileNote/noteDemo.json"));
            noteArray = (JSONArray) object;
            for (int i = 0; i < noteArray.size(); i++) {
                System.out.println(noteArray.get(i));
                JSONObject noteObject = (JSONObject) noteArray.get(i);
                String description = (String) noteObject.get("Description");
                System.out.println(description);
                String day = (String) noteObject.get("Day");
                System.out.println(day);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return noteArray;
    }

    public void deleteJSON(JsonObject object) {
        JSONArray noteArray = openJSON();
        for (int i = 0; i < noteArray.size(); i++) {
            if (noteArray.get(i).equals(object)) {
                noteArray.remove(i);
            }
        }
        try {
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter("FileNote/noteDemo.json");
            file.write(noteArray.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveJSON() {
        JSONArray noteArray = openJSON();
        JSONObject obj = new JSONObject();
        obj.put("Description", description);
        obj.put("Day", saveDate);
        noteArray.add(obj);
        try {
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter("FileNote/noteDemo.json");
            file.write(noteArray.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Note{" +
                ", description='" + description + '\'' +
                ", saveDate=" + saveDate +
                '}';
    }
}
