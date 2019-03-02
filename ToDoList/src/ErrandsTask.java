import java.time.LocalDate;

public class ErrandsTask extends Task {

	public ErrandsTask(String type, String text, LocalDate localDate) {
		super(type, text, localDate);
		// TODO Auto-generated constructor stub
	}

	  @Override
	  public String toString() {
		  return "[" + taskDate + ": " +  taskType + "] " + taskText; 
	  }

	@Override
	protected LocalDate getTaskDate() {
		// TODO Auto-generated method stub
		return taskDate;
	}
}
