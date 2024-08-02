package ucw.models.testdata;

import lombok.Data;

@Data
public class NewNote {
    private String subject, dateReceived, complainantName, claimNumber,
            relatedTo, complaintDetails;
}
