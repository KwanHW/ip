package duke.io;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskStore;
import duke.task.TaskType;
import duke.task.Timeable;
import duke.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Represents an interface to perform file reading and writing the tasks to the drive.
 */
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

    /**
     * Creates a directory if the directory in <i>PATH</i> does not exist.
     */
    public void makeDirectory() {
        if (!this.directoryExists())  {
            this.file.getParentFile().mkdir();
        }
    }

    /**
     * Iterates through the given task list and write all the tasks into <i>PATH</i>.
     * @param ts <code>TaskStore</code> containing all the tasks to write into the file.
     * @throws IOException If the file could not be accessed (e.g. file not found, no write permissions on file)
     */
    public void writeToFile(TaskStore ts) throws IOException {
            FileWriter fw = new FileWriter(PATH);
            for (Task task : ts.getTaskList()) {
                fw.write(task.writeToFile() + "\n");
            }
            fw.close();
    }

    /**
     * Imports the tasks specified in <i>PATH</i>. When reading each line in the file, it will create a task based on the first column along with the subsequent columns which would be the arguments.
     * Returns an empty <code>TaskStore</code> if an IOException occurs.
     * @return The imported tasks in the form of <code>TaskStore</code>.
     * @throws IOException If the file does not exist. In this case, an empty TaskStore is returned.
     * @throws DateTimeParseException If the date could not be parsed when creating the task. The user may need to change the date format such that it is readable by the program.
     */
    public TaskStore importTasks() throws IOException, DateTimeParseException {
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
                        tasks.addTask(new Event(line[2],isDone,Timeable.of(line[3])));
                        break;
                    case DEADLINE:
                        tasks.addTask(new Deadline(line[2],isDone,Timeable.of(line[3])));
                        break;
                }
            }

            return tasks;

        } catch (IOException e) {
            this.makeDirectory();
            this.file.createNewFile();
            return tasks;
        }
    }
}
