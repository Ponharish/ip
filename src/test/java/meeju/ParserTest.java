package meeju;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ParserTest {
    private TaskList taskList = new TaskList(new StorageStub());
    private Parser parser = new Parser();


    //CHECKSTYLE.OFF: MethodName
    @Test
    public void bye_Success() {
        try {
            assertEquals(" Bye. Hope to see you again soon!", parser.parse(taskList, "bye"));
        } catch (MeejuException e) {
            fail();
        }
    }
    @Test
    public void incorrectCommand_Success() {
        try {
            assertEquals("I'm sorry, I did not understand that =^..^=",
                parser.parse(taskList, "this is an invalid command!"));
        } catch (MeejuException e) {
            fail();
        }
    }

    @Test
    public void addTodoTask_Success() {
        String message = "Meow! I've added this task:\n"
                + "\t" + "[T][ ] testTodo" + "\nNow you have " + 1
                + " tasks in the list.";
        try {
            assertEquals(message,
                    parser.parse(taskList, "todo testTodo"));
        } catch (MeejuException e) {
            fail();
        }
    }

    @Test
    public void addDeadlineTask_Success() {
        String message = "Meow! I've added this task:\n"
                + "\t" + "[D][ ] deadlineTest (by: Sep 5 2024 18:00HRS)" + "\nNow you have " + 1
                + " tasks in the list.";
        try {
            assertEquals(message,
                parser.parse(taskList, "deadline deadlineTest /by 05/09/2024 1800"));
        } catch (MeejuException e) {
            fail();
        }
    }

    @Test
    public void emptyCommand() throws MeejuException {
        String instruction = "  ";
        String result = parser.parse(taskList, instruction);
        assertEquals("Empty Command!", result);
    }

    // The following test was generated using ChatGPT
    @Test
    public void hiCommand() throws MeejuException {
        String instruction = "hi";
        String result = parser.parse(taskList, instruction);
        assertEquals("Meow! Hello There! How can i help you?", result);
    }

    // The following test was generated using ChatGPT
    @Test
    public void parse_ListCommand() throws MeejuException {
        String instruction = "list";
        taskList.addTodoTask("Buy groceries");
        String result = parser.parse(taskList, instruction);
        assertEquals("1. [T][ ] Buy groceries\n", result);
    }


    // The following test was generated using ChatGPT
    @Test
    public void parse_FindCommand() throws MeejuException {
        String instruction = "find groceries";
        taskList.addTodoTask("Buy groceries");
        String result = parser.parse(taskList, instruction);
        assertTrue(result.contains("Buy groceries"));
    }

    // The following test was generated using ChatGPT
    @Test
    public void testParse_DeleteCommand() throws MeejuException {
        taskList.addTodoTask("Read a book");
        String instruction = "delete 1";
        String result = parser.parse(taskList, instruction);
        String expected = "Meow! I've removed this task:\n"
                + "\t" + "[T][ ] Read a book"
                + "\nNow you have " + 0 + " tasks in the list.";
        assertEquals(expected, result);
    }

    // The following test was generated using ChatGPT
    @Test
    public void parse_MarkCommand() throws MeejuException {
        taskList.addTodoTask("Read a book");
        String instruction = "mark 1";
        String result = parser.parse(taskList, instruction);
        String expected = "Meow! I've marked this task as done:\n"
                + "\t" + "[T][X] Read a book";
        assertEquals(expected, result);
    }

    // The following test was generated using ChatGPT
    @Test
    public void parse_UnmarkCommand() throws MeejuException {
        taskList.addTodoTask("Read a book");
        parser.parse(taskList, "mark 1");
        String instruction = "unmark 1";
        String result = parser.parse(taskList, instruction);
        String expected = "Meow! I've marked this task as not done yet:\n"
                + "\t" + "[T][ ] Read a book";
        assertEquals(expected, result);
    }

    //CHECKSTYLE.ON: MethodName
}
