package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {

  private BufferedReader _reader;

  public String readAllText() {
    try {
      StringBuffer buffer = new StringBuffer();
      String data;
      while (((data = _reader.readLine()) != null)) {
        buffer.append(data);
      }
      return buffer.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String[] readAllLine() {
    List<String> list = new ArrayList<>();
    try {
      String data;
      while (((data = _reader.readLine()) != null)) {
        list.add(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String[][] readTo2DArray(String delimiter) {
    List<String[]> list = new ArrayList<>();
    try {
      String data;
      while (((data = _reader.readLine()) != null)) {
        String[] row = data.split(delimiter);
        list.add(row);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void readInto2DArray(String[][] destination, String delimiter) {
    try {
      String data;
      int i = 0;
      while (((data = _reader.readLine()) != null)) {
        String[] row = data.split(delimiter);
        for (int j = 0; j < row.length; j++) {
          destination[i][j] = row[j];
        }
        i++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static TextFileInputConfigurationBuilder newInputConfigurationBuilder() {
    return new TextFileInputConfigurationBuilder();
  }

  public static class TextFileInputConfigurationBuilder {

    private String _file_path;

    public TextFileInputConfigurationBuilder sourceFile(String file_path) {
      _file_path = file_path;
      return this;
    }

    public TextFileReader build() {
      File f = new File(_file_path);
      try {
        FileReader reader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(reader);
        TextFileReader obj = new TextFileReader();
        obj._reader = bufferedReader;
        return obj;
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
  }
}
