package g7.sp4.common.models;

import jakarta.persistence.*;

import java.util.List;
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

    @OneToMany(mappedBy = "id", fetch=FetchType.EAGER)
    private List<BatchPart> parts;

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

    public List<BatchPart> getParts() {
        return parts;
    }

    public void setParts(List<BatchPart> parts) {
        this.parts = parts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
