package com.example.MyVolunteer_api.model.task;

import com.example.MyVolunteer_api.constants.SignUpStatus;
import com.example.MyVolunteer_api.model.user.Volunteer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TaskSignups")
public class TaskSignups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signup_id")
    private Integer signupId;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private VolunteerOpportunities task;

    @Column(nullable = false)
    private SignUpStatus status;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String taskTitle;

    private String taskDesc;

    @Column(nullable = false)
    private String organizedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;

    public Date getAssignedDate() {
        if (task != null) {
            return assignedDate = task.getStartsAt();
        }
        return assignedDate;
    }

    public Date getCompletionDate() {
        if (task != null) {
            return completionDate = task.getEndsAt();
        }
        return completionDate;
    }

    public SignUpStatus getStatus() {
        if (status == SignUpStatus.TAKEN && new Date().after(getCompletionDate())) {
            status = SignUpStatus.COMPLETED;
        }
        return status;
    }


}