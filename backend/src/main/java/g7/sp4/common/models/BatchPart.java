package g7.sp4.common.models;

import jakarta.persistence.*;

@Entity
public class BatchPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @ManyToOne
    private Batch batch;

    private long partId;

    @Column(name="count")
    private int count;

    public void setId(long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }
    public void setBatch(Batch batch){
        this.batch = batch;
    }
    public Batch getBatch(){
        return batch;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public long getPartId() {
        return partId;
    }
    public void setPartId(long partId) {
        this.partId = partId;
    }
}
