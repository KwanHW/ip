public class Ui {
    public static final String TASK_MARKED="Nice! I marked this task as done:\n %s";
    public static final String TASK_UNMARKED="OK, I've marked this task as not done yet:\n %s";

    public Ui() {

    }

    public void greet() {
        String logo = "                _     _       \n"
                + "               | |   | |      \n"
                + " __      ____ _| | __| | ___  \n"
                + " \\ \\ /\\ / / _` | |/ _` |/ _ \\ \n"
                + "  \\ V  V / (_| | | (_| | (_) |\n"
                + "   \\_/\\_/ \\__,_|_|\\__,_|\\___/ \n";
        System.out.println("Hello from\n" + logo);


        this.printMessage("Hello! I'm Waldo\nWhat can I do for you?");
    }

    public void printMessage(String inputTxt) {
        System.out.println("____________________________________________________________");
        System.out.println(inputTxt);
        System.out.println("____________________________________________________________");
    }

    public void printError(String errorMsg) {
       this.printMessage(String.format("☹ OOPS!!! %s",errorMsg));
    }

    public void printTaskMarking(Task t) {
        if (t.getIsDone()) {
            this.printMessage(String.format(this.TASK_MARKED,t));
        } else {
           this.printMessage(String.format(this.TASK_UNMARKED,t));
        }
    }

    public void bye() {
        this.printMessage("Bye. Hope to see you again soon!");
    }
}
