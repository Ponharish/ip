package meeju;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the reading and writing of tasks to and from file.
 */
public class Storage {
    private File file;
    private Scanner scanner;
    private String path  = "./data/meeju.txt";

    /* Note - The delimiter used is '!-' */

    /**
     * Constructor for Storage
     * If the directory for the storage file does not exist, it creates the data directory.
     */
    public Storage() {
        this.file = new File(this.path);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Initializes the task list by reading from the storage file.
     *
     * This method is invoked at the start of the program when TaskList is initialised.
     * It reads from the file and populates the tasklist with stored data
     *
     * @return An ArrayList containing the tasks initialized from the file.
     */
    public ArrayList<Task> initialiseList() {
        ArrayList<Task> taskList = new ArrayList<>();
        if (file.exists()) {
            try {
                this.scanner = new Scanner(this.file);
            } catch (Exception e) {
                System.out.println(e); //Ideally never reached here!
            }
            while (scanner.hasNext()) {
                String currentLine = scanner.nextLine();
                String[] currentLineParsed = currentLine.split("!-");
                Task task;
                try {
                    if (currentLineParsed[0].strip().equals("T")) {
                        task = new Todo(currentLineParsed[2]);
                        taskList.add(task);
                    } else if (currentLineParsed[0].strip().equals("D")) {
                        task = new Deadline(currentLineParsed[2].strip(),
                                currentLineParsed[3].strip());
                        taskList.add(task);
                    } else {
                        task = new Event(currentLineParsed[2].strip(),
                                currentLineParsed[3].strip(), currentLineParsed[4].strip());
                        taskList.add(task);
                    }
                    if (currentLineParsed[1].strip().equals("true")) {
                        task.setIsDone(true);
                    }
                } catch (Exception e) {
                    System.out.println(e); //Ideally never reached here!
                }
            }
        }
        return taskList;
    }

    /**
     * Updates the storage file with the current task list.
     *
     * Takes each task in the taskList, serialises it and writes it to the file.
     *
     * @param taskList The task list containing the tasks to be saved to the file.
     * @throws MeejuException If an I/O error occurs while writing to the file.
     */
    public void updateFile(ArrayList<Task> taskList) throws MeejuException {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(this.path);
        } catch (IOException e) {
            throw new MeejuException("IO exception!");
        }
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            String next;
            Task currentTask = taskList.get(i);
            next = currentTask.serializeDetails();
            content.append(next);
        }
        try {
            fileWriter.write(content.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new MeejuException("IO exception!");
        }
    }
}
