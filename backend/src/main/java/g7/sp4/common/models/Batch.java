package g7.sp4.common.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "has_completed", nullable = false)
    private boolean hasCompleted = false;

    @Column(name = "employee_id")
    private String employeeId;

    @ManyToOne
    private Set<BatchPart> parts;

    public boolean isHasCompleted() {
        return hasCompleted;
    }

    public void setHasCompleted(boolean hasCompleted) {
        this.hasCompleted = hasCompleted;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Set<BatchPart> getParts() {
        return parts;
    }

    public void setParts(Set<BatchPart> parts) {
        this.parts = parts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
