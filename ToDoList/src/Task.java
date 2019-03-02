import java.time.LocalDate;
import java.util.Date;

public abstract class Task {
	
	protected String taskType; 
	protected String taskText;
	protected LocalDate taskDate;
// Variables are protected in order for the subclasses to access it
	
	public Task(String type, String text, LocalDate localDate) {
		this.taskType = type;
		this.taskText = text;
		this.taskDate = localDate;
		
	}	

	protected abstract LocalDate getTaskDate();


}
