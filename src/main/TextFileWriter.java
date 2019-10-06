package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter {
  private BufferedWriter _writer;

  public void writeLine(String text) {
    try {
      _writer.write(text);
      _writer.newLine();
      _writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeAllLine(String[] lines) {
    for (String line : lines) {
      try {
        _writer.write(line);
        _writer.newLine();
        _writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void write2DArray(String[][] array, String delimiter, int number_of_row_to_write) {
    for (int i = 0; i < number_of_row_to_write; i++) {
      try {
        String line = String.join(";", array[i]);
        _writer.write(line);
        _writer.newLine();
        _writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static TextFileOutputConfigurationBuilder newConfigurationBuilder() {
    return new TextFileOutputConfigurationBuilder();
  }

  public static class TextFileOutputConfigurationBuilder {
    private String _file_path;
    private boolean _is_append = false;

    public TextFileOutputConfigurationBuilder destinationFile(String file_path) {
      _file_path = file_path;
      return this;
    }

    public TextFileOutputConfigurationBuilder isAppend() {
      this._is_append = true;
      return this;
    }

    public TextFileWriter build() {
      File f = new File(_file_path);
      try {
        FileWriter writer = _is_append ? new FileWriter(f, true) : new FileWriter(f);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        TextFileWriter obj = new TextFileWriter();
        obj._writer = bufferedWriter;
        return obj;
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      return null;
    }
  }
}
