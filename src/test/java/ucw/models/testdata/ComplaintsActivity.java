package ucw.models.testdata;

import lombok.Data;

@Data
public class ComplaintsActivity {
    private String subject, dateReceived, complaintTitle, dueDate, escalationDate,
            priority, assignTo, complaintsId;
    private NewNote newNote;
}
