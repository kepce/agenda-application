# Agenda Application

This application helps to organize daily tasks/goals. Tasks are displayed on daily tables and all tables are navigable during runtime with simple right and left arrow buttons on top of the table. Tasks can be created and added for future days or can be created and added to save a task that the user has already completed for past days.

![empty](https://user-images.githubusercontent.com/41506778/56904758-d3a5ae00-6a96-11e9-8a01-286760ea6337.jpg)

## How to use

Using **Agenda** is really easy and simple. Let's first start with the task attributes the user can assign to a task.

### Task Attributes

All attributes are shown below:  
1- A unique ID (assigned by MySQL)  
2- A name  
3- A detailed description of the task  
4- Creation date  
5- Status, in other words Completition flag (True if the task is completed, false otherwise)  
6- Due date  
7- Repetition property (Can be not repeated, repeated daily or repeated weekly)  

### Creating the First Task

Notice the combo box below the table. There are 3 options: "Add New Task", "Task Completed" and "Delete Task". To create a task first option, Add New Task, must be selected. After selecting, simply click on the "OK" button on the left. A new window to enter task details will appear like shown below.

![add_new_task_frame](https://user-images.githubusercontent.com/41506778/56905996-4d3e9b80-6a99-11e9-8508-ccffbb06664e.jpg)

Give a name and description to the task. Notice the cehckbox below the task description. If selected, the task will be repeated according to radio button selection. If the checkbox is not selected then the task will be assigned to the day of the table. Finally enter the due date of your task. Note that if your task is a repeated task, say for example a weekly task, this date will be your task's display period on tables. For example if you are adding a weekly task on 29-04-2019 (Monday) and enter due date of 01-01-2020, this task will be displayed on every Monday until 01-01-2020. Finally this window will not let the user add a new task with an invalid date.

### The Task Table

Although the tasks saved to the database has 7 attributes, the task table only shows the most relavent informaton about the tasks: Task ID, Task Name, Task Status, Due Date/Until. Tasks are colored according to their status: "Completed" or "Not Completed". For not completed tasks there are 3 possible colors: yellow for weekly tasks, magenta for daily tasks and red for regular not repeated tasks. If a task is completed regardless of its type it will be displayed in green. All colors are shown below.

![all_task_colors_2](https://user-images.githubusercontent.com/41506778/56909219-bd502000-6a9f-11e9-960b-88a312af8148.jpg)

### Changing Task Status to "Completed"

To change the status of a task the combo box below the table must be used. After selecting the task to be completed from the table, choose the second option, "Task Completed", then simply click "OK" button on the left. The selected row will turn into green if it was not so.

### Deleting a Task

Deleting a task is also done with the combo box. Select the row where the task to be deleted is present. Then choose the third option, "Delete Task", and simply click "OK" button on the left. A dialoge window will appear on the screen with a warning message like shown below.

![delete_task](https://user-images.githubusercontent.com/41506778/56910032-77945700-6aa1-11e9-8f33-3af221ca16a6.jpg)

If the selected task is regular not repeated task then it will simply be deleted afte clicking "OK" in the dialogue window. If it is not all repeated copies related to the selected task will also be deleted.

### Displaying Task Description

There are two ways to display detailed task description with the application. First works with mouse hover onto the task as shown below.

![tooltip_text](https://user-images.githubusercontent.com/41506778/56910298-26d12e00-6aa2-11e9-82ec-c247f81ec5fa.jpg)

Second way is to double-click onto the task. This will bring a small window that contains the following information: task name, task description, task creation date as shown below.

![task_desc](https://user-images.githubusercontent.com/41506778/56910534-ba0a6380-6aa2-11e9-922d-c041cc82e941.jpg)

## Notes

To use this application MySQL is needed. In the "DataBaseConnection.java" class user, password and url fields must be changed according to user details.

If MySQL database is already present and login settings are don in DataBaseConnection class, the MySQL dump file can be used for an example task list. This SQL file is in github.com/enderdincer/agenda-application/ under "mysql dump" directory, named db.sql.  
## Tools Used

Various tools have been used for this project. They are listed below:  
1- Eclipse IDE  
2- Java SE 8   
3- SQL  
4- MySQL  

## Future Work

A user interface for MySQL login details would allow the application to be executable.

