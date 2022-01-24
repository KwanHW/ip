import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
//    Support for OS independent paths
    public static final String PATH = String.join(File.separator,"data","duke.txt");
    private File file;

    public Storage() {
       this.file = new File(PATH);
    }

    public boolean directoryExists() {
        return this.file.getParentFile().exists();
    }

    public void makeDirectory() {
        if (!this.directoryExists())  {
            this.file.getParentFile().mkdir();
        }
    }

    public void writeToFile(TaskStore ts) throws IOException{
            FileWriter fw = new FileWriter(PATH);
            for (Task task : ts.getTaskList()) {
                fw.write(task.writeToFile() + "\n");
            }
            fw.close();
    }

    public TaskStore importTasks() throws IOException {
        TaskStore tasks = new TaskStore();

        try {
            Scanner s = new Scanner(this.file);
            while (s.hasNext()) {
                String[] line = s.nextLine().split(" \\| ");
                TaskType type = TaskType.fromString(line[0]);
                boolean isDone = line[1].equals("1");
                switch (type){
                    case TODO:
                        tasks.addTask(new Todo(line[2],isDone));
                        break;
                    case EVENT:
                        tasks.addTask(new Event(line[2],isDone,line[3]));
                        break;
                    case DEADLINE:
                        tasks.addTask(new Deadline(line[2],isDone,line[3]));
                }
            }

            return tasks;

        } catch (FileNotFoundException e) {
            this.file.createNewFile();
            return tasks;
        }
    }
}
