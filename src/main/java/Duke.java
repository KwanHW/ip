import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Duke {
    private  TaskStore tasks;
    private  Storage storage;
    private  Ui ui;

    public static void main(String[] args) {
        new Duke().run();
    }

    private Duke() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.storage.makeDirectory();
        try {
            this.tasks = storage.importTasks();
        } catch (IOException e) {
            ui.printError("Unable to load file.");
        } catch (DateTimeParseException e) {
            ui.printError("Sorry I don't understand that format. Make sure its in yyyy-mm-dd.");
        }
    }

    public void run() {
        Parser parser = new Parser(ui,storage);
        Scanner input = new Scanner(System.in);

        ui.greet();
        String inputTxt;
        while (input.hasNext()) {
            inputTxt = input.nextLine();
            parser.processInput(inputTxt,tasks);

            if (inputTxt.startsWith("bye")) {
                break;
            }
        }
    }

}