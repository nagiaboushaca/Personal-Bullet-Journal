This is Repo Reaper's Bullet Journal! After the splash screen, you can enter a path to a .bujo file, 
and open your week view. You can create new Tasks and events, and it will appear in your week view.
The Task Queue bar on the right shows all your tasks and their completion status. The Notes section right
next to it is a place where you can write notes and load them later
You can even add hyperlinks into your descriptions to click and open in a browser. 
You can easily edit or delete events/tasks by clicking the buttons on each. 
You can change the theme by clicking the theme button. 
You can also see a progress bar for your tasks.

SOLID
S: Each class and method focuses on one job, for example the Task and Event classes. Other examples would be 
like the ThemeSetter class, which is used to set the style of our application. Most methods make use of private 
helper methods.
O: Our program makes use of interfaces (Model, View, Controller) and an abstract class (AbstractToDo), allowing 
for extensions without performing modifications of old classes. If there was something we wanted to include as part
of our journal, such as an assignment, we can extend it to the AbstractToDo class. 
L: All fields are marked with their super class type, and it follows the Liskov substitution principle
because our program would not break if they were substituted with another instance of the superclass. For example,
the BulletJournal interface and AbstractToDo class is used for fields, so they can be replaced by their subclasses
BulletJournalImpl, Task, Event, etc. 
I: Our program makes use of interfaces, and in those interfaces we made sure that they were short and sweet. 
Our interfaces also avoid having unnecessary code that isn't needed in all implementations. For example, our Controller
interface only has the run() method and uses private methods in the subclass BujoController class for extension.
D: Our program makes use of interfaces and abstract classes. The abstractions and interfaces used aren't reliant on the 
low level models, but they are using abstractions to handle the functionality.

![Screenshot of current output](/screenshot2.png)
