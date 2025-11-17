import java.io.*;
import java.util.*;

public class methods {

    //method to create new list of tasks.
    public static void createNewFile(){
        Scanner sc = new Scanner(System.in);
        System.out.print("File name: ");
        String fileName = sc.nextLine().trim();     //trim is used to handle extra indents input by user.

        File file = new File(fileName + ".txt");
        try {
            if (file.createNewFile()){
                System.out.println("\""+fileName+"\" added to task lists.");
            } else{
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Unable to create new file");
            throw new RuntimeException(e);
        }
    }

    //method to delete a task list
    public static void deleteFile(){
        Scanner sc = new Scanner(System.in);

        File userDir = new File(System.getProperty("user.dir"));
        FileFilter textFilter = new FileFilter() {
            @Override
            public boolean accept(File userDir) {
                return userDir.getName().endsWith(".txt");
            }
        };

        File[] textFiles= userDir.listFiles(textFilter);
        assert textFiles != null;
        Arrays.stream(textFiles).toArray();

        System.out.print("Enter file name to delete: ");
        String fileName = sc.nextLine().trim()+".txt";

        File delFile = new File(fileName);

        if (delFile.delete()){
            System.out.println("\n\""+fileName+"\" has been deleted.");
        } else {
            System.out.println("File can't be deleted/No file with such name.");
        }
    }

    //method to rename a task list.
    public static void renameFile(){
        Scanner sc = new Scanner(System.in);

        File userDir = new File(System.getProperty("user.dir"));
        FileFilter textFilter = new FileFilter() {
            @Override
            public boolean accept(File userDir) {
                return userDir.getName().endsWith(".txt");
            }
        };

        File[] textFiles= userDir.listFiles(textFilter);
        assert textFiles != null;
        String txt = Arrays.toString(Arrays.stream(textFiles).toArray());
        // same process as deleteFile() method till above

        //from below the changes are made and added according to renaming a file
        System.out.print("Enter file name to rename: ");
        String fileName = sc.nextLine().trim()+".txt";
        System.out.print("Now enter new name for it: ");
        String newFile = sc.nextLine().trim()+".txt";

        File oldName = new File(fileName);
        File newName = new File(newFile);

        if (oldName.renameTo(newName)){
            System.out.println("\n\""+fileName+"\" changed to \""+newFile+"\"");
        } else {
            System.out.println("\nUnable to rename the list. Check if you've entered the name correctly.");
        }

    }

    //method to add task in a list
    public static void listTheItems(){
        // using File object to list all the text files in a directory to user
        File userDir = new File(System.getProperty("user.dir"));

        //storing the file names in an array
        File[] listOfFiles = userDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        // checking for text files existence in the directory.
        if (listOfFiles != null && listOfFiles.length > 0) {
            System.out.println("\nAvailable Files:");
            // Iterate over the list and print the names of the files
            int count = 1;
            for (File file : listOfFiles) {
                System.out.println(count+". "+file.getName());
                count++;
            }
        } else {
            System.out.println("No text files found in the directory.");
        }
    }

    //method to show list of existing task lists and take further actions.
    public static void showListsAndActions(){
        Scanner sc = new Scanner(System.in);
        File folder = new File(System.getProperty("user.dir"));  //fetching current directory of user to get task lists
        File[] listOfFiles = folder.listFiles((_, name) -> name.toLowerCase().endsWith(".txt")); //filtering files having.txt extension only
        if (listOfFiles != null) {
            if (listOfFiles.length == 0){
                System.out.println("No task list created yet.");
            } else {
                listTheItems();

                //providing further actions to be done with files
                boolean goBack = false;
                do {
                    System.out.println("""
                            \nWhat to do now?\
                            \n1. Check existing lists\
                            \n2. Delete a list\
                            \n3. Select a list\
                            \n4. Rename a list \
                            \n5. Go back""");

                    int opt;
                    // running while loop in case user entered wrong input
                    while (true){
                        try {
                            System.out.print("\nSelect option: ");
                            opt = sc.nextInt();
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e){
                            System.out.println("PLease enter only numerical values.");
                            sc.next();
                        }
                    }


                    //'existing lists'
                    if (opt == 1){
                        listTheItems();
                    }
                    //'delete list' option
                    else if (opt == 2) {
                        listTheItems();     //providing the available files for user to check for name of file before deletion.
                        System.out.println();
                        deleteFile();
                    }
                    //'select a list' option
                    else if (opt == 3) {
                        boolean prevMenu = false;
                        System.out.print("File name to work with: ");
                        String fileForWork = sc.nextLine().trim()+".txt";

                        File fileExist = new File(fileForWork);
                        //checking if the file exists and then proceeding else giving option to create new file.
                        if (fileExist.exists()){
                            do{
                                System.out.println("""
                                    \nList Options-\
                                    \n1. See list\
                                    \n2. Add new task\
                                    \n3. Delete a task\
                                    \n4. Previous menu""");

                                int selection;

                                //using while loop to give user option to re-enter correct input, in case.
                                while (true){
                                    try {
                                        System.out.print("\nSelect: ");
                                        selection = sc.nextInt();
                                        sc.nextLine();

                                        if (selection>4 || selection == 0){
                                            System.out.println("Enter from the available options.");
                                        }
                                        break;
                                    } catch (InputMismatchException e){
                                        System.out.println("Please enter only numerical values");
                                        sc.next();
                                    }
                                }

                                //'see list' option
                                if (selection == 1){
                                    File list = new File(fileForWork);
                                    try {
                                        Scanner file = new Scanner(list);

                                        int ctr = 1;
                                        // iterating the given file and printing the output to user
                                        System.out.println(fileForWork+"-");
                                        while (file.hasNextLine()){
                                            for (int i=0; i<list.length(); i++){
                                                String tasks = file.nextLine();
                                                System.out.println(ctr+". "+tasks);
                                                ctr++;
                                                break;
                                            }
                                        }
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    } catch (FileNotFoundException e) {
                                        System.out.println("File not found.");
                                    }
                                }

                                //'add task' option
                                else if (selection == 2){
                                    System.out.print("task: ");
                                    String taskToAdd = sc.nextLine().trim();

                                    //using file-writer class for adding a task to list
                                    try {
                                        FileWriter listWrite = new FileWriter(fileForWork,true);  //append param being true ensures that new task is added separately and doesn't overwrite the existing text.
                                        listWrite.write(taskToAdd+"\n");
                                        listWrite.close();
                                        System.out.println("\n\""+taskToAdd+"\" added to \""+fileForWork+"\"");

                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    } catch (IOException e) {
                                        System.out.println("Unable to add task.");
                                    }
                                }

                                //'delete task' option
                                else if (selection == 3){
                                    List<String> tasks = new ArrayList<>();

                                    // Read all tasks from the file into the list
                                    try (BufferedReader reader = new BufferedReader(new FileReader(fileForWork))) {
                                        String currentLine;
                                        while ((currentLine = reader.readLine()) != null) {
                                            tasks.add(currentLine);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return;
                                    }

                                    // Show tasks to the user
                                    if (tasks.isEmpty()) {
                                        System.out.println("No tasks to delete.");
                                        return;
                                    }

                                    System.out.println("Current tasks:");
                                    for (int i = 0; i < tasks.size(); i++) {
                                        System.out.println((i + 1) + ". " + tasks.get(i));
                                    }

                                    int taskNum;
                                    //running while loop to ensure and re-enter correct input.
                                    while (true){
                                        try {
                                            System.out.print("\nEnter task number to delete: ");
                                            taskNum = sc.nextInt();
                                            sc.nextLine(); // Consume newline

                                            if (taskNum>tasks.size() || taskNum==0){
                                                System.out.println("Enter from the available choices only.");
                                            }
                                            break;
                                        } catch (InputMismatchException e){
                                            System.out.println("Please enter only numerical values.");
                                            sc.next();
                                        }
                                    }

                                    // Validate task number
                                    if (taskNum < 1 || taskNum > tasks.size()) {
                                        System.out.println("Invalid task number.");
                                        return;
                                    }

                                    // Remove the specified task
                                    tasks.remove(taskNum - 1);

                                    // Write the updated tasks back to the file
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileForWork))) {
                                        for (String task : tasks) {
                                            writer.write(task);
                                            writer.newLine();
                                        }
                                    } catch (IOException e) {
                                        System.out.println("Operation failed.");
                                    }

                                    System.out.println("Task deleted successfully.");
                                }

                                //'previous menu' option
                                else if (selection == 4){
                                    prevMenu = true;
                                }

                            } while (!prevMenu);
                        } else {
                            System.out.println("No file with such name exist.");
                            int createFileOpt;
                            while (true){
                                try {
                                    System.out.print("\nDo you wanna make new file with this name?(Enter 1 for yes else 0): ");
                                    createFileOpt = sc.nextInt();
                                    sc.nextLine();
                                    break;
                                } catch (InputMismatchException e){
                                    System.out.println("Please enter 0 or 1 only.");
                                    sc.next();
                                }
                            }

                            if (createFileOpt == 1){
                                createNewFile();
                            }
                        }
                    }
                    //'rename list' option
                    else if (opt == 4) {
                        renameFile();
                    }
                    //'go back' option
                    else if (opt == 5) {
                        goBack = true;
                    }
                } while (!goBack);
            }
        }
    }

}
