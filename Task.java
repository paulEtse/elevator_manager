import java.io.*;
import java.util.Vector;

class Task {
    int date;
    int from;
    int to;

    public Task(int date, int from, int to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public static Vector<Task> csv_to_list(String filename) {
        Vector<Task> tasks = new Vector<Task>();
        String line;
        String sep = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //head line
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] items = line.replaceAll(" ", "").split(sep);
                tasks.add(new Task(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("The file is not well-formated");
            System.exit(-1);
        }
        return tasks;
    }

    public boolean current(int time) {
        return this.date == time;
    }
}