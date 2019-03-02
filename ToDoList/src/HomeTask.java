import java.time.LocalDate;
import java.util.Date;

public class HomeTask  extends Task {

	public HomeTask(String type, String text, LocalDate localDate) {
		super(type, text, localDate);
		// TODO Auto-generated constructor stub
	}



	
	  @Override
	  public String toString() {
		  return "[" + taskDate + ": " +  taskType + "] " + taskText; 
	  }




	protected LocalDate getTaskDate() {
		// TODO Auto-generated method stub
		return taskDate;
	}

}
