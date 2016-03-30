package pl.org.elzylab.eltimetable.beans.task;

public enum TaskStatus {
	CREATED (1, "created"),
	DONE(2, "done"),
	ARCHIVED (3, "archived"),
	CANCELED (4, "canceled");
	
	private int value;
	private String description;
	
	TaskStatus (int value, String description)
	{
		this.value = value;
		this.description = description;
	}
	

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static TaskStatus forValue(int i) {
		for(TaskStatus status: TaskStatus.values())
		{
			if(status.value == i)
				return status;
		}
		
		return null;
	}
}
